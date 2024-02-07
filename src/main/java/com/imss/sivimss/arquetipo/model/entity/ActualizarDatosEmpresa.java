package com.imss.sivimss.arquetipo.model.entity;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ActualizarDatosEmpresa  {
    private DatosEmpresa empresa;
    private ArrayList<DatosEmpresaBeneficiarios> beneficiarios;
    private ArrayList<DatosEmpresaSolicitante> solicitantes;
}
