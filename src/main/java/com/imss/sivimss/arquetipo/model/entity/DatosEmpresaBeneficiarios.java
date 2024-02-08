package com.imss.sivimss.arquetipo.model.entity;

import lombok.Data;

@Data
public class DatosEmpresaBeneficiarios {
    private Integer idBeneficiario;
    private Integer idParentesco;
    private Integer idPersona;
    private String nombre;
    private String primerApe;
    private String segunApe;
    private String rfc;
    private String curp;
    private String telefonoSol;
    private String correoSol;

    // actualiza documentos
    private Integer idUsuario;
    private boolean validaIne;
    private String archivoIne;
    private boolean validaActa;
    private String archivoActa;
    private String archivoBeneficiario;
    private Integer idConvenio;

}
