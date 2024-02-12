package com.imss.sivimss.cpsf.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDto {

	private Integer idUsuario;
	private String idEstado;
	private Integer idRol;
	private String desRol;
	private Integer idContratante;
	private String idPais;
	private String cveUsuario;
	private String cveMatricula;
	private String nombre;
	private String curp;
    private Integer idPersona;
    private Integer idVelatorio;
    private Integer idDelegacion;
    private String contrasenia;
    private Integer idOficina;
    private Integer indActivo;
    
}
