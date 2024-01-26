package com.imss.sivimss.arquetipo.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.imss.sivimss.arquetipo.configuration.MyBatisConfig;
import com.imss.sivimss.arquetipo.configuration.mapper.Consultas;
import com.imss.sivimss.arquetipo.model.entity.BenefXPA;
import com.imss.sivimss.arquetipo.model.entity.PreRegistrosXPA;
import com.imss.sivimss.arquetipo.model.entity.PreRegistrosXPAConBeneficiarios;
import com.imss.sivimss.arquetipo.model.request.RequestFiltroPaginado;
import com.imss.sivimss.arquetipo.service.PreRegConvService;
import com.imss.sivimss.arquetipo.service.beans.BeanQuerys;
import com.imss.sivimss.arquetipo.utils.AppConstantes;
import com.imss.sivimss.arquetipo.utils.PaginadoUtil;
import com.imss.sivimss.arquetipo.utils.Response;



@Service
public class PreRegConvServiceImpl implements PreRegConvService {

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
	public Response<Object>  obtenerPreRegistrosXEmpresa(Integer idPreReg) {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryPreRegistrosXEmpresa(idPreReg));
			} catch (Exception e) {
				e.printStackTrace();
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}


	@Override
	public Response<Object> obtenerPreRegistrosPersonasEmpresa(Integer idPreReg) {
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryPreRegPersonasEmpresa(idPreReg));
			} catch (Exception e) {
				e.printStackTrace();
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}
	

	@Override
	public Response<Object>  obtenerDocsEmpresa(Integer idPreReg) {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryDocsEmpresa(idPreReg));
			} catch (Exception e) {
				e.printStackTrace();
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}	
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}
	
	
	
	
	
	@Override
	public Response<Object>  obtenerPreRegistrosXPersona(Integer idPreReg /* idConvenio  */) {
		/*
		Este servicio obtiene el pre registro de una persona con sus 2 beneficiarios
		*/ 
		PreRegistrosXPA consultaPreRegistroXPersona = new PreRegistrosXPA();
		BenefXPA consultaBenefXPA1 = new BenefXPA();
		BenefXPA consultaBenefXPA2 = new BenefXPA();
		ArrayList<BenefXPA> beneficiarios = new ArrayList<>();
		PreRegistrosXPAConBeneficiarios preRegistro = new PreRegistrosXPAConBeneficiarios();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				consultaPreRegistroXPersona = consultas.selectPreRegistrosXPersona(query.queryPreRegistrosXPersona(idPreReg));
				preRegistro.setPreRegistro(consultaPreRegistroXPersona);

				if ( consultaPreRegistroXPersona != null ){
					consultaBenefXPA1 =consultas.selectBenefxPersona(query.queryBenefxPersona(consultaPreRegistroXPersona.getBeneficiario1()));
					consultaBenefXPA2 =consultas.selectBenefxPersona(query.queryBenefxPersona(consultaPreRegistroXPersona.getBeneficiario2()));
					
					/* Si llegan null se deben setear objetos instanciados vacios */
					
					beneficiarios.add(consultaBenefXPA1);
					beneficiarios.add(consultaBenefXPA2);
					preRegistro.setBeneficiarios(beneficiarios);
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, preRegistro);
	}

	public Response<Object> guardaDocsConvenioXPersona(Integer idPreReg,MultipartFile[] archivo){
		/* Este Servicio se encarga de cargar los docs de un pre registro */
		
		PreRegistrosXPAConBeneficiarios preRegistro = new PreRegistrosXPAConBeneficiarios();

		

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, preRegistro);
	}
	
	private String convertirABase64(MultipartFile file) {
        try {
            byte[] fileContent = file.getBytes();
            String base64String = Base64.getEncoder().encodeToString(fileContent);
            return base64String;
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores, devuelve una cadena vacía o maneja la excepción según sea necesario
            return "";
        }
    }

	@Override
	public Response<Object>  catPaquetes() {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryCatPaquetes());
			} catch (Exception e) {
				e.printStackTrace();
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object>  benefXEmpresa(Integer idPreReg) {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryBenefXEmpresa(idPreReg));
			} catch (Exception e) {
				e.printStackTrace();
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}	
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}
	

	
	
	
	@Override
	public Response<Object> titularSustituto(Integer idTitular) {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryTitularSustituto(idTitular));
			} catch (Exception e) {
				e.printStackTrace();
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}	
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object>  benefXPersona(Integer idBenef) {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryBenefxPersona(idBenef));
			} catch (Exception e) {
				e.printStackTrace();
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}	
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}
	


	@Override
	public Response<Object>  catPromotores() {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryCatPromotores());
			} catch (Exception e) {
				e.printStackTrace();
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object> beneficiarios(Integer idPreReg) {
		return null;
	}
	
	
	@Override
	public Response<Object>  actDesactConvenioPer(Integer idPreReg) {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryActDesactConvenioPer(idPreReg));
			} catch (Exception e) {
				e.printStackTrace();
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

}
