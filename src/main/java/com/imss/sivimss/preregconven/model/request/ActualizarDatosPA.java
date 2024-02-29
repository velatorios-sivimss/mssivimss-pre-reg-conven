package com.imss.sivimss.preregconven.model.request;

import lombok.Data;

@Data
public class ActualizarDatosPA  {

	private PlanPAData plan;
	private PlanPASustituto titularSustituto;
	private PlanPABeneficiario beneficiario1;
	private PlanPABeneficiario beneficiario2;
    
}
