package com.imss.sivimss.arquetipo.model.entity;

import lombok.Data;

@Data
public class DetalleConvenioPFXEmpresa  {
    private String nombre;
    private String razonSocial;
    private String rfc;
    private int idPais;
    private String cp;
    private String colonia;
    private String estado;
    private String municipio;
    private String calle;
    private String numInterior;
    private String numExterior;
    private String telefono;
    private String correo;
    private int idConvenio;
    private int idEmpresa;
    private int idPromotor;
	private String tipoPaquete;
}
