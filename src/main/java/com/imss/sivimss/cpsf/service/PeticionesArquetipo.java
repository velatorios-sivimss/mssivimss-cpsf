package com.imss.sivimss.cpsf.service;

import com.imss.sivimss.cpsf.model.request.Paginado;
import com.imss.sivimss.cpsf.model.request.PersonaNombres;
import com.imss.sivimss.cpsf.utils.Response;


public interface PeticionesArquetipo {
	
	public Response<Object> consultaUsandoQuerysNativas() ;
	public Response<Object> nuevoRegistroUsandoMappersObj( PersonaNombres persona);
	public Response<Object> actualizarRegistroUsandoMappersObj(PersonaNombres persona, int id );
	public Response<Object> paginadoGenerico(Paginado paginado );

}
