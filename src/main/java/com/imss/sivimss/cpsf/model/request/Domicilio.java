package com.imss.sivimss.cpsf.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Domicilio {
	
	private Integer idDomicilio;
	
	private String desCalle;

	private String numExterior;

	private String numInterior;
	
	private Integer codigoPostal;

	private String desColonia;

	private String desMunicipio;

	private String desEstado;
	
	private Integer idUsuario;

	public Domicilio(Integer idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

}
