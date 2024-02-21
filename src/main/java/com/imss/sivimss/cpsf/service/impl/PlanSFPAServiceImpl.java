package com.imss.sivimss.cpsf.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.imss.sivimss.cpsf.configuration.MyBatisConfig;
import com.imss.sivimss.cpsf.configuration.mapper.Consultas;
import com.imss.sivimss.cpsf.configuration.mapper.PlanSFPAMapper;
import com.imss.sivimss.cpsf.model.request.Contratante;
import com.imss.sivimss.cpsf.model.request.PagoSFPA;
import com.imss.sivimss.cpsf.model.request.PlanSFPA;
import com.imss.sivimss.cpsf.model.request.UsuarioDto;
import com.imss.sivimss.cpsf.model.response.ContratanteResponse;
import com.imss.sivimss.cpsf.model.response.PagoSFPAResponse;
import com.imss.sivimss.cpsf.model.response.PaqueteResponse;
import com.imss.sivimss.cpsf.model.response.PersonaResponse;
import com.imss.sivimss.cpsf.model.response.PlanSFPAResponse;
import com.imss.sivimss.cpsf.model.response.ReportePagoAnticipadoReponse;
import com.imss.sivimss.cpsf.service.PlanSFPAService;
import com.imss.sivimss.cpsf.service.beans.BeanQuerys;
import com.imss.sivimss.cpsf.utils.AppConstantes;
import com.imss.sivimss.cpsf.utils.DatosRequestUtil;
import com.imss.sivimss.cpsf.utils.GeneraCredencialesUtil;
import com.imss.sivimss.cpsf.utils.MensajeResponseUtil;
import com.imss.sivimss.cpsf.utils.NumeroAPalabra;
import com.imss.sivimss.cpsf.utils.ProviderServiceRestTemplate;
import com.imss.sivimss.cpsf.utils.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlanSFPAServiceImpl implements PlanSFPAService {
	
	@Autowired
	private MyBatisConfig myBatisConfig;
	
	@Autowired
	private BeanQuerys query;
	
	@Autowired
	private DatosRequestUtil datosUtil;
	
	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	@Autowired
	private ModelMapper mapper;
	
	@Value("${reporte.anexo-convenio-pago-anticipado}")
	private String convenioPagoAnticipado;
	
	@Value("${endpoints.ms-reportes}")
	private String urlReportes;
	
	@Autowired
	private GeneraCredencialesUtil generaCredenciales;
	
	private Response<Object>response;
	
    private	List<Map<String, Object>> servicios = new ArrayList<>();
	private int i;

	@Override
	public Response<Object> consultaDetallePlanSfpa(String cveUsuario) {
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			result = consultas.selectNativeQuery(query.detallePlanSFPA(cveUsuario));
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object> consultaTipoContratacion() {
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			result = consultas.selectNativeQuery(query.consultaTipoContratacion());
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object> consultaTipoPagoMensual() {
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			result = consultas.selectNativeQuery(query.consultaTipoPagoMensual());
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object> consultaPromotores() {
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			result = consultas.selectNativeQuery(query.consultaPromotores());
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object> consultaPaquetes() {
		
		List<PaqueteResponse> paqueteResponse = new ArrayList<>();
		List<Map<String, Object>> paquete = new ArrayList<>();
		
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			paquete = consultas.selectNativeQuery(query.consultaPaquetes());
			paqueteResponse = Arrays.asList(mapper.map(paquete, PaqueteResponse[].class));
			
			paqueteResponse.stream().forEach(registro ->{
				servicios = consultas.selectNativeQuery(query.consultaServiciosPaquete(registro.getIdPaquete()));
						Object[] arreglo = new Object[servicios.size()];
						i=0;
								servicios.forEach(r ->{
									arreglo[i]= r.get("servicio");
									i++;
									registro.setServiciosPaquetes(arreglo);});
			});
		
			
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, paqueteResponse);
	}

	@Override
	public Response<Object> consultaValidaAfiliado(Contratante contratanteRequest) {
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			result = consultas.selectNativeQuery(query.consultaValidaAfiliado(contratanteRequest));
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object> registrarLineaPlanSFPA(PlanSFPA planSFPARequest, Authentication authentication) {
		/* 
		 * Creamos una instancia del objeto/representación del nuevo registro 
		 */
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();

		
		/* Creamos una instancia de nuestro objeto de respuesta */
		Response<Object> resp = new Response<>();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			
			UsuarioDto user = datosUtil.getUserData(authentication);
			
			planSFPARequest.setIdUsuario(user.getIdUsuario());
		    log.info(""+user.getIdUsuario());

			PlanSFPAMapper planSFPAMapper = session.getMapper(PlanSFPAMapper.class);
			
			try {
				obtenerDetalle(planSFPARequest, user, planSFPAMapper);
				
				planSFPARequest.setTitular(planSFPARequest.getTitularesBeneficiarios().get(0).getIdContratante());
				if(planSFPARequest.getIndTitularSubstituto()==0) {
					
					planSFPARequest.setSubtitular(planSFPARequest.getTitularesBeneficiarios().get(1).getIdTitularBeneficiarios());
			        
					if(planSFPARequest.getTitularesBeneficiarios().size() == 3) {
					//	planSFPARequest.setSubtitular(planSFPARequest.getTitularesBeneficiarios().get(1).getIdTitularBeneficiarios());
						planSFPARequest.setBeneficiario1(planSFPARequest.getTitularesBeneficiarios().get(2).getIdBeneficiario1());
					}
					 if(planSFPARequest.getTitularesBeneficiarios().size() == 4 ) {
						//planSFPARequest.setSubtitular(planSFPARequest.getTitularesBeneficiarios().get(1).getIdTitularBeneficiarios());
						planSFPARequest.setBeneficiario1(planSFPARequest.getTitularesBeneficiarios().get(2).getIdBeneficiario1());
						planSFPARequest.setBeneficiario2(planSFPARequest.getTitularesBeneficiarios().get(3).getIdBeneficiario2());	
					}
				
				}else {
					if(planSFPARequest.getTitularesBeneficiarios().size() == 2) {
						//	planSFPARequest.setSubtitular(planSFPARequest.getTitularesBeneficiarios().get(1).getIdTitularBeneficiarios());
							planSFPARequest.setBeneficiario1(planSFPARequest.getTitularesBeneficiarios().get(1).getIdBeneficiario1());
						}
						if(planSFPARequest.getTitularesBeneficiarios().size() == 3 ) {
							//planSFPARequest.setSubtitular(planSFPARequest.getTitularesBeneficiarios().get(1).getIdTitularBeneficiarios());
							planSFPARequest.setBeneficiario1(planSFPARequest.getTitularesBeneficiarios().get(1).getIdBeneficiario1());
							planSFPARequest.setBeneficiario2(planSFPARequest.getTitularesBeneficiarios().get(2).getIdBeneficiario2());	
						}
				}
			
				
				planSFPARequest.setNumFolioPlanSfpa(planSFPAMapper.selectnumFolioPlanSfpa(planSFPARequest.getIdVelatorio(), planSFPARequest.getIdPaquete(), planSFPARequest.getIdTipoPagoMensual()));
		
				planSFPAMapper.insertarPlanSfpa(planSFPARequest);
				Integer idPagoMensual=0;
				String pagoMensual=null;
				for (int i = 0; i < planSFPARequest.getNumPagoMensual(); i++) {
					//LocalDate fechaActual = LocalDate.now();
					//LocalDate fechafinal = fechaActual.plusMonths(i);
					PagoSFPA pago = new PagoSFPA ();
					pago.setIdPlanSfpa(planSFPARequest.getIdPlanSfpa());
					pago.setIdEstatusPagoSfpa(i == 0?8:7);
					pago.setMonMensual(planSFPARequest.getMonPrecio()/planSFPARequest.getNumPagoMensual());
					//pago.setFecParcialidad("DATE_ADD(CURDATE(), INTERVAL " +i+" MONTH)");
					pago.setI(i);
					pago.setIdUsuario(user.getIdUsuario());
					
					
					if (i==0) {
						planSFPAMapper.insertarPagosfpa(pago);
						idPagoMensual=pago.getIdPagoSfpa();
						pagoMensual=pago.getMonMensual().toString();
						continue;
					}
					
					planSFPAMapper.insertarPagosfpa(pago);
				}
				
			//	ContratanteResponse contratante = planSFPAMapper.selectContratante(planSFPARequest.getIdPlanSfpa());
				
				//Se anula envio de corrreos
			/*	String contrasenia= generaCredenciales.generarContrasenia(contratante.getNomPersona() , contratante.getNomApellidoPaterno());
				String usuario = generaCredenciales.insertarUser(contratante.getIdTitular(),contratante.getNomPersona(), contratante.getNomApellidoPaterno(), contrasenia, contratante.getIdPersona(), user.getIdUsuario(), planSFPARequest.getIdVelatorio(), planSFPAMapper);
     			resp = generaCredenciales.enviarCorreo(usuario, contratante.getCorreo(), contratante.getNomPersona(), contratante.getNomApellidoPaterno(), contratante.getNomApellidoMaterno(), contrasenia); */
			
				
					response = this.getReporte(planSFPARequest.getIdPlanSfpa(), authentication, planSFPAMapper);
					 Map<String, Object> respuestaMap = new HashMap<>();  
					 respuestaMap.put("id", planSFPARequest.getIdPlanSfpa());
					 respuestaMap.put("reporte", response.getDatos().toString());
					 respuestaMap.put("idPagoMensual", idPagoMensual);
					 respuestaMap.put("pagoMensual", pagoMensual);
					 response.setDatos(respuestaMap);
					response.setMensaje(planSFPARequest.getNumFolioPlanSfpa());
					session.commit();
				 
			
			} catch (Exception e) {
				e.printStackTrace();
				session.rollback();
				session.close();
			}finally {
				
				session.close();
			}
		
			
		}
		return response;
	}

	private void obtenerDetalle(PlanSFPA planSFPARequest, UsuarioDto user, PlanSFPAMapper planSFPAMapper) {
		for (Contratante persona :planSFPARequest.getTitularesBeneficiarios()) {
			persona.setIdUsuario(user.getIdUsuario());
			persona.getCp().setIdUsuario(user.getIdUsuario());
			if(persona.getPersona().equalsIgnoreCase(AppConstantes.TITULAR)) {
				PersonaResponse personaTemp = planSFPAMapper.selectExisteContratante(DatosRequestUtil.condicion(persona));
				if(personaTemp != null && personaTemp.getIdPersona() != null) {
					persona.setIdPersona(personaTemp.getIdPersona());
					persona.setTipoPersona(persona.getPersona()+" onlineA");
					planSFPAMapper.actualizarPersona(persona);
				} else {
					persona.setTipoPersona(persona.getPersona()+" online");
					planSFPAMapper.insertarPersona(persona);
				}
				if(personaTemp != null && personaTemp.getIdDomicilio() != null) {
					persona.getCp().setIdDomicilio(personaTemp.getIdDomicilio());
					planSFPAMapper.actualizarDomicilio(persona.getCp());
				} else {
					planSFPAMapper.insertarDomicilio(persona.getCp());
				}
				if(personaTemp != null && personaTemp.getIdContratante() != null) {
					persona.setIdContratante(personaTemp.getIdContratante());
					planSFPAMapper.actualizarContratante(persona);
				} else {
					planSFPAMapper.insertarContratante(persona);
				}
			} else {
				PersonaResponse personaTemp = planSFPAMapper.selectExisteTitularBeneficiarios(DatosRequestUtil.condicion(persona));
				if(personaTemp != null && personaTemp.getIdPersona() != null) {
					persona.setIdPersona(personaTemp.getIdPersona());
					persona.setTipoPersona(persona.getPersona()+" onlineA");
					planSFPAMapper.actualizarPersona(persona);
				} else {
					persona.setTipoPersona(persona.getPersona()+" online");
					planSFPAMapper.insertarPersona(persona);
				}
				if(personaTemp != null && personaTemp.getIdDomicilio() != null) {
					persona.getCp().setIdDomicilio(personaTemp.getIdDomicilio());
					planSFPAMapper.actualizarDomicilio(persona.getCp());
				} else {
					planSFPAMapper.insertarDomicilio(persona.getCp());
				}
				if (persona.getPersona().equalsIgnoreCase(AppConstantes.TITULAR_SUBSTITUTO)) {
					if(personaTemp != null && personaTemp.getIdTitularBeneficiarios() != null ) {
						persona.setIdTitularBeneficiarios(personaTemp.getIdTitularBeneficiarios());
						planSFPAMapper.actualizarTitularBeneficiarios(persona);
					} else {
						planSFPAMapper.insertarTitularBeneficiarios(persona);
					}
				}
				if (persona.getPersona().equalsIgnoreCase(AppConstantes.BENEFICIARIO_1)) {
					if(personaTemp != null && personaTemp.getIdTitularBeneficiarios() != null ) {
						persona.setIdBeneficiario1(personaTemp.getIdTitularBeneficiarios());
						planSFPAMapper.actualizarTitularBeneficiarios(persona);
					} else {
						planSFPAMapper.insertarTitularBeneficiario1(persona);
					}
				}
				if (persona.getPersona().equalsIgnoreCase(AppConstantes.BENEFICIARIO_2)) {
					if(personaTemp != null && personaTemp.getIdTitularBeneficiarios() != null) {
						persona.setIdBeneficiario2(personaTemp.getIdTitularBeneficiarios());
						planSFPAMapper.actualizarTitularBeneficiarios(persona);
					} else {
						planSFPAMapper.insertarTitularBeneficiario2(persona);
					}
				}
			}
		}
	}

	@Override
	public Response<Object> folioPlanSfpa(Integer idPlanSfpa) {
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			result = consultas.selectNativeQuery(query.folioPlanSfpa(idPlanSfpa));
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object> consultarPagoSfpa(Integer idPlanSfpa) {
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			result = consultas.selectNativeQuery(query.consultarPagoSfpa(idPlanSfpa));
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object> consultaPlanSFPA(Integer idPlanSfpa) {
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			result = consultas.selectNativeQuery(query.consultaPlanSFPA(idPlanSfpa));
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object> generaReporteConvenioPagoAnticipado(Integer idPlanSfpa, Authentication authentication) {
		
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			
			PlanSFPAMapper planSFPAMapper = session.getMapper(PlanSFPAMapper.class);
			
			return getReporte(idPlanSfpa, authentication, planSFPAMapper);
		}
	}

	private Response<Object> getReporte(Integer idPlanSfpa, Authentication authentication,
			PlanSFPAMapper planSFPAMapper) {
		ReportePagoAnticipadoReponse reportePagoAnticipadoReponse;
		reportePagoAnticipadoReponse = planSFPAMapper.generaReporte(idPlanSfpa);
		
		reportePagoAnticipadoReponse.setFirmDir(planSFPAMapper.getImagenFirma());
		
		reportePagoAnticipadoReponse.setImgCheck(planSFPAMapper.getImagenCheck());
		
		reportePagoAnticipadoReponse.setNomFibeso(planSFPAMapper.getNomFibeso());
		
		Map<String, Object> envioDatos = generarMap(reportePagoAnticipadoReponse);
		
		try {
			
		//	return	MensajeResponseUtil.mensajeConsultaResponseObject(providerRestTemplate.consumirServicioReportes(envioDatos, urlReportes, authentication), AppConstantes.AGREGADO_CORRECTAMENTE);
			return	providerRestTemplate.consumirServicioReportes(envioDatos, urlReportes, authentication);
		
		} catch (IOException e) {
			
			return new Response<>(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getCause());
		}
	}

	private Map<String, Object> generarMap( ReportePagoAnticipadoReponse contratoServicioInmediatoResponse ) {
		Map<String, Object> envioDatos = new HashMap<>();
		envioDatos.put("nombreTitular", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getNombreTitular()));
		envioDatos.put("nacionalidadTitular", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getNacionalidadTitular()));
		envioDatos.put("rfcTitular", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getRfcTitular()));
		envioDatos.put("calleTitular", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getCalleTitular()));
		envioDatos.put("numExterior", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getNumExterior()));
		envioDatos.put("numInterior", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getNumInterior()));
		envioDatos.put("colonia", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getColonia()));
		envioDatos.put("codigoPostal", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getCodigoPostal()));
		envioDatos.put("municipio", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getMunicipio()));
		envioDatos.put("estado", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getEstado()));
		//se agregan lineas
		String direccion=  DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getCalleTitular())+
				" "+DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getNumExterior())+
				" "+DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getNumInterior())+
				" "+DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getCodigoPostal())+
				" "+DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getColonia())+
				" "+DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getMunicipio())+
				" "+DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getEstado());
		envioDatos.put("correo", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getCorreo()));
		envioDatos.put("telefono", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getTelefono()));
		envioDatos.put("telefonoFijo", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getTelefonoFijo()));
		envioDatos.put("totalImporte",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getTotalImporte()));
		envioDatos.put("numPago", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getNumPago()));
		envioDatos.put("ciudadFirma", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getCiudadFirma()));
		envioDatos.put("fechaCPA", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getFechaFirma()));
		envioDatos.put("paquete", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getNomPaquete()));
		envioDatos.put("pagos", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getPagos()));
		envioDatos.put("servInclPaquete", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getServInclPaquete()));
		envioDatos.put("correoVelatorio", DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getCorreoVelatorio()));
		envioDatos.put("numeroAfiliacion",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getNumeroAfiliacion()));
		
		envioDatos.put("nombreSustituto",Boolean.TRUE.equals(contratoServicioInmediatoResponse.getIndTitularSubs()) ?  DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getNombreTitular()) : DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getNombreSustituto()));
		envioDatos.put("fecNacSustituto",Boolean.TRUE.equals(contratoServicioInmediatoResponse.getIndTitularSubs()) ? DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getFecNacTitular()) : DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getFecNacSustituto()));
		envioDatos.put("rfcSustituto",Boolean.TRUE.equals(contratoServicioInmediatoResponse.getIndTitularSubs()) ? DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getRfcTitular()) : DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getRfcSustituto()));
		envioDatos.put("telefonoSustituto", Boolean.TRUE.equals(contratoServicioInmediatoResponse.getIndTitularSubs()) ?  DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getTelefono()) : DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getTelefonoSustituto()));
		envioDatos.put("direccionSustituto",Boolean.TRUE.equals(contratoServicioInmediatoResponse.getIndTitularSubs()) ?direccion : DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getDireccionSustituto()));
		
		envioDatos.put("nombreB1",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getNombreB1()));
		envioDatos.put("fecNacB1",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getFecNacB1()));
		envioDatos.put("rfcB1",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getRfcB1()));
		envioDatos.put("telefonoB1",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getTelefonoB1()));
		envioDatos.put("direccionB1",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getDireccionB1()));
		envioDatos.put("nombreB2",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getNombreB2()));
		envioDatos.put("fecNacB2",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getFecNacB2()));
		envioDatos.put("rfcB2",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getRfcB2()));
		envioDatos.put("telefonoB2",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getTelefonoB2()));
		envioDatos.put("direccionB2",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getDireccionB2()));
		envioDatos.put("totalLetra",NumeroAPalabra.convertirAPalabras(contratoServicioInmediatoResponse.getTotalImporte(), true));
		envioDatos.put("firmaDir",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getFirmDir()));
		envioDatos.put("imgCheck",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getImgCheck()));
		envioDatos.put("nomFibeso",DatosRequestUtil.validarSiEsNull(contratoServicioInmediatoResponse.getNomFibeso()));
		envioDatos.put(AppConstantes.TIPO_REPORTE, "pdf");
		envioDatos.put(AppConstantes.RUTA_NOMBRE_REPORTE, convenioPagoAnticipado);
	return envioDatos;
	}

	@Override
	public Response<Object> verDetallePlanSfpa(Integer idPlanSfpa) throws IOException, SQLException {
		PlanSFPAResponse planSFPAResponse;
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			PlanSFPAMapper planSFPAMapper = session.getMapper(PlanSFPAMapper.class);
			
			List<PagoSFPAResponse> pagoSFPA = planSFPAMapper.selectVerdetallePagoSFPA(idPlanSfpa);
			
			planSFPAResponse = DatosRequestUtil.generarDetallePlan( planSFPAMapper.selectVerdetallePlanSFPA(idPlanSfpa), pagoSFPA);

		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, planSFPAResponse);
	}

}
