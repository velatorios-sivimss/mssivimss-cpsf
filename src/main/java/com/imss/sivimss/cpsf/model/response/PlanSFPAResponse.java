package com.imss.sivimss.cpsf.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imss.sivimss.cpsf.model.request.Contratante;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanSFPAResponse {
	
	private Integer idPlanSfpa;
	private String numFolioPlanSFPA;
	private Integer idTipoContratacion;
	private Integer idPaquete;
	private Integer idTipoPagoMensual;
	private Integer desTipoPagoMensual;
	private Integer indTitularSubstituto;
	private Integer indModificarTitularSubstituto;
	private Integer indPromotor;
	private Integer idPromotor;
	private Integer idVelatorio;
	private String desIdVelatorio;
	private Integer idEstatusPlanSfpa;
	private String desEstatusPlanSfpa;
	private Integer indActivo;
	private Boolean indTipoPagoMensual;
	private String fecIngreso;
	private Integer numPago;
	private List<Contratante> titularesBeneficiarios;

}
