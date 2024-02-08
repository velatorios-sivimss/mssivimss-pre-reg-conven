package com.imss.sivimss.arquetipo.model.entity;

import lombok.Data;

@Data
public class DatosPersonaBeneficiarios  {
    private Long idPersona;
    private Long idBeneficiario;
    private String idParentesco;
    private String nombre;
    private String curp;
    private String rfc;
    private String correo;
    private String telefono;
    // private Long idContratante;
	
}
