package com.imss.sivimss.arquetipo.model.request;

import lombok.Data;

@Data
public class PlanPAData {
	
	private Integer idPagonMensual;
	private Integer idPaquete;
	private Integer idTitularSust;
	private Boolean indTitularSut;
	private Integer idConvenio;
	private String curp;
	private String rfc;
	private String nss;
	private String nombre;
	private String primApellido;
	private String segApellido;
	private String numSex;
	private String oreoSex;
	private String fecNac;
	private Integer idPais;
	private Integer idEstado;
	private String telefono;
	private String telefonoFij;
	private String correo;
	private Integer idPersonaContratante;
	private Integer idDomicilio;
	private String calle;
	private String numExt;
	private String numInt;
	private String cp;
	private String colonia;
	private String municipio;
	private String estado;
	
}
