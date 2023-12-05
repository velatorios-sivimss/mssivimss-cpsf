package com.imss.sivimss.cpsf.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.imss.sivimss.cpsf.model.request.Contratante;
import com.imss.sivimss.cpsf.model.request.PlanSFPA;
import com.imss.sivimss.cpsf.model.request.UsuarioDto;
import com.imss.sivimss.cpsf.service.PlanSFPAService;
import com.imss.sivimss.cpsf.utils.Response;
import com.imss.sivimss.cpsf.utils.LogUtil;
import com.imss.sivimss.cpsf.utils.ProviderServiceRestTemplate;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/linea-plan-sfpa")
public class PlanSFPAController {
	
	@Autowired
	private PlanSFPAService planSFPAService;
	
	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	@Autowired
	private LogUtil logUtil;

	private static final String RESILENCIA = " Resilencia  ";
	private static final String CONSULTA = "consulta";
	private static final String INSERT = "insert";
	
	@PostMapping("/consulta-detalle-linea-plan-sfpa")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> consultaDetalleLineaPlanSfpa(@RequestBody UsuarioDto usuarioDto, Authentication authentication)	throws Throwable {
		Response<Object> response = planSFPAService.consultaDetallePlanSfpa(usuarioDto.getCveUsuario());
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	@GetMapping("/consulta-tipo-contratacion")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> consultaTipoContratacion(Authentication authentication)	throws Throwable {
		Response<Object> response = planSFPAService.consultaTipoContratacion();
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	@GetMapping("/consulta-tipo-pago-mensual")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> consultaTipoPagoMensual(Authentication authentication)	throws Throwable {
		Response<Object> response = planSFPAService.consultaTipoPagoMensual();
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	@GetMapping("/consulta-promotores")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> consultaPromotores(Authentication authentication)	throws Throwable {
		Response<Object> response = planSFPAService.consultaPromotores();
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	@GetMapping("/consulta-paquetes")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> consultaPaquetes(Authentication authentication)	throws Throwable {
		Response<Object> response = planSFPAService.consultaPaquetes();
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}	
	
	@PostMapping("/consulta-valida-afiliado")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenerico")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> consultaPaquetes(@RequestBody Contratante contratanteRequest, Authentication authentication)	throws Throwable {
		Response<Object> response = planSFPAService.consultaValidaAfiliado(contratanteRequest);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	@PostMapping("/crear")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenericoInsert")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenericoInsert")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> insertaLineaPlanSFPA(@RequestBody PlanSFPA planSFPARequest , Authentication authentication)	throws Throwable {
		Response<Object> response = planSFPAService.registrarLineaPlanSFPA(planSFPARequest, authentication);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	@PostMapping("/reporte-contrato")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenericoInsert")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenericoInsert")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> generaReporteConvenioPagoAnticipado(@RequestBody PlanSFPA planSFPARequest , Authentication authentication)	throws Throwable {
		Response<Object> response = planSFPAService.generaReporteConvenioPagoAnticipado(planSFPARequest.getIdPlanSfpa(), authentication);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	@PostMapping("/ver-detalle-linea-plan-sfpa")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackGenericoInsert")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGenericoInsert")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> verDetallePlanSfpa(@RequestBody PlanSFPA planSFPARequest , Authentication authentication)	throws Throwable {
		Response<Object> response = planSFPAService.verDetallePlanSfpa(planSFPARequest.getIdPlanSfpa());
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	/**
	 * fallbacks generico
	 * 
	 * @return respuestas
	 * @throws IOException 
	 */
	CompletableFuture<Object> fallbackGenerico(@RequestBody UsuarioDto usuarioDto, Authentication authentication,
			CallNotPermittedException e) throws Throwable {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),RESILENCIA, CONSULTA,authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	CompletableFuture<Object> fallbackGenerico(Authentication authentication,
			RuntimeException e) throws Throwable {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),RESILENCIA, CONSULTA,authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	CompletableFuture<Object> fallbackGenericoInsert(@RequestBody PlanSFPA planSFPARequest, Authentication authentication,
			NumberFormatException e) throws Throwable {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),RESILENCIA, INSERT,authentication);
		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

}
