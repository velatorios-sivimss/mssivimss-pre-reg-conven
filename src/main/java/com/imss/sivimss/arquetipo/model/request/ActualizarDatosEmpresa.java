package com.imss.sivimss.arquetipo.model.request;

import java.util.ArrayList;

import com.imss.sivimss.arquetipo.model.entity.DatosEmpresa;
import com.imss.sivimss.arquetipo.model.entity.DatosEmpresaBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.DatosEmpresaSolicitante;

import lombok.Data;

@Data
public class ActualizarDatosEmpresa  {
    private DatosEmpresa empresa;
    private ArrayList<DatosEmpresaBeneficiarios> beneficiarios;
    private ArrayList<DatosEmpresaSolicitante> solicitantes;
}
