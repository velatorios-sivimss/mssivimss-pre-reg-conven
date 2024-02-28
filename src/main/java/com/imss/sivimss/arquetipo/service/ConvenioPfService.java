package com.imss.sivimss.arquetipo.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.arquetipo.utils.DatosRequest;
import com.imss.sivimss.arquetipo.utils.Response;

public interface ConvenioPfService {

	public Response<Object> actualizarPFPersona(DatosRequest request, Authentication authentication)throws IOException;
	public Response<Object> actualizarBeneficiario(DatosRequest request, Authentication authentication)throws IOException;
	public Response<Object> actualizarPlanPFEmpresa(DatosRequest request, Authentication authentication)throws IOException;
	public Response<Object> actualizarersonaPFEmpresa(DatosRequest request, Authentication authentication)throws IOException;
	public Response<Object> actualizarEstatusConvenioPf(DatosRequest request, Authentication authentication)throws IOException;
}
