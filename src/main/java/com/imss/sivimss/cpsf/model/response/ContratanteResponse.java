package com.imss.sivimss.cpsf.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContratanteResponse {
	
	private Integer idTitular;
	
	private Integer idPersona;
	
	private String nomPersona;
	
	private String nomApellidoPaterno;
	
	private String nomApellidoMaterno;
	
	private String correo;

}
