package com.imss.sivimss.arquetipo.model.entity;

import lombok.Data;

@Data
public class DatosEmpresaSolicitante  {
    private String rfc;
    private String curp;
    private String nombre;
	private String primerApe;
    private String segunApe;
    private String telefonoSol;
    private String correoSol;
    private Integer idPaisSolicitante;
    private Integer idPersona;
    private Integer idEstadoSolicitante;
    
    private Integer cp;
    private String colonia;
    private String estado;
    private String municipio;
    private String calle;
    private String numInterior;
    private String numExterior;
    private Integer idDomicilio;
   
   

	
}
