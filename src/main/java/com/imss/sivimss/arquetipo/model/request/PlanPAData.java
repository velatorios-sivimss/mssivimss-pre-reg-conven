package com.imss.sivimss.arquetipo.model.request;

import lombok.Data;

@Data
public class PlanPAData {
	
	private int idPagonMensual;
	private int idPaquete;
	private int idTitularSust;
	private boolean indTitularSut;
	private int idConvenio;
	private String curp;
	private String rfc;
	private String nss;
	private String nombre;
	private String primApellido;
	private String segApellido;
	private String numSex;
	private String oreoSex;
	private String fecNac;
	private int idPais;
	private int idEstado;
	private String telefono;
	private String telefonoFij;
	private String correo;
	private int idPersonaContratante;
	private int idDomicilio;
	private String calle;
	private String numExt;
	private String numInt;
	private String cp;
	private String colonia;
	private String municipio;
	private String estado;
	
}
