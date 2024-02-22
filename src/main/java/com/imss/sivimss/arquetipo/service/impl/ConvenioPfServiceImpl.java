package com.imss.sivimss.arquetipo.service.impl;

import java.io.IOException;
import java.util.logging.Level;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imss.sivimss.arquetipo.configuration.MyBatisConfig;
import com.imss.sivimss.arquetipo.configuration.mapper.ActualizaConvenioPFPersonaMapper;
import com.imss.sivimss.arquetipo.model.request.ActualizaConvenioPersonaPFDTO;
import com.imss.sivimss.arquetipo.service.ConvenioPfService;
import com.imss.sivimss.arquetipo.service.beans.Usuario;
import com.imss.sivimss.arquetipo.utils.AppConstantes;
import com.imss.sivimss.arquetipo.utils.DatosRequest;
import com.imss.sivimss.arquetipo.utils.LogUtil;
import com.imss.sivimss.arquetipo.utils.Response;



@Service
public class ConvenioPfServiceImpl implements ConvenioPfService{

	@Autowired
	private LogUtil logUtil;

	@Autowired
	private MyBatisConfig myBatisConfig;

	private static final Logger log = LoggerFactory.getLogger(ConvenioPfServiceImpl.class);

	@Autowired
	private ModelMapper mapper;

	private final String ERROR = "error: {}";

	private Gson gson = new Gson();
	
	private Usuario usuario;
	
	private ActualizaConvenioPersonaPFDTO convenioPersonaPFDTO;

	@Override
	public Response<Object> actualizarPFPersona(DatosRequest request, Authentication authentication)
			throws IOException {
		usuario = gson.fromJson((String) authentication.getPrincipal(), Usuario.class);
		String datosJson=request.getDatos().get(AppConstantes.DATOS).toString();
		this.convenioPersonaPFDTO=gson.fromJson(datosJson, ActualizaConvenioPersonaPFDTO.class);
		convenioPersonaPFDTO.setIdUsuario(Integer.parseInt(usuario.getIdUsuario()));
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ActualizaConvenioPFPersonaMapper convenioPFPersonaMapper= session.getMapper(ActualizaConvenioPFPersonaMapper.class);
			try {
			
				convenioPFPersonaMapper.actualizarPersona(convenioPersonaPFDTO);
				convenioPFPersonaMapper.actualizaDomicilio(convenioPersonaPFDTO);
				convenioPFPersonaMapper.actualizaPaqueteConvenio(convenioPersonaPFDTO);
				convenioPFPersonaMapper.actualizaPromotor(convenioPersonaPFDTO);
				
				if (convenioPersonaPFDTO.isValidaIne()) {
					convenioPFPersonaMapper.actualizaIne(convenioPersonaPFDTO);
				}
				
				if (convenioPersonaPFDTO.isValidaRfc()) {
					convenioPFPersonaMapper.actualizaRFC(convenioPersonaPFDTO);
				}
				
				if (convenioPersonaPFDTO.isValidaCurp()) {
					convenioPFPersonaMapper.actualizaCurp(convenioPersonaPFDTO);
				}
				

			} catch (Exception e) {
				session.rollback();
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, 200, AppConstantes.OCURRIO_ERROR_GUARDAR, e.getMessage());
			}

			session.commit();
		}

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, null);
	}

	@Override
	public Response<Object> actualizarBeneficiario(DatosRequest request, Authentication authentication)
			throws IOException {
		usuario = gson.fromJson((String) authentication.getPrincipal(), Usuario.class);
		String datosJson=request.getDatos().get(AppConstantes.DATOS).toString();
		this.convenioPersonaPFDTO=gson.fromJson(datosJson, ActualizaConvenioPersonaPFDTO.class);
		convenioPersonaPFDTO.setIdUsuario(Integer.parseInt(usuario.getIdUsuario()));
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ActualizaConvenioPFPersonaMapper convenioPFPersonaMapper= session.getMapper(ActualizaConvenioPFPersonaMapper.class);

			try {
			
				convenioPFPersonaMapper.actualizarPersona(convenioPersonaPFDTO);
				convenioPFPersonaMapper.actualizaDocumentosBeneficiario(convenioPersonaPFDTO);
				
				
			} catch (Exception e) {
				session.rollback();
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, 200, AppConstantes.OCURRIO_ERROR_GUARDAR, e.getMessage());
			}

			session.commit();
		}

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, null);
	}

	@Override
	public Response<Object> actualizarPlanPFEmpresa(DatosRequest request, Authentication authentication)
			throws IOException {
		usuario = gson.fromJson((String) authentication.getPrincipal(), Usuario.class);
		String datosJson=request.getDatos().get(AppConstantes.DATOS).toString();
		this.convenioPersonaPFDTO=gson.fromJson(datosJson, ActualizaConvenioPersonaPFDTO.class);
		convenioPersonaPFDTO.setIdUsuario(Integer.parseInt(usuario.getIdUsuario()));
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ActualizaConvenioPFPersonaMapper convenioPFPersonaMapper= session.getMapper(ActualizaConvenioPFPersonaMapper.class);

			try {
			
				convenioPFPersonaMapper.actualizaPlanEmpresa(convenioPersonaPFDTO);
				convenioPFPersonaMapper.actualizaDomicilio(convenioPersonaPFDTO);

			} catch (Exception e) {
				session.rollback();
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, 200, AppConstantes.OCURRIO_ERROR_GUARDAR, e.getMessage());
			}

			session.commit();
		}

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, null);
	}

	@Override
	public Response<Object> actualizarersonaPFEmpresa(DatosRequest request, Authentication authentication)
			throws IOException {
		usuario = gson.fromJson((String) authentication.getPrincipal(), Usuario.class);
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		this.convenioPersonaPFDTO= new ActualizaConvenioPersonaPFDTO();
		convenioPersonaPFDTO.setIdUsuario(Integer.parseInt(usuario.getIdUsuario()));
		try (SqlSession session = sqlSessionFactory.openSession()) {
		
			try {
			

			} catch (Exception e) {
				session.rollback();
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, 200, AppConstantes.OCURRIO_ERROR_GUARDAR, e.getMessage());
			}

			session.commit();
		}

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, null);
	}

	@Override
	public Response<Object> actualizarEstatusConvenioPf(DatosRequest request, Authentication authentication)
			throws IOException {
		usuario = gson.fromJson((String) authentication.getPrincipal(), Usuario.class);
		String datosJson=request.getDatos().get(AppConstantes.DATOS).toString();
		this.convenioPersonaPFDTO=gson.fromJson(datosJson, ActualizaConvenioPersonaPFDTO.class);
		convenioPersonaPFDTO.setIdUsuario(Integer.parseInt(usuario.getIdUsuario()));
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ActualizaConvenioPFPersonaMapper convenioPFPersonaMapper= session.getMapper(ActualizaConvenioPFPersonaMapper.class);

			try {
			    convenioPFPersonaMapper.actualizarEstatusConvenioPF(convenioPersonaPFDTO);
				if (convenioPersonaPFDTO.getTipoContratacion()==2) {
					convenioPFPersonaMapper.insertPagoBitacora(convenioPersonaPFDTO);
				}
				
				

			} catch (Exception e) {
				session.rollback();
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				return new Response<>(true, 200, AppConstantes.OCURRIO_ERROR_GUARDAR, e.getMessage());
			}

			session.commit();
		}

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, null);
	}

	

}
