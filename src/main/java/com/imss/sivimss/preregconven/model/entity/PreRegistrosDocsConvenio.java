package com.imss.sivimss.preregconven.model.entity;

import java.util.Date;

import lombok.Data;

@Data
public class PreRegistrosDocsConvenio {

	private int idValidaDocumento;
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
	private int idConvenioPf;
	private int idUsuarioAlta;
	private Date fecAlta;
	private int idUsuarioBaja;
	private Date fecBaja;
	private int idUsuarioModifica;
	private Date fecActualizacion;

	
	
}
