package com.imss.sivimss.preregconven.model.request;

import java.util.ArrayList;

import com.imss.sivimss.preregconven.model.entity.DatosPersonaBeneficiarios;
import com.imss.sivimss.preregconven.model.entity.DatosPersonaConvenio;

import lombok.Data;

@Data
public class ActualizarConvenioPersona  {
    private DatosPersonaConvenio convenio;
    private ArrayList<DatosPersonaBeneficiarios> beneficiarios;
}
