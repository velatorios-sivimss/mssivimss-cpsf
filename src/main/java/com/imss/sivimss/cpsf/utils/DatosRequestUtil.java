package com.imss.sivimss.cpsf.utils;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.imss.sivimss.cpsf.model.request.Contratante;
import com.imss.sivimss.cpsf.model.request.UsuarioDto;

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
}
