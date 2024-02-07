package com.imss.sivimss.arquetipo.model.entity;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ActualizarDatosEmpresa  {
    private ArrayList<DatosEmpresaSolicitante> solicitantes;
    private DatosEmpresa empresa;
   
}
