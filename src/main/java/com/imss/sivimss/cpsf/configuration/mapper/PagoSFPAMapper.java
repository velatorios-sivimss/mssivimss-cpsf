package com.imss.sivimss.cpsf.configuration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imss.sivimss.cpsf.model.request.PagoRequest;
import com.imss.sivimss.cpsf.model.response.CostoResponse;

/*
 * Este es un ejemplo de cómo se pueden implementar querys a través de interfaces con MyBatis.
 * */

public interface PagoSFPAMapper {
	
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
	
	@Select("SELECT \r\n"
			+ "(\r\n"
			+ "CAST(IFNULL(SUM(sps.IMP_PAGO), 0.0) + ifnull(sum( sps.IMP_AUTORIZADO_VALE_PARITARIO), 0)  AS DOUBLE) \r\n"
			+ "- IFNULL(sps2.IMP_MONTO_MENSUAL, 0.0)\r\n"
			+ ") \r\n"
			+ "AS deudaMensualActual, \r\n"
			+ "0.0 AS deudasPasadas, \r\n"
			+ "0.0 AS pagosRealizados \r\n"
			+ "FROM SVC_BITACORA_PAGO_ANTICIPADO sps \r\n"
			+ "JOIN SVT_PAGO_SFPA sps2 ON \r\n"
			+ " sps2.ID_PAGO_SFPA = sps.ID_PAGO_SFPA \r\n"
			+ "WHERE sps2.ID_PLAN_SFPA = #{idPlan}\r\n"
			+ "AND sps.IND_ACTIVO = 1  \r\n"
			+ "AND MONTH(sps.FEC_ALTA) = MONTH(CURDATE())  \r\n"
			+ "UNION ALL \r\n"
			+ "SELECT 0 AS deudaMensualActual, \r\n"
			+ "CAST(IFNULL(SUM(sps.IMP_MONTO_MENSUAL),0.0) AS DOUBLE) AS deudasPasadas,  \r\n"
			+ "ifnull(sps.IMP_MONTO_MENSUAL,0.0) AS pagosRealizados \r\n"
			+ "FROM SVT_PAGO_SFPA sps \r\n"
			+ "LEFT JOIN SVC_BITACORA_PAGO_ANTICIPADO bpaa  \r\n"
			+ "ON bpaa.ID_PAGO_SFPA = sps.ID_PAGO_SFPA \r\n"
			+ "AND bpaa.IND_ACTIVO = 1\r\n"
			+ "WHERE sps.IND_ACTIVO = 1  \r\n"
			+ "AND sps.ID_PLAN_SFPA = #{idPlan}\r\n"
			+ "AND sps.ID_ESTATUS_PAGO = 2 \r\n"
			+ "UNION ALL \r\n"
			+ "SELECT 0.0 AS deudaMensualActual, \r\n"
			+ "0.0 AS deudasPasadas,  \r\n"
			+ "ifnull (ps.IMP_PRECIO - CAST(IFNULL(SUM(bpaa.IMP_PAGO),0.0) + ifnull(sum( bpaa.IMP_AUTORIZADO_VALE_PARITARIO), 0)  AS DOUBLE) ,0.0) AS pagosRealizados \r\n"
			+ "FROM SVT_PAGO_SFPA sps \r\n"
			+ "JOIN SVC_BITACORA_PAGO_ANTICIPADO bpaa  \r\n"
			+ "ON bpaa.ID_PAGO_SFPA = sps.ID_PAGO_SFPA \r\n"
			+ " JOIN SVT_PLAN_SFPA ps ON ps.ID_PLAN_SFPA=  sps.ID_PLAN_SFPA\r\n"
			+ "WHERE bpaa.IND_ACTIVO = 1  \r\n"
			+ "AND sps.ID_PLAN_SFPA = #{idPlan}"
			)
	public List<CostoResponse> validaMontoPagoSFPA(Integer idPlan);
	
	@Update(value = ""
			+ "UPDATE SVT_PAGO_SFPA SET ID_USUARIO_MODIFICA = #{idUsuario},\r\n"
			+ "FEC_ACTUALIZACION = NOW(),\r\n"
			+ "REF_FOLIO_RECIBO = (\r\n"
			+ "SELECT\r\n"
			+ "LPAD(\r\n"
			+ "(case when \r\n"
			+ "	( SELECT COUNT(SPS.ID_PAGO_SFPA) FROM SVT_PAGO_SFPA SPS WHERE REF_FOLIO_RECIBO IS NOT NULL AND SPS.ID_ESTATUS_PAGO=5) = 0\r\n"
			+ "then 1 else (SELECT COUNT(SPS.ID_PAGO_SFPA)+ 1 FROM SVT_PAGO_SFPA SPS WHERE REF_FOLIO_RECIBO IS NOT NULL AND SPS.ID_ESTATUS_PAGO=5)\r\n"
			+ "end ),7,'0')\r\n"
			+ "FROM\r\n"
			+ "DUAL\r\n"
			+ ")\r\n"
			+ "WHERE ID_PAGO_SFPA =#{idPagoSFPA}\r\n"
			+ "AND ID_PLAN_SFPA = #{idPlanSFPA}"
			)
	public int actualizarFolioPago( @Param("idUsuario")Integer idUsuario, 
			@Param("idPagoSFPA")Integer idPagoSFPA, @Param("idPlanSFPA")Integer idPlanSFPA);
	
	@Update(value = ""
			+ "UPDATE \r\n"
			+ "SVT_PAGO_SFPA \r\n"
			+ "SET \r\n"
			+ "ID_ESTATUS_PAGO = #{idEstatusPago},\r\n"
			+ "IND_TIPO_PAGO = #{out.idPlataforma},\r\n"
			+ "ID_USUARIO_MODIFICA = #{out.idUsuario},\r\n"
			+ "FEC_ACTUALIZACION = now()\r\n"
			+ "WHERE ID_PAGO_SFPA = #{out.idPagoSFPA}\r\n"
			+ "AND ID_PLAN_SFPA = #{out.idRegistro}"
			)
	public int actualizaEstatusPagoSFPA( @Param("out")PagoRequest pago, 
			@Param("idEstatusPago")Integer idEstatusPago);
	
	@Select("SELECT \r\n"
			+ "ifnull(ps.IMP_PRECIO,0) - ifnull(SUM(sps.IMP_MONTO_MENSUAL),0) as total\r\n"
			+ "FROM SVT_PAGO_SFPA sps\r\n"
			+ "JOIN SVT_PLAN_SFPA ps ON ps.ID_PLAN_SFPA = sps.ID_PLAN_SFPA\r\n"
			+ "where sps.IND_ACTIVO = 1\r\n"
			+ "AND sps.ID_ESTATUS_PAGO = 5\r\n"
			+ "ANd ps.ID_PLAN_SFPA = #{idPlan}"
			)
	public Double totalPagado(Integer idPlan);
	
	@Update(value = ""
			+ "UPDATE \r\n"
			+ "SVT_PLAN_SFPA \r\n"
			+ "SET ID_ESTATUS_PLAN_SFPA = #{idEstatusPlan},\r\n"
			+ "ID_USUARIO_MODIFICA = #{out.idUsuario},\r\n"
			+ "FEC_ACTUALIZACION = NOW()\r\n"
			+ "WHERE \r\n"
			+ "ID_PLAN_SFPA = #{out.idRegistro}"
			)
	public int actualizaEstatusPlan( @Param("out")PagoRequest pago, 
			@Param("idEstatusPlan")Integer idEstatusPlan);
}
