package com.imss.sivimss.cpsf.configuration.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imss.sivimss.cpsf.model.response.RenConPFResponse;

public interface RenConvenioPFMapper {
	
	@Select("SELECT \r\n"
			+ "ID_RENOVACION_CONVENIO_PF AS idRenConvenioPF, \r\n"
			+ "FEC_INICIO AS fecInicio \r\n"
			+ "FROM \r\n"
			+ "SVT_RENOVACION_CONVENIO_PF \r\n"
			+ "WHERE \r\n"
			+ "ID_RENOVACION_CONVENIO_PF = #{idRenConvenioPF} \r\n"
			+ "LIMIT 1"
			)
	public RenConPFResponse selectDatos(Integer idRenConvenioPF);
	
	@Update(value = ""
			+ "UPDATE SVT_RENOVACION_CONVENIO_PF \r\n"
			+ "SET \r\n"
			+ "	ID_ESTATUS = 2, \r\n"
			+ "	FEC_ACTUALIZACION = NOW(), \r\n"
			+ "	ID_USUARIO_MODIFICA = #{idUsuario} \r\n"
			+ "WHERE ID_RENOVACION_CONVENIO_PF = #{idRenConvenioPF} \r\n"
			)
	public int actualizarRegistroObj(@Param("idRenConvenioPF")Integer idRenConvenioPF, @Param("idUsuario")Integer idUsuario);
}
