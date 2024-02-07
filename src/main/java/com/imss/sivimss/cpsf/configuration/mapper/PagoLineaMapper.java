package com.imss.sivimss.cpsf.configuration.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.imss.sivimss.cpsf.model.request.PagoRequest;
import com.imss.sivimss.cpsf.model.response.ComPagoResponse;

/*
 * Este es un ejemplo de cómo se pueden implementar querys a través de interfaces con MyBatis.
 * */

public interface PagoLineaMapper {
	
	/*
	 * Este es un ejemplo para realizar un insert con un objeto como parámetro
	 * Esta debería ser la forma estandar para insertar nuevos registros
	 * 
	 * Se debe pasar un objeto con la anotacion @Param("out") para despues sacar los
	 * valores del objeto usando los comodines 
	 * #{out.nomPersona} -> #{nombreDelParam.nombreAtributoDeClase}
	 * 
	 * Esta expresión se utiliza para especificar el atributo del objeto que almacenará 
	 * el identificador del nuevo registro. @Options tiene mas aplicaciones, pero en este ejemplo
	 * se limita solo a obtener el id generado
	 * @Options(useGeneratedKeys = true,keyProperty = "out.idPersona", keyColumn="id")
	 * 
	 * */
	@Insert(value = "INSERT INTO SVT_PAGO_LINEA("
			+ "ID_REGISTRO, ID_FLUJO_PAGOS, ID_VELATORIO,"
			+ "ID_CLIENTE_LINEA, CVE_REFERENCIA, NUM_APROBACION, "
			+ "CVE_FOLIO_PAGO, NUM_TARJETA, REF_EMISOR, "
			+ "STP_TRANSACCION, IMP_VALOR, ID_ESTATUS_PAGO, "
			+ "ID_USUARIO_ALTA, FEC_ALTA, CVE_FOLIO_REGISTRO) "
			+ "VALUES ("
			+ " #{out.idRegistro}, #{out.idFlujoPagos}, #{out.idVelatorio},"
			+ "#{out.idCliente}, #{out.referencia}, #{out.numAprobacion}, "
			+ "#{out.folioPago}, #{out.numTarjeta}, #{out.emisorTarjeta}, "
			+ "#{out.fecTransaccion}, #{out.importe}, 4, "
			+ "#{out.idUsuario}, NOW(), #{out.folio})")
	@Options(useGeneratedKeys = true,keyProperty = "out.idPagoLinea", keyColumn="id")
	public int nuevoRegistroObj(@Param("out")PagoRequest pago);
	
	@Select("SELECT\r\n"
			+ "ID_PAGO_LINEA AS idPagoLinea,\r\n"
			+ "US.CVE_USUARIO AS claveCliente,\r\n"
			+ "CONCAT(PER.NOM_PERSONA, ' ', PER.NOM_PRIMER_APELLIDO, ' ', PER.NOM_SEGUNDO_APELLIDO) AS nomUsuario,\r\n"
			+ "PL.CVE_FOLIO_REGISTRO AS numCon,\r\n"
			+ "FP.DESC_FLUJO_PAGOS AS conPago,\r\n"
			+ "PL.IMP_VALOR AS impPagado,\r\n"
			+ "PL.CVE_REFERENCIA AS referencia,\r\n"
			+ "PL.NUM_APROBACION AS numAprobacion,\r\n"
			+ "PL.CVE_FOLIO_PAGO AS folioPago,\r\n"
			+ "PL.NUM_TARJETA AS numTarjeta,\r\n"
			+ "PL.REF_EMISOR AS emisorTarjeta,\r\n"
			+ "PL.STP_TRANSACCION AS fecTransaccion,\r\n"
			+ "DEL.ID_DELEGACION AS idDelegacion,\r\n"
			+ "DEL.DES_DELEGACION AS nomDelegacion,\r\n"
			+ "VEL.ID_VELATORIO AS idVelatorio,\r\n"
			+ "VEL.DES_VELATORIO AS nomVelatorio\r\n"
			+ "FROM\r\n"
			+ "SVT_PAGO_LINEA PL\r\n"
			+ "INNER JOIN SVT_USUARIOS US ON US.ID_USUARIO = PL.ID_CLIENTE_LINEA\r\n"
			+ "INNER JOIN SVC_PERSONA PER ON PER.ID_PERSONA = US.ID_PERSONA\r\n"
			+ "INNER JOIN SVC_VELATORIO VEL ON VEL.ID_VELATORIO = PL.ID_VELATORIO\r\n"
			+ "INNER JOIN SVC_DELEGACION DEL ON DEL.ID_DELEGACION = VEL.ID_DELEGACION\r\n"
			+ "INNER JOIN SVC_FLUJO_PAGOS FP ON FP.ID_FLUJO_PAGOS = PL.ID_FLUJO_PAGOS\r\n"
			+ "WHERE\r\n"
			+ "PL.ID_PAGO_LINEA = #{idPagoLinea} \r\n"
			+ "LIMIT 1"
			)
	public ComPagoResponse selectDatos(Integer idPagoLinea);
}
