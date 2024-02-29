package com.imss.sivimss.preregconven.model.entity;

import lombok.Data;

@Data
public class DatosPersonaConvenio  {
    private Long idPersona;
    private Long idPais;
    private Long idEstado;
    private Long idDomicilio;
    private Long idPaquete;
    private Long idContraPaqPF;
    private int idConvenioPF;;
    
    // actualizarDatosContratante()
    private String nombre;
    private String primerApe;
    private String segunApe;
    private String curp;
    private String rfc;
    private String correo;
    private String telefono;
   
    // actualizarDomicilioContratante()
    private String calle;
    private String numExt;
    private String numInt;
    private String cp;
    private String colonia;
    private String municipio;
    private String estado;

    // actualizarPaqueteContratante()
    private boolean indEnfermedad;
    private String otraEnfermedad;
    
	public String getNombreCompleto (){
    
        return this.nombre + " " + this.primerApe + " " + this.segunApe;
    }
}
