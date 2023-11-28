package com.imss.sivimss.cpsf.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.cpsf.model.request.Persona;
import com.imss.sivimss.cpsf.utils.Response;


public interface PeticionesService {
	Response<Object>  consultarById(Integer id, Authentication authentication)throws IOException;
	Response<Object> consultar(Authentication authentication)throws IOException;
	Response<Object> consultarPaginado(Map<String, Object> params, Authentication authentication)throws Throwable;
//	Response<Object> guardarDatos(Persona request, Authentication authentication) throws Throwable;
	Response<Object> actualizaDatos(Persona request, Authentication authentication) throws Throwable;
	Response<Object> borrarDatos(Persona request, Authentication authentication) throws Throwable;
	Response<Object> consultaMyBatis() throws Throwable;
	
}
