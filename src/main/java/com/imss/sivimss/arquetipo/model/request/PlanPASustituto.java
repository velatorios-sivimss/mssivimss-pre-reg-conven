package com.imss.sivimss.arquetipo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlanPASustituto {
	
	private String calle;
	private String numExt;
	private String numInt;
	private String colonia;
	private String municipio;
	private String estado;
	private Integer idDomicilio;
	private Integer idPersonaTitular;
	private String nss;
	private String curp;
	private String rfc;
	private String nombre;
	private String primerApe;
	private String segunApe;
	private String sexo;
	private String fechaNac;
	private Integer idPais;
	private String telefonoFijo;
	private String telefono;
	private String correo;
	private Integer idEstado;
	private String otroSexo;
	
}
