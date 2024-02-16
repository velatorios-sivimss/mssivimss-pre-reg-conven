package com.imss.sivimss.arquetipo.model.request;

import lombok.Data;

@Data
public class PlanPABeneficiario {
	
	private Integer idBeneficiario;
	private String curp;
	private String rfc;
	private String matricula;
	private String nss;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private Integer idSexo;
	private String sexo;
	private String otroSexo;
	private String fecNacimiento;
	private Integer idPais;
	private String pais;
	private Integer idLugarNac;
	private String lugarNac;
	private String telFijo;
	private String telCelular;
	private String correo;
	private String calle;
	private String numExt;
	private String numInt;
	private String cp;
	private String colonia;
	private String municipio;
	private Integer idEstado;
	private String estado;
	private Integer idPersonaTitular;
	private Integer idDomicilio;

}
