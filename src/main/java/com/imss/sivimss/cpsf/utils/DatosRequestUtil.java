package com.imss.sivimss.cpsf.utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.imss.sivimss.cpsf.model.request.Contratante;
import com.imss.sivimss.cpsf.model.request.Domicilio;
import com.imss.sivimss.cpsf.model.request.UsuarioDto;
import com.imss.sivimss.cpsf.model.response.DetallePlanSFPAResponse;
import com.imss.sivimss.cpsf.model.response.PlanSFPAResponse;

@Component
public class DatosRequestUtil {

	
	public String getDatosJson (DatosRequest datosRequest) {
		return datosRequest.getDatos().get(AppConstantes.DATOS).toString();
	}
	
	public UsuarioDto getUserData(Authentication authentication) {
		Gson gson = new Gson();
		return gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
	}
	
	public static String validarSiEsNull(String valor) {
		if (valor != null) {
			return valor;
		}
		return "";
	}
	
	public static String condicion (Contratante contratanteRequest) {
		StringBuilder where = new StringBuilder();
		if(contratanteRequest.getCurp() != null && !contratanteRequest.getCurp().isEmpty()) {
			where.append(" AND SP.CVE_CURP = '").append(contratanteRequest.getCurp()).append(ConsultaConstantes.COMILLA_SIMPLE);
		}
		if(contratanteRequest.getRfc() != null && !contratanteRequest.getRfc().isEmpty()) {
			where.append(" AND SP.CVE_RFC = '").append(contratanteRequest.getRfc()).append(ConsultaConstantes.COMILLA_SIMPLE);
		}
		if (contratanteRequest.getIne() != null ) {
			where.append(" AND SP.NUM_INE = '").append(contratanteRequest.getIne()).append(ConsultaConstantes.COMILLA_SIMPLE);
		}
		where.append(" ORDER BY SP.ID_PERSONA DESC LIMIT 1");
		
		return where.toString();
	}
	
