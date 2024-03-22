package com.imss.sivimss.preregconven.model.response;

import lombok.Data;

@Data
public class ResponseContratanteRfcCurp {

	private int idConvenio;
	private String curp;
	private String rfc;
	private boolean curpDuplicada;
	private boolean rfcDuplicado;
}
