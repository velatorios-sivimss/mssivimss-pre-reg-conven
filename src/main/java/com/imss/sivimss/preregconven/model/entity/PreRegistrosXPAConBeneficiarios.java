package com.imss.sivimss.preregconven.model.entity;

import java.util.ArrayList;
import lombok.Data;

@Data
public class PreRegistrosXPAConBeneficiarios {

	private PreRegistrosXPA preRegistro;
	private ArrayList<BenefXPA> beneficiarios;
	private BenefXPA sustituto;
	
	
}
