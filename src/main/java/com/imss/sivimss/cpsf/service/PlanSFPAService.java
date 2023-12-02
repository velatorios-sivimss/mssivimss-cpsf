package com.imss.sivimss.cpsf.service;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.cpsf.model.request.Contratante;
import com.imss.sivimss.cpsf.model.request.PlanSFPA;
import com.imss.sivimss.cpsf.utils.Response;

public interface PlanSFPAService {
	
	public Response<Object> consultaDetallePlanSfpa(String  cveUsuario);
	
	Response<Object> consultaTipoContratacion();
	
	Response<Object> consultaTipoPagoMensual();
	
	Response<Object> consultaPromotores();
	
	Response<Object> consultaPaquetes();
	
	Response<Object> consultaValidaAfiliado(Contratante contratanteRequest);
	
	Response<Object>  registrarLineaPlanSFPA(PlanSFPA planSFPARequest, Authentication authentication);
	
	Response<Object>  folioPlanSfpa(Integer idPlanSfpa);
	
	Response<Object> consultarPagoSfpa(Integer idPlanSfpa);
	
	Response<Object> consultaPlanSFPA(Integer idPlanSfpa);
	
	Response<Object> generaReporteConvenioPagoAnticipado(Integer idPlanSfpa, Authentication authentication);

}
