package com.imss.sivimss.arquetipo.model.entity;

import java.util.Date;

import lombok.Data;

@Data
public class PreRegistrosXPA {

	private int idPersona;
	private int idContratante;
	private int idDomicilio;
	private String folioConvenio;
	private String curp;
	private String rfc;
	private String matricula;
	private String nss;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private int idSexo;
	private Date fecNacimiento;
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
	private int idPaquete;
	private int numPagos;
	private String nomPaquete;
	private String titularSust;
	private int idTitularSust;
	private int beneficiario1;
	private int beneficiario2;
	private Boolean gestionPromotor;
	private int activo;
}
