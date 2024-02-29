package com.imss.sivimss.preregconven.model.entity;

import java.util.ArrayList;
import lombok.Data;

@Data
public class PreRegistrosXPFPersonaConBeneficiarios {

	
	private DetalleConvenioPFXPersona detalleConvenioPFModel;
	private ArrayList<DetalleConvenioPFXPersonaBeneficiarios> beneficiarios;
	
}
