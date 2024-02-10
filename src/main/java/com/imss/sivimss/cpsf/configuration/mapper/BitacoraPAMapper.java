package com.imss.sivimss.cpsf.configuration.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import com.imss.sivimss.cpsf.model.request.PagoRequest;

/*
 * Este es un ejemplo de cómo se pueden implementar querys a través de interfaces con MyBatis.
 * */

public interface BitacoraPAMapper {
	
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
	@Insert(value = "INSERT INTO SVC_BITACORA_PAGO_ANTICIPADO( "
			+ "ID_PAGO_SFPA, IND_TIPO_PAGO, FEC_PAGO, "
			+ "NUM_AUTORIZACION, REF_FOLIO_AUTORIZACION, IMP_PAGO, "
			+ "ID_METODO_PAGO, IND_ACTIVO, ID_USUARIO_ALTA, "
			+ "FEC_ALTA ) "
			+ "VALUES ( "
			+ "#{out.idPagoSFPA}, #{out.idPlataforma}, #{out.fecTransaccion},"
			+ "#{out.numAprobacion}, #{out.folioPago}, #{out.importe}, "
			+ "#{out.idMetodoPago}, b'1', #{out.idUsuario}, "
			+ "NOW() )")
	public void nuevoRegistroObj(@Param("out")PagoRequest pago);

}
