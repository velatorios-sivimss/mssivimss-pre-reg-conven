package com.imss.sivimss.arquetipo.model.entity;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class PreRegistrosXPersonaConBeneficiarios {

	private PreRegistrosXPersona preRegistro;
	private ArrayList<BenefxPersona> beneficiarios;
	
	
}
