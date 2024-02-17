package com.imss.sivimss.arquetipo.model.request;

import lombok.Data;

@Data
public class ActualizarDatosPA  {

	private PlanPAData plan;
	private PlanPASustituto titularSustituto;
	private PlanPABeneficiario beneficiario1;
	private PlanPABeneficiario beneficiario2;
    
}
