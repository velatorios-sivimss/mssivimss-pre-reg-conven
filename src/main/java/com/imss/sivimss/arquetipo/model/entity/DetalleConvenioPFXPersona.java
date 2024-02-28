package com.imss.sivimss.arquetipo.model.entity;

import lombok.Data;

@Data
public class DetalleConvenioPFXPersona  {
	private String matricula;
    private String folioConvenio;
    private String rfc;
    private String curp;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String calle;
    private String numExt;
    private String numInt;
    private String cp;
    private String colonia;
    private String municipio;
    private String estado;
    private String pais;
    private int idPais;
    private String lugarNac;
    private int idLugarNac;
    private String correo;
    private String telCelular;
    private int idPaquete;
    private String tipoPaquete;
    private String enfermedadPre;
    private String otraEnfermedad;
	private int activo;
    private Long idPersona;
    private Long idEstado;
    private Long idDomicilio;
    private Long idContraPaqPF;
    private int docIne;
    private int docCurp;
    private int docRfc;
    private String fechaNaciemiento;
    private String otroSexo;
    private int idSexo;
    private int idPromotor;
    private int idContrantante;
    private int idValidaDocumento;
    private String nombreDocumentoINE;
    private String nombreDocumentoCURP;
    private String nombreDocumentoRFC;


}
