package com.imss.sivimss.arquetipo.model.entity;

import java.util.Date;

import lombok.Data;

@Data
public class DetalleConvenioPFXPersona  {
	private int idPersona;
    private int idContratante;
    private int idDomicilio;
    private String folioConvenio;
    private String curp;
    private String rfc;
    private String matricula;
    private String nss;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String sexo;
    private String fecNacimiento;
    private String pais;
    private int idPais;
    private String lugarNacimiento;
    private int idLugarNac;
    private String telCelular;
    private String telFijo;
    private String correo;
    private String calle;
    private String numExt;
    private String numInt;
    private String cp;
    private String colonia;
    private String municipio;
    private String estado;
    private int idPaquete;
    private String nomPaquete;
    private int gestionPromotor;
	
}
