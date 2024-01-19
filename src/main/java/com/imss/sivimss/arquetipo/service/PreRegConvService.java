package com.imss.sivimss.arquetipo.service;

import java.util.Map;

import com.imss.sivimss.arquetipo.model.request.RequestFiltroPaginado;
import com.imss.sivimss.arquetipo.utils.Response;


public interface PreRegConvService {
	
	public Response<Object> obtenerPreRegistros(RequestFiltroPaginado paginado );
	public Response<Object> obtenerPreRegistrosXPersona(Integer idPreReg);
	public Response<Object> benefXPersona(Integer idBenef);
	public Response<Object> titularSustituto(Integer idTitular);
	public Response<Object> actDesactConvenioPer(Integer idPreReg);
	
	public Response<Object> obtenerPreRegistrosXEmpresa(Integer idPreReg);
	public Response<Object> obtenerPreRegistrosPersonasEmpresa(Integer idPreReg);
	public Response<Object> benefXEmpresa(Integer idPreReg);
	public Response<Object> obtenerDocsEmpresa(Integer idPreReg);
	
	
	public Response<Object> catPaquetes();
	public Response<Object> catPromotores();
	public Response<Object> beneficiarios(Integer idPreReg);
}
