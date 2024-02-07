package com.imss.sivimss.arquetipo.model.entity;

import lombok.Data;

@Data
public class DetalleConvenioPFXEmpresaBeneficiarios  {
    private Integer idBeneficiario;
    private Integer idPersona;
    private String nombre;
    private int edad;
    private String parentesco;
    private String curp;
    private String rfc;
    private String correo;
    private String telefono;
    private Integer idContratante;
}
