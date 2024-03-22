package com.imss.sivimss.preregconven.model.request;

import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class Flujos {
	
	@Min(value=0,message = "Pagina Minima 0")
	private Integer idFLujo;
	
	@Min(value=1,message = "Tamanio Minimo 1")
	private Integer idConvenio;
	
	@Min(value=1,message = "Tamanio Minimo 1")
	private Integer seccion;
}
