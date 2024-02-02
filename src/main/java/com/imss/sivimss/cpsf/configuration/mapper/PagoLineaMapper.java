package com.imss.sivimss.cpsf.configuration.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import com.imss.sivimss.cpsf.model.request.PagoRequest;

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
	@Insert(value = "INSERT INTO SVT_PAGO_LINEA(ID_REGISTRO, ID_FLUJO_PAGOS, ID_VELATORIO,"
			+ "ID_CLIENTE_LINEA, CVE_REFERENCIA, NUM_APROBACION, CVE_FOLIO_PAGO, NUM_TARJETA,"
			+ "REF_EMISOR, STP_TRANSACCION, IMP_VALOR, ID_ESTATUS_PAGO, ID_USUARIO_ALTA, FEC_ALTA) "
			+ "VALUES ( #{out.idRegistro}, #{out.idFlujoPagos}, #{out.idVelatorio},"
			+ "#{out.idCliente}, #{out.referencia}, #{out.numAprobacion}, #{out.folioPago}, #{out.numTarjeta},"
			+ "#{out.emisorTarjeta}, #{out.fecTransaccion}, #{out.importe}, 4, #{out.idUsuario}, NOW())")
	@Options(useGeneratedKeys = true,keyProperty = "out.idPagoLinea", keyColumn="id")
	public int nuevoRegistroObj(@Param("out")PagoRequest pago);
	
}
