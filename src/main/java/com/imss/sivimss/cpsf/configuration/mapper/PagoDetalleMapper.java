package com.imss.sivimss.cpsf.configuration.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import com.imss.sivimss.cpsf.model.request.PagoDetalleRequest;

/*
 * Este es un ejemplo de cómo se pueden implementar querys a través de interfaces con MyBatis.
 * */

public interface PagoDetalleMapper {
	
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
	@Insert(value = "INSERT INTO SVT_PAGO_DETALLE("
			+ "ID_PAGO_BITACORA, ID_METODO_PAGO, IMP_PAGO,"
			+ "NUM_AUTORIZACION, FEC_PAGO, CVE_ESTATUS, "
			+ "ID_USUARIO_ALTA, FEC_ALTA) "
			+ "VALUES "
			+ "( #{out.idPagoBitacora}, #{out.idMetodoPago}, #{out.importe},"
			+ "#{out.numAutorizacion}, #{out.fecPago}, #{out.estatusPago},"
			+ "#{out.idUsuarioAlta}, NOW())")
	@Options(useGeneratedKeys = true,keyProperty = "out.idPagoDetalle", keyColumn="id")
	public int nuevoRegistroObj(@Param("out")PagoDetalleRequest pago);
	
}
