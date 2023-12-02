package com.imss.sivimss.cpsf.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagoSFPA {
	
	private Integer idPagoSfpa;
	private Integer idPlanSfpa;
	private Integer idEstatusPagoSfpa;
	private Double monMensual;
	private String fecParcialidad;
	private Integer indActivo = 1;
	private Integer idUsuario;

}
