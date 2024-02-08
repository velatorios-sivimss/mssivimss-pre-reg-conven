package com.imss.sivimss.arquetipo.model.entity;

import java.util.Date;

import lombok.Data;

@Data
public class PreRegistrosXPA {

	private Long idPersona;
	private Long idContratante;
	
	private String folioConvenio;
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
	private String pais;
	private String idPais;
	private String lugarNac;
	private String idLugarNac;
	private String telCelular;
	private String telFijo;
	private String correo;
	private String calle;
	private String numExt;
	private String numInt;
	private String cp;
	private String colonia;
	private String municipio;
	private String estado;
	private Long idPaquete;
	private int numPagos;
	private String nomPaquete;
	private String titularSust;
	private Long idTitularSust;
	private Long beneficiario1;
	private Long beneficiario2;
	private Boolean gestionPromotor;
	private int activo;
	private Long idPersonaContratante;
	private Long idDomicilio;
}
