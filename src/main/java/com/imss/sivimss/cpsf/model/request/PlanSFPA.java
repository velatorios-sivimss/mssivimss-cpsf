package com.imss.sivimss.cpsf.model.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanSFPA {
	
	private Integer idPlanSfpa;
	private String numFolioPlanSfpa;
	private Integer idTipoContratacion;
	private Integer idPaquete;
	private Double monPrecio;
	private Integer idTipoPagoMensual;
	private Integer numPagoMensual;
	private Integer indTitularSubstituto;
	private Integer indModificarTitularSubstituto;
	private Integer indPromotor;
	private Integer idPromotor;
	private Integer idVelatorio;
	private Integer idEstatusPlanSfpa = 8;
	private Integer indActivo;
	private Boolean indTipoPagoMensual;
	private List<Contratante> titularesBeneficiarios;
	private Integer idUsuario;
	private Integer idTitular;
	private Integer idSubtitular;
	private Integer idBeneficiario1;
	private Integer idBeneficiario2;

}
