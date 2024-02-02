package com.imss.sivimss.cpsf.model.request;

import lombok.Data;

@Data
public class PagoBitacoraRequest {

	private Integer idPagoBitacora;
	private Integer idRegistro;
	private Integer idFlujoPagos;
	private Integer idVelatorio;
	private String fechaRegistro;
	private String nomContratante;
	private String folio;
	private Double importeRegistro;
	private Integer estatusPago;
	private Integer idUsuarioAlta;
	private Integer idPlataforma;
}
