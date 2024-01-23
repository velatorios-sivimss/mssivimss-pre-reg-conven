package com.imss.sivimss.arquetipo.service.impl;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.arquetipo.configuration.MyBatisConfig;
import com.imss.sivimss.arquetipo.configuration.mapper.Consultas;
import com.imss.sivimss.arquetipo.configuration.mapper.ConvenioPA;
import com.imss.sivimss.arquetipo.configuration.mapper.ConvenioPF;
import com.imss.sivimss.arquetipo.model.entity.BenefXPA;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresa;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresaBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresaBeneficiariosDocs;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresaSolicitantes;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXPersona;
import com.imss.sivimss.arquetipo.model.entity.PreRegistrosXPA;
import com.imss.sivimss.arquetipo.model.entity.PreRegistrosXPAConBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.PreRegistrosXPFEmpresaConSolicitantes;
import com.imss.sivimss.arquetipo.model.request.RequestFiltroPaginado;
import com.imss.sivimss.arquetipo.service.PreRegConvServiceNuevo;
import com.imss.sivimss.arquetipo.service.beans.BeanQuerys;
import com.imss.sivimss.arquetipo.utils.AppConstantes;
import com.imss.sivimss.arquetipo.utils.PaginadoUtil;
import com.imss.sivimss.arquetipo.utils.Response;

@Service
public class PreRegConvServiceNuevoImpl implements PreRegConvServiceNuevo {
	@Autowired
	private BeanQuerys query;

	@Autowired
	private MyBatisConfig myBatisConfig;
	
	@Autowired
	private PaginadoUtil paginadoUtil;
	
