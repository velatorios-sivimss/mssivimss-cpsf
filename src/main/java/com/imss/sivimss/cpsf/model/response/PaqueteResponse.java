package com.imss.sivimss.cpsf.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaqueteResponse {
	
	private Integer idPaquete;
	private String nomPaquete;
	private String descPaquete;
	private Object [] serviciosPaquetes;

}
