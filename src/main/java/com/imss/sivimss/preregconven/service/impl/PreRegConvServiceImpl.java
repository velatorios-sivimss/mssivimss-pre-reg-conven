package com.imss.sivimss.preregconven.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.imss.sivimss.preregconven.configuration.MyBatisConfig;
import com.imss.sivimss.preregconven.configuration.mapper.Consultas;
import com.imss.sivimss.preregconven.model.entity.BenefXPA;
import com.imss.sivimss.preregconven.model.entity.PreRegistrosXPA;
import com.imss.sivimss.preregconven.model.entity.PreRegistrosXPAConBeneficiarios;
import com.imss.sivimss.preregconven.model.request.RequestFiltroPaginado;
import com.imss.sivimss.preregconven.service.PreRegConvService;
import com.imss.sivimss.preregconven.service.beans.BeanQuerys;
import com.imss.sivimss.preregconven.utils.AppConstantes;
import com.imss.sivimss.preregconven.utils.LogUtil;
import com.imss.sivimss.preregconven.utils.PaginadoUtil;
import com.imss.sivimss.preregconven.utils.Response;



@Service
public class PreRegConvServiceImpl implements PreRegConvService {

	@Autowired
	private BeanQuerys query;

	@Autowired
	private MyBatisConfig myBatisConfig;
	
	@Autowired
	private PaginadoUtil paginadoUtil;

	@Autowired
	private LogUtil logUtil;
	
	private static final String ERROR = "ERROR"; 

	private static final Logger log = LoggerFactory.getLogger(PreRegConvServiceImpl.class);
	
	@Override
	public Response<Object> obtenerPreRegistros(RequestFiltroPaginado request, Authentication authentication)
			throws IOException {
		
		Page<Map<String, Object>> objetoPaginado = paginadoUtil.paginado(request.getPagina(), request.getTamanio(), query.queryPreRegistros(request));
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, objetoPaginado);
		
	}

	@Override
	public Response<Object>  obtenerPreRegistrosXEmpresa(Integer idPreReg, Authentication authentication)
			throws IOException {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryPreRegistrosXEmpresa(idPreReg));
			} catch (Exception e) {
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}


	@Override
	public Response<Object> obtenerPreRegistrosPersonasEmpresa(Integer idPreReg, Authentication authentication)
			throws IOException {
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryPreRegPersonasEmpresa(idPreReg));
			} catch (Exception e) {
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}
	

	@Override
	public Response<Object>  obtenerDocsEmpresa(Integer idPreReg, Authentication authentication)
			throws IOException {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryDocsEmpresa(idPreReg));
			} catch (Exception e) {
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}	
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}
	
	
	
	
	
	@Override
	public Response<Object>  obtenerPreRegistrosXPersona(Integer idPreReg /* idConvenio  */, Authentication authentication)
			throws IOException {
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
					//consultaBenefXPA1 =consultas.selectBenefxPersona(query.queryBenefxPersona(consultaPreRegistroXPersona.getBeneficiario1()));
					//consultaBenefXPA2 =consultas.selectBenefxPersona(query.queryBenefxPersona(consultaPreRegistroXPersona.getBeneficiario2()));
					
					/* Si llegan null se deben setear objetos instanciados vacios */
					
					beneficiarios.add(consultaBenefXPA1);
					beneficiarios.add(consultaBenefXPA2);
					preRegistro.setBeneficiarios(beneficiarios);
					
				}
				
			} catch (Exception e) {
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, preRegistro);
	}

	public Response<Object> guardaDocsConvenioXPersona(Integer idPreReg,MultipartFile[] archivo, Authentication authentication){
		/* Este Servicio se encarga de cargar los docs de un pre registro */
		
		PreRegistrosXPAConBeneficiarios preRegistro = new PreRegistrosXPAConBeneficiarios();

		

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, preRegistro);
	}

	@Override
	public Response<Object>  catPaquetes(Authentication authentication)
			throws IOException {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryCatPaquetes());
			} catch (Exception e) {
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object>  benefXEmpresa(Integer idPreReg, Authentication authentication)
			throws IOException {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryBenefXEmpresa(idPreReg));
			} catch (Exception e) {
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}	
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}
	

	
	
	
	@Override
	public Response<Object> titularSustituto(Integer idTitular, Authentication authentication)
			throws IOException {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryTitularSustituto(idTitular));
			} catch (Exception e) {
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}	
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object>  benefXPersona(Integer idBenef, Authentication authentication)
			throws IOException {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryBenefxPersona(idBenef));
			} catch (Exception e) {
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}	
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}
	


	@Override
	public Response<Object>  catPromotores(Authentication authentication)
			throws IOException {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryCatPromotores());
			} catch (Exception e) {
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object> beneficiarios(Integer idPreReg, Authentication authentication)
			throws IOException{
		return null;
	}
	
	
	@Override
	public Response<Object>  actDesactConvenioPer(Integer idPreReg, Authentication authentication)
			throws IOException{
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryActDesactConvenioPer(idPreReg));
			} catch (Exception e) {
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

}
