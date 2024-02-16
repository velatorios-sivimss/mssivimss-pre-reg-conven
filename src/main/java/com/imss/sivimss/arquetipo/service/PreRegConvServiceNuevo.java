package com.imss.sivimss.arquetipo.service;

import com.imss.sivimss.arquetipo.utils.DatosRequest;
import com.imss.sivimss.arquetipo.utils.Response;

import java.io.IOException;

import org.springframework.security.core.Authentication;

public interface PreRegConvServiceNuevo {

	public Response<Object> obtenerPreRegistros(DatosRequest paginado);

	public Response<Object> preRegXConvenios(DatosRequest request);

	public Response<Object> preRegXConveniosDocs(DatosRequest request);

	public Response<Object> actDesactConvenio(DatosRequest request);

	public Response<Object> validarRfcCurpContratante(DatosRequest request);

	public Response<Object> actualizarDatosEmpresa(DatosRequest request, Authentication authentication);

	public Response<Object> actualizarDatosPersona(DatosRequest request, Authentication authentication);

	public Response<Object> actualizarDatosPA(DatosRequest request, Authentication authentication);

	/*
	 * public Response<Object> benefXPersona(Integer idBenef);
	 * public Response<Object> titularSustituto(Integer idTitular);
	 * 
	 * public Response<Object> guardaDocsConvenioXPersona(Integer
	 * idPreReg,MultipartFile[] archivo);
	 * 
	 * public Response<Object> obtenerPreRegistrosXEmpresa(Integer idPreReg);
	 * public Response<Object> obtenerPreRegistrosPersonasEmpresa(Integer idPreReg);
	 * public Response<Object> benefXEmpresa(Integer idPreReg);
	 * public Response<Object> obtenerDocsEmpresa(Integer idPreReg);
	 * 
	 * 
	 * public Response<Object> catPaquetes();
	 * public Response<Object> catPromotores();
	 * public Response<Object> beneficiarios(Integer idPreReg);
	 */
	public Response<Object> descargarDocumentos(DatosRequest request, Authentication authentication)throws IOException;
	
}
