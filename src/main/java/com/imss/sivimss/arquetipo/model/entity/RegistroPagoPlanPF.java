package com.imss.sivimss.arquetipo.model.entity;

import lombok.Data;

@Data
public class RegistroPagoPlanPF {

	private Integer idConvenioPf;
	private Integer idFlujo;
	private int idVelatorio;
	private String fecOds;
	private String nomContratante;
	private String cveFolio;
	private Double importe;
	private Integer cvdEstatusPago;
	private Integer idUsuarioAlta;
	
	
}
