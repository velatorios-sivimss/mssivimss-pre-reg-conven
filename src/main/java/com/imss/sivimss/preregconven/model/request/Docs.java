package com.imss.sivimss.preregconven.model.request;

import lombok.Data;

@Data
public class Docs {
	private int idConvenioPf;
	
	private boolean indIneAfiliado;
	private String refUbicacionIne;
	
	private boolean indCurp;
	private String refUbicacionCurp;
	
	private boolean indRfc;
	private String refUbicacionRfc;
	
	private boolean indActaNacimiento;
	private String refUbicacionActaNacimiento;
	
	private boolean indIneBeneficiario;
	private String refUbicacionIneBeneficiario;

	private String refDocIneAfiliado;
	private String refDocCurpAfiliado;
	private String refDocRfcAfiliado;
	
	
	
	
}