	public static  PlanSFPAResponse generarDetallePlan(DetallePlanSFPAResponse detallePlanSFPA) throws SQLException {
		PlanSFPAResponse planSFPAResponse = new PlanSFPAResponse();
		List<Contratante> titularesBeneficiarios = new ArrayList<>(); 
		Contratante contratanteRequest = new Contratante();
		Domicilio cp = new Domicilio ();
		planSFPAResponse.setIdPlanSfpa(detallePlanSFPA.getIdPlanSfpa());
		planSFPAResponse.setNumFolioPlanSFPA(detallePlanSFPA.getNumFolioPlanSfpa());
		planSFPAResponse.setIdTipoContratacion(detallePlanSFPA.getIdTipoContratacion());
		planSFPAResponse.setIdPaquete(detallePlanSFPA.getIdPaquete());
		planSFPAResponse.setIdTipoPagoMensual(detallePlanSFPA.getIdTipoPagoMensual());
		planSFPAResponse.setIndTitularSubstituto(detallePlanSFPA.getIndTitularSubstituto());
		planSFPAResponse.setIndModificarTitularSubstituto(detallePlanSFPA.getIndModificarTitularSubstituto());
		planSFPAResponse.setIndTipoPagoMensual(detallePlanSFPA.getNumPago()== 0?false:true);
		planSFPAResponse.setIdPromotor(detallePlanSFPA.getIdPromotor());
		planSFPAResponse.setIndPromotor(detallePlanSFPA.getIndPromotor());
		planSFPAResponse.setIndActivo(detallePlanSFPA.getIndActivo());
		planSFPAResponse.setIdVelatorio(detallePlanSFPA.getIdVelatorio());
		planSFPAResponse.setDesIdVelatorio(detallePlanSFPA.getDesVelatorio());
		planSFPAResponse.setFecIngreso(detallePlanSFPA.getFecIngreso());
		planSFPAResponse.setNumPago(detallePlanSFPA.getNumPago());
		contratanteRequest.setPersona(AppConstantes.TITULAR);
		contratanteRequest.setIdPersona(detallePlanSFPA.getIdPersona());
		contratanteRequest.setRfc(detallePlanSFPA.getRfc());
		contratanteRequest.setCurp(detallePlanSFPA.getCurp());
		contratanteRequest.setMatricula(detallePlanSFPA.getMatricula());
		contratanteRequest.setNss(detallePlanSFPA.getNss());
		contratanteRequest.setNomPersona(detallePlanSFPA.getNomPersona());
		contratanteRequest.setPrimerApellido(detallePlanSFPA.getPrimerApellido());
		contratanteRequest.setSegundoApellido(detallePlanSFPA.getSegundoApellido());
		contratanteRequest.setSexo(detallePlanSFPA.getSexo());
		contratanteRequest.setOtroSexo(detallePlanSFPA.getOtroSexo());
		contratanteRequest.setFecNacimiento(detallePlanSFPA.getFecNacimiento());
		contratanteRequest.setIdPais(detallePlanSFPA.getIdPais());
		contratanteRequest.setIdEstado(detallePlanSFPA.getIdEstado());
		contratanteRequest.setTelefono(detallePlanSFPA.getTelefono());
		contratanteRequest.setTelefonoFijo(detallePlanSFPA.getTelefonoFijo());
		contratanteRequest.setCorreo(detallePlanSFPA.getCorreo());
		contratanteRequest.setTipoPersona(detallePlanSFPA.getTipoPersona());
		contratanteRequest.setIne(detallePlanSFPA.getIne());
		cp.setIdDomicilio(detallePlanSFPA.getIdDomicilio());
		cp.setDesCalle(detallePlanSFPA.getDesCalle());
		cp.setNumExterior(detallePlanSFPA.getNumExterior());
		cp.setNumInterior(detallePlanSFPA.getNumInterior());
		cp.setCodigoPostal(detallePlanSFPA.getCodigoPostal());
		cp.setDesColonia(detallePlanSFPA.getDesColonia());
		cp.setDesMunicipio(detallePlanSFPA.getDesMunicipio());
		cp.setDesEstado(detallePlanSFPA.getDesEstado());
		contratanteRequest.setCp(cp);
		titularesBeneficiarios.add(contratanteRequest);
		if(detallePlanSFPA.getIdPersona2() != null && detallePlanSFPA.getIdDomicilio2() != null){
			Contratante contratanteRequest2 = new Contratante();
			Domicilio cp2 = new Domicilio ();
			contratanteRequest2.setPersona(AppConstantes.TITULAR_SUBSTITUTO);
			contratanteRequest2.setIdPersona(detallePlanSFPA.getIdPersona2());
			contratanteRequest2.setRfc(detallePlanSFPA.getRfc2());
			contratanteRequest2.setCurp(detallePlanSFPA.getCurp2());
			contratanteRequest2.setMatricula(detallePlanSFPA.getMatricula2());
			contratanteRequest2.setNss(detallePlanSFPA.getNss2());
			contratanteRequest2.setNomPersona(detallePlanSFPA.getNomPersona2());
			contratanteRequest2.setPrimerApellido(detallePlanSFPA.getPrimerApellido2());
			contratanteRequest2.setSegundoApellido(detallePlanSFPA.getSegundoApellido2());
			contratanteRequest2.setSexo(detallePlanSFPA.getSexo2());
			contratanteRequest2.setOtroSexo(detallePlanSFPA.getOtroSexo2());
			contratanteRequest2.setFecNacimiento(detallePlanSFPA.getFecNacimiento2());
			contratanteRequest2.setIdPais(detallePlanSFPA.getIdPais2());
			contratanteRequest2.setIdEstado(detallePlanSFPA.getIdEstado2());
			contratanteRequest2.setTelefono(detallePlanSFPA.getTelefono2());
			contratanteRequest2.setTelefonoFijo(detallePlanSFPA.getTelefonoFijo2());
			contratanteRequest2.setCorreo(detallePlanSFPA.getCorreo2());
			contratanteRequest2.setTipoPersona(detallePlanSFPA.getTipoPersona2());
			contratanteRequest2.setIne(detallePlanSFPA.getIne());
			cp2.setIdDomicilio(detallePlanSFPA.getIdDomicilio2());
			cp2.setDesCalle(detallePlanSFPA.getDesCalle2());
			cp2.setNumExterior(detallePlanSFPA.getNumExterior2());
			cp2.setNumInterior(detallePlanSFPA.getNumInterior2());
			cp2.setCodigoPostal(detallePlanSFPA.getCodigoPostal2());
			cp2.setDesColonia(detallePlanSFPA.getDesColonia2());
			cp2.setDesMunicipio(detallePlanSFPA.getDesMunicipio2());
			cp2.setDesEstado(detallePlanSFPA.getDesEstado2());
			contratanteRequest2.setCp(cp2);
			titularesBeneficiarios.add(contratanteRequest2);
		}
		if(detallePlanSFPA.getIdPersona3() != null && detallePlanSFPA.getIdDomicilio3() != null){
			Contratante contratanteRequest3 = new Contratante();
			Domicilio cp3 = new Domicilio ();
			contratanteRequest3.setPersona(AppConstantes.BENEFICIARIO_1);
			contratanteRequest3.setIdPersona(detallePlanSFPA.getIdPersona3());
			contratanteRequest3.setRfc(detallePlanSFPA.getRfc3());
			contratanteRequest3.setCurp(detallePlanSFPA.getCurp3());
			contratanteRequest3.setMatricula(detallePlanSFPA.getMatricula3());
			contratanteRequest3.setNss(detallePlanSFPA.getNss3());
			contratanteRequest3.setNomPersona(detallePlanSFPA.getNomPersona3());
			contratanteRequest3.setPrimerApellido(detallePlanSFPA.getPrimerApellido3());
			contratanteRequest3.setSegundoApellido(detallePlanSFPA.getSegundoApellido3());
			contratanteRequest3.setSexo(detallePlanSFPA.getSexo3());
			contratanteRequest3.setOtroSexo(detallePlanSFPA.getOtroSexo3());
			contratanteRequest3.setFecNacimiento(detallePlanSFPA.getFecNacimiento3());
			contratanteRequest3.setIdPais(detallePlanSFPA.getIdPais3());
			contratanteRequest3.setIdEstado(detallePlanSFPA.getIdEstado3());
			contratanteRequest3.setTelefono(detallePlanSFPA.getTelefono3());
			contratanteRequest3.setTelefonoFijo(detallePlanSFPA.getTelefonoFijo3());
			contratanteRequest3.setCorreo(detallePlanSFPA.getCorreo3());
			contratanteRequest3.setTipoPersona(detallePlanSFPA.getTipoPersona3());
			contratanteRequest3.setIne(detallePlanSFPA.getIne3());
			cp3.setIdDomicilio(detallePlanSFPA.getIdDomicilio3());
			cp3.setDesCalle(detallePlanSFPA.getDesCalle3());
			cp3.setNumExterior(detallePlanSFPA.getNumExterior3());
			cp3.setNumInterior(detallePlanSFPA.getNumInterior3());
			cp3.setCodigoPostal(detallePlanSFPA.getCodigoPostal3());
			cp3.setDesColonia(detallePlanSFPA.getDesColonia3());
			cp3.setDesMunicipio(detallePlanSFPA.getDesMunicipio3());
			cp3.setDesEstado(detallePlanSFPA.getDesEstado3());
			contratanteRequest3.setCp(cp3);
			titularesBeneficiarios.add(contratanteRequest3);
		}
		if(detallePlanSFPA.getIdPersona4() != null && detallePlanSFPA.getIdDomicilio4() != null){
			Contratante contratanteRequest4 = new Contratante();
			Domicilio cp4 = new Domicilio ();
			contratanteRequest4.setPersona(AppConstantes.BENEFICIARIO_2);
			contratanteRequest4.setIdPersona(detallePlanSFPA.getIdPersona4());
			contratanteRequest4.setRfc(detallePlanSFPA.getRfc4());
			contratanteRequest4.setCurp(detallePlanSFPA.getCurp4());
			contratanteRequest4.setMatricula(detallePlanSFPA.getMatricula4());
			contratanteRequest4.setNss(detallePlanSFPA.getNss4());
			contratanteRequest4.setNomPersona(detallePlanSFPA.getNomPersona4());
			contratanteRequest4.setPrimerApellido(detallePlanSFPA.getPrimerApellido4());
			contratanteRequest4.setSegundoApellido(detallePlanSFPA.getSegundoApellido4());
			contratanteRequest4.setSexo(detallePlanSFPA.getSexo4());
			contratanteRequest4.setOtroSexo(detallePlanSFPA.getOtroSexo4());
			contratanteRequest4.setFecNacimiento(detallePlanSFPA.getFecNacimiento4());
			contratanteRequest4.setIdPais(detallePlanSFPA.getIdPais4());
			contratanteRequest4.setIdEstado(detallePlanSFPA.getIdEstado4());
			contratanteRequest4.setTelefono(detallePlanSFPA.getTelefono4());
			contratanteRequest4.setTelefonoFijo(detallePlanSFPA.getTelefonoFijo4());
			contratanteRequest4.setCorreo(detallePlanSFPA.getCorreo4());
			contratanteRequest4.setTipoPersona(detallePlanSFPA.getTipoPersona4());
			contratanteRequest4.setIne(detallePlanSFPA.getIne4());
			cp4.setIdDomicilio(detallePlanSFPA.getIdDomicilio4());
			cp4.setDesCalle(detallePlanSFPA.getDesCalle4());
			cp4.setNumExterior(detallePlanSFPA.getNumExterior4());
			cp4.setNumInterior(detallePlanSFPA.getNumInterior4());
			cp4.setCodigoPostal(detallePlanSFPA.getCodigoPostal4());
			cp4.setDesColonia(detallePlanSFPA.getDesColonia4());
			cp4.setDesMunicipio(detallePlanSFPA.getDesMunicipio4());
			cp4.setDesEstado(detallePlanSFPA.getDesEstado4());
			contratanteRequest4.setCp(cp4);
			titularesBeneficiarios.add(contratanteRequest4);
		}
		planSFPAResponse.setTitularesBeneficiarios(titularesBeneficiarios);
		return planSFPAResponse;
	}
}
