package com.imss.sivimss.arquetipo.model.request;

import java.util.ArrayList;

import com.imss.sivimss.arquetipo.model.entity.DatosPersonaBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.DatosPersonaConvenio;

import lombok.Data;

@Data
public class ActualizarConvenioPersona  {
    private DatosPersonaConvenio convenio;
    private ArrayList<DatosPersonaBeneficiarios> beneficiarios;
}
