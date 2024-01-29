package com.imss.sivimss.arquetipo.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.imss.sivimss.arquetipo.model.request.PersonaNombres;
import com.imss.sivimss.arquetipo.model.request.RequestFiltroPaginado;
import com.imss.sivimss.arquetipo.service.PreRegConvService;
import com.imss.sivimss.arquetipo.service.PreRegConvServiceNuevo;
import com.imss.sivimss.arquetipo.utils.LogUtil;
import com.imss.sivimss.arquetipo.utils.ProviderServiceRestTemplate;
import com.imss.sivimss.arquetipo.utils.Response;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/sivimss")
public class PreRegConvController {
	
	@Autowired
	private PreRegConvService pprc;

	@Autowired
	private PreRegConvServiceNuevo pprc2;

	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	@Autowired
	private LogUtil logUtil;

	private static final String CONSULTA = "consulta";
	private static final String INSERT = "insert";
	private static final String UPDATE = "update";
	

	
	@PostMapping("/buscar/preregistros")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackConsultaPaginada")
	@Retry(name = "msflujo", fallbackMethod = "fallbackConsultaPaginada")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> preregistros (@Validated @RequestBody RequestFiltroPaginado request, Authentication authentication) throws IOException {
		/* Consulta pagiada */
		Response<Object> response = pprc2.obtenerPreRegistros(request);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

	}
	
	@GetMapping("/buscar/{idFlujo}/{idConvenioPf}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@Retry(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> preRegXConvenios(@PathVariable Integer idFlujo, @PathVariable Integer idConvenioPf, 
			Authentication authentication) throws Throwable {
		/* Consulta Detalle 
		 * 
		 * 1 PA
		 * 2 PF Empresa
		 * 3 PF Persona
		*/
		Response<Object> response = pprc2.preRegXConvenios(idFlujo,idConvenioPf);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

	}

	@GetMapping("/buscar/docs/{idFlujo}/{idConvenioPf}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@Retry(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> preRegXConveniosDocs(@PathVariable Integer idFlujo, @PathVariable Integer idConvenioPf, 
			Authentication authentication) throws Throwable {
		/* Consulta Detalle 
		 * 
		 * 1 PA
		 * 2 PF Empresa
		 * 3 PF Persona
		*/
		Response<Object> response = pprc2.preRegXConveniosDocs(idFlujo,idConvenioPf);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

	}
	
	
	
	@PutMapping("/activar/desactivar/{idFlujo}/{idConvenioPf}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@Retry(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> actDesactConvenio(@PathVariable Integer idFlujo, @PathVariable Integer idConvenioPf, 
			Authentication authentication) throws Throwable {
		
		Response<Object> response = pprc2.actDesactConvenio(idFlujo, idConvenioPf);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

	}
	
	
	
	
	
	/* -------------FLUJOS REVALIDAR-------------------- 	*/

	@GetMapping("/buscar/empresa/{idPreReg}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@Retry(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> preRegXEmpresa(@PathVariable Integer idPreReg, 
			Authentication authentication) throws Throwable {
		
		Response<Object> response = pprc.obtenerPreRegistrosXEmpresa(idPreReg);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

	}


	@GetMapping("/buscar/empresa/personas/{idPreReg}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@Retry(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> preRegPersonasEmpresa(@PathVariable Integer idPreReg, 
			Authentication authentication) throws Throwable {
		
		Response<Object> response = pprc.obtenerPreRegistrosPersonasEmpresa(idPreReg);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

	}
	
	@GetMapping("/buscar/empresa/beneficiarios/{idPreReg}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@Retry(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> benefXEmpresa(@PathVariable Integer idPreReg, 
			Authentication authentication) throws Throwable {
		
		Response<Object> response = pprc.benefXEmpresa(idPreReg);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

	}

	@GetMapping("/buscar/empresa/docs/{idPreReg}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@Retry(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> obtenerDocsEmpresa(@PathVariable Integer idPreReg, 
			Authentication authentication) throws Throwable {		
		Response<Object> response = pprc.obtenerDocsEmpresa(idPreReg);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

	}
	
	
	
	
	

	@GetMapping("/buscar/paquetes")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackConsulta")
	@Retry(name = "msflujo", fallbackMethod = "fallbackConsulta")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> catPaquetes( 
			Authentication authentication) throws Throwable {
		
		Response<Object> response = pprc.catPaquetes();
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

	}
	

	@GetMapping("/buscar/titularSustituto/{idTitSust}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@Retry(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> titularSustituto(@PathVariable Integer idTitSust, 
			Authentication authentication) throws Throwable {
		Response<Object> response = pprc.titularSustituto(idTitSust);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@GetMapping("/buscar/benfPersona/{idBenef}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@Retry(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> benefXPersona(@PathVariable Integer idBenef, 
			Authentication authentication) throws Throwable {
		Response<Object> response = pprc.benefXPersona(idBenef);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@GetMapping("/buscar/promotores")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackConsulta")
	@Retry(name = "msflujo", fallbackMethod = "fallbackConsulta")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> promotores(Authentication authentication) throws Throwable {
		Response<Object> response = pprc.catPromotores();
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	

	

	@PutMapping("/guardar/documentos/{idPreReg}")
	@CircuitBreaker(name = "msflujo", fallbackMethod = "fallbackConsultaGenerica")
	@Retry(name = "msflujo", fallbackMethod = "fallbackGuardarDocs")
	@TimeLimiter(name = "msflujo")
	public CompletableFuture<Object> guardarDocs(@PathVariable Integer idPreReg, 
			Authentication authentication, MultipartFile[] archivo) throws Throwable {
		
		Response<Object> response = pprc.guardaDocsConvenioXPersona(idPreReg,archivo);
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));

	}
	
	/*
	 * 
	 * FallBack
	 * 
	 */
	
	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackConsultaPaginada(@RequestBody RequestFiltroPaginado paginado,Authentication authentication,
			CallNotPermittedException e) throws IOException {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA + " " + paginado,authentication);

		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackConsulta(Authentication authentication,
			CallNotPermittedException e) throws IOException {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA,authentication);

		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackInsert(@RequestBody PersonaNombres persona, Authentication authentication,
			CallNotPermittedException e) throws IOException {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),INSERT,authentication);

		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackUpdate(@RequestBody PersonaNombres persona,
			@PathVariable int id, Authentication authentication,
			CallNotPermittedException e) throws IOException {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),UPDATE,authentication);

		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}

	@SuppressWarnings("unused")
	private CompletableFuture<Object> fallbackConsultaGenerica(@PathVariable Integer idFlujo,@PathVariable Integer idConvenioPf,  Authentication authentication,
			CallNotPermittedException e) throws IOException {
		Response<?> response = providerRestTemplate.respuestaProvider(e.getMessage());
		 logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),e.getMessage(),CONSULTA,authentication);

		return CompletableFuture
				.supplyAsync(() -> new ResponseEntity<>(response, HttpStatus.valueOf(response.getCodigo())));
	}
	
}
