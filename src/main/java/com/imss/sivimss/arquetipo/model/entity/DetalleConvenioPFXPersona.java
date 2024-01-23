package com.imss.sivimss.arquetipo.model.entity;

import java.util.Date;

import lombok.Data;

@Data
public class DetalleConvenioPFXPersona  {
	private String matricula;
    private String rfc;
    private String curp;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private int idPersona;
    private int idContratante;
    private int idDomicilio;
    private String folioConvenio;
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
	
}
