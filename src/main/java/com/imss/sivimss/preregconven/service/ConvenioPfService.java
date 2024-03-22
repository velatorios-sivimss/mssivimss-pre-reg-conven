package com.imss.sivimss.preregconven.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.preregconven.utils.DatosRequest;
import com.imss.sivimss.preregconven.utils.Response;

public interface ConvenioPfService {

	public Response<Object> actualizarPFPersona(DatosRequest request, Authentication authentication)throws IOException;
	public Response<Object> actualizarBeneficiario(DatosRequest request, Authentication authentication)throws IOException;
	public Response<Object> actualizarPlanPFEmpresa(DatosRequest request, Authentication authentication)throws IOException;
	public Response<Object> actualizarersonaPFEmpresa(DatosRequest request, Authentication authentication)throws IOException;
	public Response<Object> actualizarEstatusConvenioPf(DatosRequest request, Authentication authentication)throws IOException;
}
