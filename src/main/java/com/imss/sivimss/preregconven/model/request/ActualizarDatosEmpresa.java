package com.imss.sivimss.preregconven.model.request;

import java.util.ArrayList;

import com.imss.sivimss.preregconven.model.entity.DatosEmpresa;
import com.imss.sivimss.preregconven.model.entity.DatosEmpresaBeneficiarios;
import com.imss.sivimss.preregconven.model.entity.DatosEmpresaSolicitante;

import lombok.Data;

@Data
public class ActualizarDatosEmpresa  {
    private DatosEmpresa empresa;
    private ArrayList<DatosEmpresaBeneficiarios> beneficiarios;
    private ArrayList<DatosEmpresaSolicitante> solicitantes;
}
