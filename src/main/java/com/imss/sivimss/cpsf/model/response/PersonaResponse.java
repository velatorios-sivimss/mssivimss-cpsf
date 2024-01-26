package com.imss.sivimss.cpsf.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonaResponse {
	
	private Integer idContratante;
	
	private Integer idTitularBeneficiarios;
	
	private Integer idPersona;
	
	private Integer idDomicilio;

}
