package com.imss.sivimss.arquetipo.service;

import org.springframework.stereotype.Service;

import com.imss.sivimss.arquetipo.model.request.RequestFiltroPaginado;
import com.imss.sivimss.arquetipo.utils.Response;


public interface PreRegConvServiceNuevo {
	
	public Response<Object> obtenerPreRegistros(RequestFiltroPaginado paginado );
	public Response<Object> preRegXConvenios(Integer idFlujo, Integer idPreReg);
	public Response<Object> preRegXConveniosDocs(Integer idFlujo, Integer idPreReg);
	public Response<Object> actDesactConvenio(Integer idFlujo,Integer idPreReg);
	
	/*
	public Response<Object> benefXPersona(Integer idBenef);
	public Response<Object> titularSustituto(Integer idTitular);
	
	public Response<Object> guardaDocsConvenioXPersona(Integer idPreReg,MultipartFile[] archivo);
	
	public Response<Object> obtenerPreRegistrosXEmpresa(Integer idPreReg);
	public Response<Object> obtenerPreRegistrosPersonasEmpresa(Integer idPreReg);
	public Response<Object> benefXEmpresa(Integer idPreReg);
	public Response<Object> obtenerDocsEmpresa(Integer idPreReg);
	
	
	public Response<Object> catPaquetes();
	public Response<Object> catPromotores();
	public Response<Object> beneficiarios(Integer idPreReg);
	 */
}
