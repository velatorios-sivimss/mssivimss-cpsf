package com.imss.sivimss.cpsf.configuration.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imss.sivimss.cpsf.model.response.ConPFResponse;

public interface ConvenioPFMapper {
	
	@Select("SELECT \r\n"
			+ "ID_CONVENIO_PF AS idConvenioPF, \r\n"
			+ "FEC_INICIO AS fecInicio \r\n"
			+ "FROM \r\n"
			+ "SVT_CONVENIO_PF \r\n"
			+ "WHERE \r\n"
			+ "ID_CONVENIO_PF = #{idConvenioPF} \r\n"
			+ "LIMIT 1"
			)
	public ConPFResponse selectDatos(Integer idConvenioPF);
	
	@Update(value = ""
			+ "UPDATE SVT_CONVENIO_PF \r\n"
			+ "SET \r\n"
			+ "	ID_ESTATUS_CONVENIO = 2, \r\n"
			+ "	FEC_ACTUALIZACION = NOW(), \r\n"
			+ "	ID_USUARIO_MODIFICA = #{idUsuario} \r\n"
			+ "WHERE ID_CONVENIO_PF = #{idConvenioPF} \r\n"
			)
	public int actualizarRegistroObj(@Param("idConvenioPF")Integer idConvenioPF, @Param("idUsuario")Integer idUsuario);
	
	@Select("SELECT sp.REF_CORREO as correo\r\n"
			+ "FROM SVT_USUARIOS su \r\n"
			+ "inner join SVC_PERSONA sp on su.ID_PERSONA = sp.ID_PERSONA \r\n"
			+ "WHERE su.ID_USUARIO = #{idUsuario}  "
			)
	public String correoUsuario(Integer idUsuario);
}
