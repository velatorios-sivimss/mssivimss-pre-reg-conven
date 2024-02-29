package com.imss.sivimss.preregconven.model.request;

import javax.validation.constraints.Min;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestFiltroPaginado {	
	
	@Min(value=0,message = "Pagina Minima 0")
	private Integer pagina;
	
	@Min(value=1,message = "Tamanio Minimo 1")
	private Integer tamanio;

	@Min(value=1,message = "Tamanio Minimo 1")
	private Integer idVelatorio;

	private String convenioPF;
	
	private String convenioPSFPA;
	
	private String rfc;
	
}
