package com.imss.sivimss.arquetipo.model.entity;

import lombok.Data;

@Data
public class DetalleConvenioPFXPersonaBeneficiarios  {
    private Long idPersona;
    private Long idBeneficiario;
    private String nombre;
    private int edad;
    private String parentesco;
    private String curp;
    private String rfc;
    private String correo;
    private String telefono;
    private int docIne;
    private int docActa;
    private Long idContratante;
	
}
