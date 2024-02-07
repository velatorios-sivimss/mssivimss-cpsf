package com.imss.sivimss.cpsf.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.cpsf.model.request.PagoRequest;
import com.imss.sivimss.cpsf.utils.Response;


public interface PagoService {
	
	public Response<Object> crear( PagoRequest pago, Authentication authentication) throws IOException;
	public Response<Object> obtener( int idPagoLinea, Authentication authentication ) throws IOException;

}
