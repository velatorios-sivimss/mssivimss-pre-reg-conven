package com.imss.sivimss.arquetipo.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActualizaConvenioPersonaPFDTO {
    private Integer idUsuario;
    private Integer idConvenioPF;
    private Integer idContratanteBeneficiario;
    private Integer idPaquete;
    private Integer idContraPaqPF;
    private Integer idContrantante;
    private String matricula;
    private String idPromotor;

    // persona
    private Integer idPersona;
    private String nombre;
    private String primerApe;
    private String segunApe;
    private String rfc;
    private String curp;
    private String idEnfermedad;
    private String otraEnfermedad;
    private Integer idBeneficiario;
    private Integer idParentesco;
    private String correo;
    private String telefono;
    private Integer idSexo;
    private String otroSexo;
    private String fechaNaciemiento;
    private String idEstado;
    private String idPais;

    // datos domicilio
    private Integer idDomicilio;
    private String calle;
    private String colonia;
    private String municipio;
    private String estado;

    private String numExt;
    private String numInt;
    private String cp;

    // documentos beneficiarios
    private String documento;
    private String nombreIne;
    private String nombreActa;
    private boolean validaIne;
    private boolean validaActa;
    private Integer idContratante;
    private boolean actualizaArchivo;

    // documentos contratante
    private Integer idValidaDocumento;
    private boolean validaCurp;
    private String nombreCurp;
    private boolean validaRfc;
    private String nombreRfc;
    private String archivoIne;
    private String archivoCurp;
    private String archivoRfc;

    // falta que piña me responda si puede o no homologar los request en caso de que
    // si se pueda aqui igual queda lo de empresa si no se crea un nuevo dto

}
