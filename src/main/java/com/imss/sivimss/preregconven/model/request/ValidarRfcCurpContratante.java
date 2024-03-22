package com.imss.sivimss.preregconven.model.request;

import lombok.Data;

@Data
public class ValidarRfcCurpContratante {
	
	private String rfc;
	private String curp;
	private Integer idConvenio;
	private Integer idFlujo;
}
