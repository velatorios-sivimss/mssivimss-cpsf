package com.imss.sivimss.cpsf.model.request;

import lombok.Data;

@Data
public class MotorPagoRequest {
	
	private String lugarExpedicion;
	private String fecExpedicion;
    private String cveCliente;
	private String nomUsuario;
	
}
