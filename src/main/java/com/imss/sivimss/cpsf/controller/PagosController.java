package com.imss.sivimss.cpsf.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imss.sivimss.cpsf.model.request.PagoRequest;
import com.imss.sivimss.cpsf.service.PagoService;
import com.imss.sivimss.cpsf.utils.LogUtil;
import com.imss.sivimss.cpsf.utils.ProviderServiceRestTemplate;
import com.imss.sivimss.cpsf.utils.Response;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/pago/online")
public class PagosController {
	
	@Autowired
	private PagoService pagoService;

	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	@Autowired
	private LogUtil logUtil;

	private static final String CONSULTA = "consulta";
	private static final String INSERT = "insert";
	
	@PostMapping("/crear")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackInsert")
	@Retry(name = "msflujo", fallbackMethod = "fallbackInsert")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> realizarPago(@RequestBody PagoRequest pago, Authentication authentication)	throws Throwable {
		Response<Object> response = pagoService.crear(pago, authentication);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	@GetMapping("/obtener/{id}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackConsulta")
	@Retry(name = "msflujo", fallbackMethod = "fallbackConsulta")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> obetenerPago(@PathVariable("id") @Min(1) int id, Authentication authentication)	throws Throwable {
		Response<Object> response = pagoService.obtener(id);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
	/*
	 * 
	 * FallBack
	 * 
	 */
	

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackConsulta(Authentication authentication,
			CallNotPermittedException e) throws IOException {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA,authentication);

		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackInsert(@RequestBody PagoRequest pago, Authentication authentication,
			CallNotPermittedException e) throws IOException {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),INSERT,authentication);

		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

}
