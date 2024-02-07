package com.imss.sivimss.cpsf.model.response;

import lombok.Data;

@Data
public class ComPagoResponse {
	
	private Integer idPagoLinea;
	private String claveCliente;
	private String nomUsuario;
	private String numCon;
	private String conPago;
	private Double impPagado;
	private String referencia;
	private String numAprobacion;
	private String folioPago;
	private String numTarjeta;
	private String emisorTarjeta;
	private String fecTransaccion;
	private Integer idDelegacion;
	private String nomDelegacion;
	private Integer idVelatorio;
	private String nomVelatorio;
	
}
