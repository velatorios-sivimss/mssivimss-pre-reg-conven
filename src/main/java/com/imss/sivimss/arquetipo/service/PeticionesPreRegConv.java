package com.imss.sivimss.arquetipo.service;

import com.imss.sivimss.arquetipo.model.request.Paginado;
import com.imss.sivimss.arquetipo.utils.Response;


public interface PeticionesPreRegConv {
	
	public Response<Object> obtenerPreRegistros(Paginado paginado );
	public Response<Object> obtenerPreRegistrosXPersona(Integer idPreReg);
	public Response<Object> obtenerPreRegistrosXEmpresa(Integer idPreReg);
	public Response<Object> catPaquetes();
	public Response<Object> catPromotores();
	public Response<Object> benefXEmpresa(Integer idPreReg);
	public Response<Object> titularSustituto(Integer idTitular);
	public Response<Object> beneficiarios(Integer idPreReg);
}
