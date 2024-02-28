package com.imss.sivimss.cpsf.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imss.sivimss.cpsf.configuration.MyBatisConfig;
import com.imss.sivimss.cpsf.configuration.mapper.BitacoraPAMapper;
import com.imss.sivimss.cpsf.configuration.mapper.ConvenioPFMapper;
import com.imss.sivimss.cpsf.configuration.mapper.PagoBitacoraMapper;
import com.imss.sivimss.cpsf.configuration.mapper.PagoDetalleMapper;
import com.imss.sivimss.cpsf.configuration.mapper.PagoLineaMapper;
import com.imss.sivimss.cpsf.configuration.mapper.PagoSFPAMapper;
import com.imss.sivimss.cpsf.configuration.mapper.RenConvenioPFMapper;
import com.imss.sivimss.cpsf.model.request.PagoRequest;
import com.imss.sivimss.cpsf.service.PagoService;
import com.imss.sivimss.cpsf.utils.AppConstantes;
import com.imss.sivimss.cpsf.utils.LogUtil;
import com.imss.sivimss.cpsf.utils.ProviderServiceRestTemplate;
import com.imss.sivimss.cpsf.utils.Response;
import com.imss.sivimss.cpsf.model.request.UsuarioDto;
import com.imss.sivimss.cpsf.model.response.ComPagoResponse;
import com.imss.sivimss.cpsf.model.response.ConPFResponse;
import com.imss.sivimss.cpsf.model.response.CostoResponse;
import com.imss.sivimss.cpsf.model.response.RenConPFResponse;
import com.imss.sivimss.cpsf.model.request.PagoBitacoraRequest;
import com.imss.sivimss.cpsf.model.request.PagoDetalleRequest;

@Service
public class PagoServiceImpl implements PagoService {

	@Autowired
	private LogUtil logUtil;
	
	@Autowired
	private MyBatisConfig myBatisConfig;
	
	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	@Value("${reporte.comprobante-pago}")
	private String nomReporte;
	
	@Value("${endpoints.ms-reportes}")
	private String urlReportes;
	
	@Value("${formato_fecha_hora}")
	private String formatoFechaHora;
	
	private static final String ERROR_INFORMACION = "52";
	private static final String EXITO = "Exito";
	private static final Integer ESTATUS_PAGADO = 4;
	private static final Integer PLATAFORMA_LINEA = 2;
	private static final String TAR_CREDITO = "TARJETA DE CRÉDITO";
	private static final String TAR_DEBITO = "TARJETA DE DÉBITO";
	
	@Override
	public Response<Object> crear(PagoRequest pago, Authentication authentication) throws IOException {	
		
		Response<Object> response = new Response<>();
		Gson gson = new Gson();
		UsuarioDto usuarioDto = gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		String impresion;
		try (SqlSession session = sqlSessionFactory.openSession();) {

			pago.setIdUsuario(usuarioDto.getIdUsuario());
			pago.setIdPlataforma(PLATAFORMA_LINEA);
			pago.setIdCliente(usuarioDto.getIdUsuario());

			if (pago.getIdMetodoPago().equals(3)) {
				pago.setEmisorTarjeta(TAR_CREDITO);
			} else {
				pago.setEmisorTarjeta(TAR_DEBITO);
			}

			impresion = gson.toJson(pago);

			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(), "", "Pago con: " + pago.getEmisorTarjeta(),
					authentication);

			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(), "", "Datos de entrada: " + pago.getEmisorTarjeta(),
					authentication);

