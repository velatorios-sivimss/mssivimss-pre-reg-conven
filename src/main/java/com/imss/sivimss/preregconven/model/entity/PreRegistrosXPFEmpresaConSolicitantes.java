package com.imss.sivimss.preregconven.model.entity;

import java.util.ArrayList;
import lombok.Data;

@Data
public class PreRegistrosXPFEmpresaConSolicitantes {

	private DetalleConvenioPFXEmpresa empresa;
	private ArrayList<DetalleConvenioPFXEmpresaSolicitantes> solicitantes;
	private ArrayList<DetalleConvenioPFXEmpresaBeneficiarios> beneficiarios;
	
}