	private static final String ERROR = "ERROR"; 
	@Override
	public Response<Object> obtenerPreRegistros(RequestFiltroPaginado request) {
		Page<Map<String, Object>> objetoPaginado = paginadoUtil.paginado(request.getPagina(), request.getTamanio(), query.queryPreRegistros(request));
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, objetoPaginado);
	}

	@Override
	public Response<Object> preRegXConveniosDocs(Integer idFlujo, Integer idConvenioPf) {
		if ( idFlujo == null ){
			return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
		}
		
		switch (idFlujo) {
			case 1:
				// PreRegistrosXPAConBeneficiarios preRegistro = consultaConveniosPA(idConvenioPf);
				// localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/buscar/1/30
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, null);
			case 2:
				ArrayList<DetalleConvenioPFXEmpresaBeneficiariosDocs> detalleConvenioPFEmpresa = consultaConveniosPFEmpresaDocs(idConvenioPf);
				// localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/buscar/docs/2/9
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, detalleConvenioPFEmpresa);
			case 3:
				// DetalleConvenioPFXPersona detalleConvenioPFPersona = consultaConveniosPFPersona(idConvenioPf);
				// localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/buscar/3/14
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, null );

		}

		return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);

	}

	@Override
	public Response<Object> preRegXConvenios(Integer idFlujo, Integer idConvenioPf) {
		if ( idFlujo == null ){
			return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
		}
		
		switch (idFlujo) {
			case 1:
				PreRegistrosXPAConBeneficiarios preRegistro = consultaConveniosPA(idConvenioPf);
				// localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/buscar/1/30
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, preRegistro);
			case 2:
			PreRegistrosXPFEmpresaConSolicitantes detalleConvenioPFEmpresa = consultaConveniosPFEmpresa(idConvenioPf);
				// localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/buscar/2/2
				// localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/buscar/2/9
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, detalleConvenioPFEmpresa);
			case 3:
				DetalleConvenioPFXPersona detalleConvenioPFPersona = consultaConveniosPFPersona(idConvenioPf);
				// localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/buscar/3/14
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, detalleConvenioPFPersona);

		}

		return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);

	}

	public PreRegistrosXPAConBeneficiarios consultaConveniosPA( Integer idConvenioPf ){
		PreRegistrosXPA consultaPreRegistrosXPA = new PreRegistrosXPA();
		PreRegistrosXPAConBeneficiarios preRegistro = new PreRegistrosXPAConBeneficiarios();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();

		try(SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPA conveniosPA = session.getMapper(ConvenioPA.class);
			try {
				consultaPreRegistrosXPA = conveniosPA.consultaDetalleConvenioPA(idConvenioPf);
					preRegistro.setPreRegistro(consultaPreRegistrosXPA);

					if ( consultaPreRegistrosXPA != null ){
						BenefXPA beneficiarioPA1 = conveniosPA.consultaBeneficiariosConvenioPA(consultaPreRegistrosXPA.getBeneficiario1());
						BenefXPA beneficiarioPA2 = conveniosPA.consultaBeneficiariosConvenioPA(consultaPreRegistrosXPA.getBeneficiario2());

						ArrayList<BenefXPA> beneficiarios = new ArrayList<>();
						beneficiarios.add(beneficiarioPA1);
						beneficiarios.add(beneficiarioPA2);
						preRegistro.setBeneficiarios(beneficiarios);
					}

				
			} catch (Exception e) {
				return null;
			}
		}

		return preRegistro;
	}

	public PreRegistrosXPFEmpresaConSolicitantes consultaConveniosPFEmpresa ( Integer idConvenioPf ){
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		DetalleConvenioPFXEmpresa detalleConvenioPFModel = null;
		ArrayList<DetalleConvenioPFXEmpresaSolicitantes> solicitantes = new ArrayList<>();
		PreRegistrosXPFEmpresaConSolicitantes convenioPFEmpresa = new PreRegistrosXPFEmpresaConSolicitantes();
		ArrayList<DetalleConvenioPFXEmpresaBeneficiarios> beneficiarios = new ArrayList<>();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPF convenios = session.getMapper(ConvenioPF.class);
			try {
				detalleConvenioPFModel = convenios.consultaDetalleConvenioXEmpresa(idConvenioPf);
				solicitantes = convenios.consultaDetalleConvenioXEmpresaSolicitantes(idConvenioPf);
				beneficiarios = convenios.consultaDetalleConvenioXEmpresaBeneficiarios(idConvenioPf);

				convenioPFEmpresa.setEmpresa(detalleConvenioPFModel != null ? detalleConvenioPFModel : new DetalleConvenioPFXEmpresa());
				convenioPFEmpresa.setSolicitantes(solicitantes != null ? solicitantes : new ArrayList<DetalleConvenioPFXEmpresaSolicitantes>() );
				convenioPFEmpresa.setBeneficiarios(beneficiarios != null ? beneficiarios : new ArrayList<DetalleConvenioPFXEmpresaBeneficiarios>());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return convenioPFEmpresa;
	}

	public ArrayList<DetalleConvenioPFXEmpresaBeneficiariosDocs> consultaConveniosPFEmpresaDocs ( Integer idConvenioPf ){
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		DetalleConvenioPFXEmpresa detalleConvenioPFModel = null;
		ArrayList<DetalleConvenioPFXEmpresaSolicitantes> solicitantes = new ArrayList<>();
		PreRegistrosXPFEmpresaConSolicitantes convenioPFEmpresa = new PreRegistrosXPFEmpresaConSolicitantes();
		ArrayList<DetalleConvenioPFXEmpresaBeneficiariosDocs> beneficiarios = new ArrayList<>();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPF convenios = session.getMapper(ConvenioPF.class);
			try {
				detalleConvenioPFModel = convenios.consultaDetalleConvenioXEmpresa(idConvenioPf);
				solicitantes = convenios.consultaDetalleConvenioXEmpresaSolicitantes(idConvenioPf);
				beneficiarios = convenios.consultaDetalleConvenioXEmpresaBeneficiariosDocs(idConvenioPf);
				/* 
				convenioPFEmpresa.setEmpresa(detalleConvenioPFModel != null ? detalleConvenioPFModel : new DetalleConvenioPFXEmpresa());
				convenioPFEmpresa.setSolicitantes(solicitantes != null ? solicitantes : new ArrayList<DetalleConvenioPFXEmpresaSolicitantes>() );
				convenioPFEmpresa.setBeneficiarios(beneficiarios != null ? beneficiarios : new ArrayList<DetalleConvenioPFXEmpresaBeneficiarios>());
				*/
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return beneficiarios;
	}

	public DetalleConvenioPFXPersona consultaConveniosPFPersona ( Integer idConvenioPf ){
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		DetalleConvenioPFXPersona detalleConvenioPFModel = null;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPF convenios = session.getMapper(ConvenioPF.class);
			try {
				detalleConvenioPFModel = convenios.consultaDetalleConvenioXPersona(idConvenioPf);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return detalleConvenioPFModel;
	}
}
