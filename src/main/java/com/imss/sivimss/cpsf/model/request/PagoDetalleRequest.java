package com.imss.sivimss.cpsf.model.request;

import lombok.Data;

@Data
public class PagoDetalleRequest {

	private Integer idPagoDetalle;
	private Integer idPagoBitacora;
	private Integer idMetodoPago;
	private Double importe;
	private String numAutorizacion;
	private String fecPago;
	private Integer estatusPago;
	private Integer idUsuarioAlta;
	
}
