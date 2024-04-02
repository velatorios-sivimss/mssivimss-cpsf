package com.imss.sivimss.cpsf.utils;

public class AppConstantes {
	
	public static final String CONSULTA = "0";
	
	public static final String NUMERO_DE_PAGINA = "0";
	public static final String TAMANIO_PAGINA = "10";
	public static final String ORDER_BY = "id";
	public static final String ORDER_DIRECTION = "asc";
	public static final String DATOS = "datos";
	
	public static final String TITULAR = "titular";
	public static final String TITULAR_SUBSTITUTO = "titular substituto";
	public static final String BENEFICIARIO_1 = "beneficiario 1";
	public static final String BENEFICIARIO_2 = "beneficiario 2";	
	
	public static final String AGREGADO_CORRECTAMENTE= "30"; 
	public static final String RUTA_NOMBRE_REPORTE = "rutaNombreReporte";
	public static final String TIPO_REPORTE = "tipoReporte";
	public static final String USR_CONTRASENIA= "registrarUsuario";
	public static final String CORREO_PAGO_EN_LINEA= "pagoEnLinea";

	public static final String FUNCIONALIDAD = "idFuncionalidad";
	public static final String SERVICIO = "servicio";
	public static final String CIRCUITBREAKER = "186"; // El servicio no responde, no permite más llamadas.
	public static final String EXITO = "Exito";
	public static final String OCURRIO_ERROR_GENERICO = "187";// Ocurrio un error al procesar tu solicitud.
	public static final String BAD_REQUEST_MENSAJE = "Datos incorrectos.";

	public static final String STATUSEXCEPTION = "status";
	public static final String EXPIREDJWTEXCEPTION = "expired";
	public static final String MALFORMEDJWTEXCEPTION = "malformed";
	public static final String UNSUPPORTEDJWTEXCEPTION = "unsupported";
	public static final String ILLEGALARGUMENTEXCEPTION = "illegalArgument";
	public static final String SIGNATUREEXCEPTION = "signature";
	public static final String FORBIDDENEXCEPTION = "forbidden";

	public static final String EXPIREDJWTEXCEPTION_MENSAJE = "Token expirado.";
	public static final String MALFORMEDJWTEXCEPTION_MENSAJE = "Token mal formado.";
	public static final String UNSUPPORTEDJWTEXCEPTION_MENSAJE = "Token no soportado.";
	public static final String ILLEGALARGUMENTEXCEPTION_MENSAJE = "Token vacío.";
	public static final String SIGNATUREEXCEPTION_MENSAJE = "Fallo la firma.";
	public static final String FORBIDDENEXCEPTION_MENSAJE = "No tiene autorización para realizar la solicitud.";

	private AppConstantes() {

	}

}
