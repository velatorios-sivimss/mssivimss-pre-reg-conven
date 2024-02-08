package com.imss.sivimss.arquetipo.model.entity;

import lombok.Data;

@Data
public class BenefXPA {

	private Long idBeneficiario;
	private String curp;
	private String rfc;
	private String matricula;
	private String nss;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private int idSexo;
	private String sexo;
	private String otroSexo;
	private String fecNacimiento;
	private int idPais;
	private String pais;
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
	private int idEstado;
	private String estado;
	private Long idPersonaTitular;
	private Long idDomicilio;
}
