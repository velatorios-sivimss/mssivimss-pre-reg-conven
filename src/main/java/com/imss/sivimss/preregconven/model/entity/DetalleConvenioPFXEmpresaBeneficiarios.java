package com.imss.sivimss.preregconven.model.entity;

import lombok.Data;

@Data
public class DetalleConvenioPFXEmpresaBeneficiarios  {
    private Integer idBeneficiario;
    private Integer idPersona;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private int edad;
    private String parentesco;
    private String curp;
    private String rfc;
    private String correo;
    private String telefono;
    private String nombreArchivo;
    private Integer idContratante;
    private int docIne;
    private int docActa;
    private int idParentesco;
    private String fechaNaciemiento;
    private String otroSexo;
    private int idSexo;
    private int idPais;
    private int idEstado;
}
