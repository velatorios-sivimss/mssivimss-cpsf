package com.imss.sivimss.cpsf.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetallePlanSFPAResponse extends DetalleExtPlanSFPAResponse{
	
	private Integer idPlanSfpa;
	private String numFolioPlanSfpa;
	private Integer idTipoContratacion;
	private Integer idPaquete;
	private Integer idTipoPagoMensual;
	private Integer indTitularSubstituto;
	private Integer indModificarTitularSubstituto;
	private Integer idPromotor;
	private Integer indPromotor;
	private String fecIngreso;
	private Integer indActivo;
	private Integer idVelatorio;
	private String desIdVelatorio;
	private Integer numPago;
	private Integer idPersona;
	private String rfc;
	private String curp;
	private String matricula;
	private String nss;
	private String nomPersona;
	private String primerApellido;
	private String segundoApellido;
	private Integer sexo;
	private String otroSexo;
	private String fecNacimiento;
	private Integer idPais;
	private Integer idEstado;
	private String telefono;
	private String telefonoFijo;
	private String correo;
	private String tipoPersona;
	private String ine;
	private Integer idDomicilio;
	private String desCalle;
	private String numExterior;
	private String numInterior;
	private Integer codigoPostal;
	private String desColonia;
	private String desMunicipio;
	private String desEstado;
	private String desVelatorio;
	private Integer idPersona2;
	private String rfc2;
	private String curp2;
	private String matricula2;
	private String nss2;
	private String nomPersona2;
	private String primerApellido2;
	private String segundoApellido2;
	private Integer sexo2;
	private String otroSexo2;
	private String fecNacimiento2;
	private Integer idPais2;
	private Integer idEstado2;
	private String telefono2;
	private String telefonoFijo2;
	private String correo2;
	private String tipoPersona2;
	private String ine2;
	private Integer idDomicilio2;
	private String desCalle2;
	private String numExterior2;
	private String numInterior2;
	private Integer codigoPostal2;
	private String desColonia2;
	private String desMunicipio2;
	private String desEstado2;
}
