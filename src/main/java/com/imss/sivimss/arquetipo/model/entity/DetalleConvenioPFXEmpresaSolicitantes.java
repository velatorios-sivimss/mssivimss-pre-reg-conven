package com.imss.sivimss.arquetipo.model.entity;

import lombok.Data;

@Data
public class DetalleConvenioPFXEmpresaSolicitantes  {
    private int idPaquete;
    private String rfc;
    private String curp;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String calle;
    private String numExterior;
    private String numInterior;
    private String cp;
    private String colonia;
    private String municipio;
    private String estado;
    private String pais;
    private int idPais;
    private String lugarNac;
    private int idLugarNac;
    private String telefono;
    private String correo;
    private String tipoPaquete;
    private Integer idPersona;
    private Integer idDomicilio;
	
}
