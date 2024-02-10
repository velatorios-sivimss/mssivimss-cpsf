package com.imss.sivimss.cpsf.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PagoRequest {
	
	private Integer idPagoLinea;
	
	@Min(value=0,message = "El id debe ser mayor a 0")
	private Integer idRegistro;
	
	@Min(value=1,message = "El id debe ser mayor a 0")
	private Integer idFlujoPagos;
	
	@Min(value=1,message = "El id debe ser mayor a 0")
	private Integer idVelatorio;
	
	private Integer idCliente;

    private String folio;
	
	@Min(value=0,message = "El importe debe ser mayor a 0")
	private Double importe;
	
	@NotBlank(message = "El titular de la tarjeta no puede ser vacío")
    private String nomTitular;
	
	@NotBlank(message = "El numero de la tarjeta no puede ser vacío")
    private String numTarjeta;
	
	@NotBlank(message = "La referencia no puede ser vacía")
    private String referencia;
	
	@NotBlank(message = "El numero de aprobacion no puede ser vacío")
    private String numAprobacion;
	
	@NotBlank(message = "El folio del pago no puede ser vacío")
    private String folioPago;
	
	@Min(value=3, message = "El id del Metodo de Pago no puede ser vacío")
    private Integer idMetodoPago;
	
	private String emisorTarjeta;
	
	@NotBlank(message = "La fecha de transaccion no puede ser vacía")
    private String fecTransaccion;
	
	@NotBlank(message = "El Nombre del Contratante no puede ser vacío")
    private String nomContratante;
	
	private Integer idUsuario;
	
	private Integer idPagoSFPA;
	
	private Integer idPlataforma;
	
}
