package com.imss.sivimss.preregconven.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import com.imss.sivimss.preregconven.model.request.RequestFiltroPaginado;
import com.imss.sivimss.preregconven.utils.Response;


public interface PreRegConvService {
	
	public Response<Object> obtenerPreRegistros(RequestFiltroPaginado paginado, Authentication authentication ) throws IOException;
	public Response<Object> obtenerPreRegistrosXPersona(Integer idPreReg, Authentication authentication) throws IOException;
	public Response<Object> benefXPersona(Integer idBenef, Authentication authentication) throws IOException;
	public Response<Object> titularSustituto(Integer idTitular, Authentication authentication) throws IOException;
	public Response<Object> actDesactConvenioPer(Integer idPreReg, Authentication authentication) throws IOException;
	public Response<Object> guardaDocsConvenioXPersona(Integer idPreReg,MultipartFile[] archivo, Authentication authentication) throws IOException;
	
	public Response<Object> obtenerPreRegistrosXEmpresa(Integer idPreReg, Authentication authentication) throws IOException;
	public Response<Object> obtenerPreRegistrosPersonasEmpresa(Integer idPreReg, Authentication authentication) throws IOException;
	public Response<Object> benefXEmpresa(Integer idPreReg, Authentication authentication) throws IOException;
	public Response<Object> obtenerDocsEmpresa(Integer idPreReg, Authentication authentication) throws IOException;
	
	public Response<Object> catPaquetes(Authentication authentication) throws IOException;
	public Response<Object> catPromotores(Authentication authentication) throws IOException;
	public Response<Object> beneficiarios(Integer idPreReg, Authentication authentication) throws IOException;
}
