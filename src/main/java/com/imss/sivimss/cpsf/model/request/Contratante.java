package com.imss.sivimss.cpsf.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contratante extends PersonaPlanSFPA{

	private Integer idContratante;
	
	private Integer idTitularBeneficiarios;
	
	private Integer idBeneficiario1;
	
	private Integer idBeneficiario2;
	
	private String matricula;

	private Domicilio cp;
	
}