			/*
			 * Se inicia un session Factory
			 * 
			 * Usa los datos de MyBatisConfig y toma el datasource de ese mismo archivo
			 * 
			 * La trydeclaración -with-resources es una trydeclaración que declara uno o más
			 * recursos. Un recurso es un objeto que debe cerrarse una vez finalizado el
			 * programa.
			 */
			try {

				/*
				 * Debemos indicar cual o cuales Mapper vamos a utilizar (Asegurate de
				 * declararlo en tu archivo MyBatisConfig.class
				 * configuration.addMapper(NombreDeMiMapper.class);)
				 */
				PagoLineaMapper pagoLineaMapper = session.getMapper(PagoLineaMapper.class);
				pagoLineaMapper.nuevoRegistroObj(pago);
				session.commit();

			} catch (Exception e) {
				/*
				 * Para el escenario en que fallen las querys
				 * 
				 * 1._ Realizamos un roll back (regresamos los cambios) 2._ Cerramos la
				 * conexión.
				 */

				logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), e.getMessage(), "Error al guardar el pago en Linea",
						authentication);

				session.rollback();

				throw new IOException(ERROR_INFORMACION, e.getCause());
			}


			impresion = gson.toJson(pago);

			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(), "", "Registro insertado: " + impresion, authentication);

			// Preparando registro Pago Bitacora
			PagoBitacoraRequest pagoBitacora = new PagoBitacoraRequest();
			pagoBitacora.setIdRegistro(pago.getIdRegistro());
			pagoBitacora.setIdFlujoPagos(pago.getIdFlujoPagos());
			pagoBitacora.setIdVelatorio(pago.getIdVelatorio());
			pagoBitacora.setNomContratante(pago.getNomContratante());
			pagoBitacora.setFolio(pago.getFolio());
			pagoBitacora.setImporteRegistro(pago.getImporte());
			pagoBitacora.setEstatusPago(ESTATUS_PAGADO);
			pagoBitacora.setIdUsuarioAlta(usuarioDto.getIdUsuario());
			pagoBitacora.setIdPlataforma(PLATAFORMA_LINEA);

			PagoDetalleRequest pagoDetalle = new PagoDetalleRequest();
			pagoDetalle.setIdMetodoPago(pago.getIdMetodoPago());
			pagoDetalle.setImporte(pago.getImporte());
			pagoDetalle.setNumAutorizacion(pago.getNumAprobacion());
			pagoDetalle.setFecPago(pago.getFecTransaccion());
			pagoDetalle.setEstatusPago(ESTATUS_PAGADO);
			pagoDetalle.setIdUsuarioAlta(usuarioDto.getIdUsuario());

			switch (pago.getIdFlujoPagos()) {

			case 2:
				response = crearCon(pago, authentication, pagoBitacora, pagoDetalle);
				break;
			case 3:
				response = crearRenCon(pago, authentication, pagoBitacora, pagoDetalle);
				break;
			case 4:
				response = crearPA(pago, authentication);
				break;
			default:
				break;

			}
		}
		
		return response;
	}

	@Override
	public Response<Object> obtener(int idPagoLinea, Authentication authentication ) 
			throws IOException{
		
		Response<Object> response = null;
		ComPagoResponse comPagoResponse;
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession();) {

			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(), "", "Id a Buscar: " + idPagoLinea, authentication);

			try {

				/*
				 * Debemos indicar cual o cuales Mapper vamos a utilizar (Asegurate de
				 * declararlo en tu archivo MyBatisConfig.class
				 * configuration.addMapper(NombreDeMiMapper.class);)
				 */
				PagoLineaMapper pagoLineaMapper = session.getMapper(PagoLineaMapper.class);
				comPagoResponse = pagoLineaMapper.selectDatos(formatoFechaHora, idPagoLinea);
				String fecha = comPagoResponse.getFecTransaccion().replaceAll("\"", "");
				comPagoResponse.setFecTransaccion(fecha);
				session.commit();

				response = new Response<>(false, 200, EXITO, comPagoResponse);

			} catch (Exception e) {
				/*
				 * Para el escenario en que fallen las querys
				 * 
				 * 1._ Realizamos un roll back (regresamos los cambios) 2._ Cerramos la
				 * conexión.
				 */

				logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), e.getMessage(), "Error al buscar el pago: ",
						authentication);

				session.rollback();
				throw new IOException(ERROR_INFORMACION, e.getCause());
			}

			
		}
		
		return response;
	}
	
	private Response<Object> crearCon(PagoRequest pago, Authentication authentication, PagoBitacoraRequest pagoBitacora,
			PagoDetalleRequest pagoDetalle) throws IOException {
		
		Response<Object> response = null;
		ConPFResponse conPF;
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		

		try (SqlSession session = sqlSessionFactory.openSession();) {

			try {
				/*
				 * Debemos indicar cual o cuales Mapper vamos a utilizar (Asegurate de
				 * declararlo en tu archivo MyBatisConfig.class
				 * configuration.addMapper(NombreDeMiMapper.class);)
				 */
				ConvenioPFMapper convenioPFMapper = session.getMapper(ConvenioPFMapper.class);
				PagoBitacoraMapper pagoBitacoraMapper = session.getMapper(PagoBitacoraMapper.class);
				PagoDetalleMapper pagoDetalleMapper = session.getMapper(PagoDetalleMapper.class);
				conPF = convenioPFMapper.selectDatos(pago.getIdRegistro());
				pagoBitacora.setFechaRegistro(conPF.getFecInicio());
				pagoBitacoraMapper.nuevoRegistroObj(pagoBitacora);
				pagoDetalle.setIdPagoBitacora(pagoBitacora.getIdPagoBitacora());
				pagoDetalleMapper.nuevoRegistroObj(pagoDetalle);
				convenioPFMapper.actualizarRegistroObj(pago.getIdRegistro(), pago.getIdUsuario());
				session.commit();
				response = new Response<>(false, 200, EXITO, pago);

			} catch (Exception e) {
				/*
				 * Para el escenario en que fallen las querys
				 * 
				 * 1._ Realizamos un roll back (regresamos los cambios) 2._ Cerramos la
				 * conexión.
				 */

				logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), e.getMessage(), "Error al crear el Convenio PF: ",
						authentication);

				session.rollback();
				throw new IOException(ERROR_INFORMACION, e.getCause());
			}
		}
		
		
		
		return response;
	}
	
	private Response<Object> crearRenCon(PagoRequest pago, Authentication authentication, PagoBitacoraRequest pagoBitacora,
			PagoDetalleRequest pagoDetalle) throws IOException {
		
		Response<Object> response = null;
		RenConPFResponse renConPF;
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try (SqlSession session = sqlSessionFactory.openSession();) {
			try {

				/*
				 * Debemos indicar cual o cuales Mapper vamos a utilizar (Asegurate de
				 * declararlo en tu archivo MyBatisConfig.class
				 * configuration.addMapper(NombreDeMiMapper.class);)
				 */
				RenConvenioPFMapper renConvenioPFMapper = session.getMapper(RenConvenioPFMapper.class);
				PagoBitacoraMapper pagoBitacoraMapper = session.getMapper(PagoBitacoraMapper.class);
				PagoDetalleMapper pagoDetalleMapper = session.getMapper(PagoDetalleMapper.class);
				renConPF = renConvenioPFMapper.selectDatos(pago.getIdRegistro());
				pagoBitacora.setFechaRegistro(renConPF.getFecInicio());
				pagoBitacoraMapper.nuevoRegistroObj(pagoBitacora);
				pagoDetalle.setIdPagoBitacora(pagoBitacora.getIdPagoBitacora());
				pagoDetalleMapper.nuevoRegistroObj(pagoDetalle);
				renConvenioPFMapper.actualizarRegistroObj(pago.getIdRegistro(), pago.getIdUsuario());
				session.commit();

			} catch (Exception e) {
				/*
				 * Para el escenario en que fallen las querys
				 * 
				 * 1._ Realizamos un roll back (regresamos los cambios) 2._ Cerramos la
				 * conexión.
				 */

				logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), e.getMessage(),
						"Error al crear la Renovacion del Convenio PF: ", authentication);

				session.rollback();
				throw new IOException(ERROR_INFORMACION, e.getCause());
			}

		}
		return response;
		
	}
	
	private Response<Object> crearPA(PagoRequest pago, Authentication authentication) throws IOException {
		
		Response<Object> response = null;
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		
		try (SqlSession session = sqlSessionFactory.openSession();) {
			try {

				/*
				 * Debemos indicar cual o cuales Mapper vamos a utilizar (Asegurate de
				 * declararlo en tu archivo MyBatisConfig.class
				 * configuration.addMapper(NombreDeMiMapper.class);)
				 */
				BitacoraPAMapper bitacoraPAMapper = session.getMapper(BitacoraPAMapper.class);
				PagoSFPAMapper pagoSFPAMapper = session.getMapper(PagoSFPAMapper.class);
				bitacoraPAMapper.nuevoRegistroObj(pago);
				Double costoRestante = validaCosto(pago.getIdRegistro(), authentication);

				logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), "", "El costo restante es: " + costoRestante,
						authentication);

				Integer estatusPagoSFPA = 8;// 8 estatus por pagar

				if (costoRestante == 0 || costoRestante == 0.0) {

					logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
							this.getClass().getPackage().toString(), "", "costo restante es 0", authentication);
					estatusPagoSFPA = 5;// 5 pagado
					pagoSFPAMapper.actualizarFolioPago(pago.getIdUsuario(), pago.getIdPagoSFPA(), pago.getIdRegistro());
				}

				if (costoRestante == -1.0)
					return new Response<>(false, 500, "Error al buscar los costos", null);

				logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), "", "el estatus de pago es " + estatusPagoSFPA,
						authentication);

				pagoSFPAMapper.actualizaEstatusPagoSFPA(pago, estatusPagoSFPA);

				Double total = validaTotalPagado(pago.getIdRegistro(), authentication);

				logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), "", "El total es: " + total, authentication);

				Integer estatusPlan = 2;// esatus plan 2 vigente
				if (total <= 0.0)
					estatusPlan = 4;// esatus plan 4 pagado

				logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), "", "Total pagado: " + total, authentication);

				logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), "", "Estatus Plan: " + estatusPlan, authentication);

				pagoSFPAMapper.actualizaEstatusPlan(pago, estatusPlan);
				session.commit();

				response = new Response<>(false, 200, EXITO, pago);

			} catch (Exception e) {
				/*
				 * Para el escenario en que fallen las querys
				 * 
				 * 1._ Realizamos un roll back (regresamos los cambios) 2._ Cerramos la
				 * conexión.
				 */

				logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), e.getMessage(), "Error al crear Pagos Anticipados: ",
						authentication);

				session.rollback();
				throw new IOException(ERROR_INFORMACION, e.getCause());
			}

			
		}
		return response;
	}

	@Override
	public Response<Object> reporte(int idPagoLinea, Authentication authentication) throws IOException {

		Map<String, Object> envioDatos = new HashMap<>();
		envioDatos.put("idPagoLinea", idPagoLinea);
		envioDatos.put(AppConstantes.TIPO_REPORTE, "pdf");
		envioDatos.put(AppConstantes.RUTA_NOMBRE_REPORTE, nomReporte);
		
		try {

				return	providerRestTemplate.consumirServicioReportes(envioDatos, urlReportes, authentication);
			
			} catch (IOException e) {
				
				return new Response<>(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getCause());
			}
	}
	
	private Double validaCosto( Integer idPlan, Authentication authentication ) throws IOException {

        Double deudaMensualActual = 0.0;
        Double deudasPasadas = 0.0;
        Double pagosRealizados = 0.0;
        Double mensualidad = 0.0;
        List<CostoResponse> costos = null;
        
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession();) {

			try {

				/*
				 * Debemos indicar cual o cuales Mapper vamos a utilizar (Asegurate de
				 * declararlo en tu archivo MyBatisConfig.class
				 * configuration.addMapper(NombreDeMiMapper.class);)
				 */
				PagoSFPAMapper pagoSFPAMapper = session.getMapper(PagoSFPAMapper.class);
				costos = pagoSFPAMapper.validaMontoPagoSFPA(idPlan);
				session.commit();

			} catch (Exception e) {
				/*
				 * Para el escenario en que fallen las querys
				 * 
				 * 1._ Realizamos un roll back (regresamos los cambios) 2._ Cerramos la
				 * conexión.
				 */

				logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), e.getMessage(), "Error al buscar los costos: ",
						authentication);

				session.rollback();
				throw new IOException(ERROR_INFORMACION, e.getCause());
			}

			session.close();

			if ((costos != null) && (costos.size() > 0)) {
				Integer contador = 0;
				for (CostoResponse rs : costos) {

					if (contador == 0)
						deudaMensualActual = rs.getDeudaMensualActual();

					if (contador == 1) {
						deudasPasadas = rs.getDeudasPasadas();
						mensualidad = rs.getPagosRealizados();
					}

					if (contador == 2)
						pagosRealizados = rs.getPagosRealizados();
					contador++;

				}

				logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), "",
						"El costo deudaMensualActual es: " + deudaMensualActual, authentication);

				logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), "", "El costo mensual es: " + deudasPasadas,
						authentication);

				logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), "", "El costo pagosRealizado es: " + pagosRealizados,
						authentication);

				if ((pagosRealizados) == 0.0 || (pagosRealizados) == 0) {
					return 0.0;
				} else if (deudaMensualActual > 0) {
					if ((deudaMensualActual - mensualidad) > mensualidad && (deudasPasadas - pagosRealizados) > 0) {
						return (deudasPasadas - pagosRealizados);
					} else if ((deudasPasadas - pagosRealizados) > 0)
						return (deudasPasadas - pagosRealizados);

					return 0.0;
				}
				return deudaMensualActual;

			}
		}

        return 0.0;

    }
	
	private Double validaTotalPagado( Integer idPlan, Authentication authentication ) throws IOException {

        Double total = 0.0;
        SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
      
		try (SqlSession session = sqlSessionFactory.openSession();) {
			try {

				/*
				 * Debemos indicar cual o cuales Mapper vamos a utilizar (Asegurate de
				 * declararlo en tu archivo MyBatisConfig.class
				 * configuration.addMapper(NombreDeMiMapper.class);)
				 */
				PagoSFPAMapper pagoSFPAMapper = session.getMapper(PagoSFPAMapper.class);
				total = pagoSFPAMapper.totalPagado(idPlan);
				session.commit();

			} catch (Exception e) {
				/*
				 * Para el escenario en que fallen las querys
				 * 
				 * 1._ Realizamos un roll back (regresamos los cambios) 2._ Cerramos la
				 * conexión.
				 */

				logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), e.getMessage(), "Error al buscar los costos: ",
						authentication);

				session.rollback();
				throw new IOException(ERROR_INFORMACION, e.getCause());
			}

			
		}
        return total;

    }

}
