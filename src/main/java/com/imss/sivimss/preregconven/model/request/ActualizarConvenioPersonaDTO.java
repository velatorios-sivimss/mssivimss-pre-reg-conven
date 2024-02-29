package com.imss.sivimss.preregconven.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActualizarConvenioPersonaDTO {
    private Integer idConvenioPF;
    private Integer idContraPaqPF;
    private Integer idTipoContratacion;
    private Integer idEstado;
    private Integer idPromotor;
    private Integer idUsuario;
    private Integer idPaquete;
    private Integer idContratoConvenioPaquete;
    private Integer idContratante;
    private Integer indEnfermedad;
    private String otraEnfermedad;
    private Integer idPersona;
    private String cveMatricula;
    private Integer idDomicilio;
    private String calle;
    private String numExt;
    private String numInt;
    private String cp;
    private String colonia;
    private String municipio;
    private String estado;
    private Integer idValidaDocumento;
    private boolean validaIne;
    private String nombreIne;
    private boolean validaCurp;
    private String nombreCurp;
    private boolean validaRfc;
    private String nombreRfc;
    private String archivoIne;
    private String archivoCurp;
    private String archivoRfc;
    private Integer idPais;
    private String rfc;
    private String curp;
    private String nombre;
    private String primerApe;
    private String segunApe;
    //private String fechaNacimiento;
    private String telefono;
    private String correo;
    //private Integer sexo;
    //private String otroSexo;

}
