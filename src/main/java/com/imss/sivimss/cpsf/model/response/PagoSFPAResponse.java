package com.imss.sivimss.cpsf.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagoSFPAResponse {
	
	private Integer idPagoSFPA;
	private String fechaParcialidad;
	private Double importeMensual;
	private Double importeAcumulado;
	private String estatusPago;
	private String noPagos;
	private String velatorio;
	private Integer validaPago;
	private Integer idPlanSFPA;
	private Integer idEstatus;
	private Double importePagado;
	private String folioRecibo;
	

}
