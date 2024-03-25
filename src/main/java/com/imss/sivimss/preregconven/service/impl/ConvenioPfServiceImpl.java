package com.imss.sivimss.preregconven.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imss.sivimss.preregconven.configuration.MyBatisConfig;
import com.imss.sivimss.preregconven.configuration.mapper.ActualizaConvenioPFPersonaMapper;
import com.imss.sivimss.preregconven.model.request.ActualizaConvenioPersonaPFDTO;
import com.imss.sivimss.preregconven.service.ConvenioPfService;
import com.imss.sivimss.preregconven.service.beans.Usuario;
import com.imss.sivimss.preregconven.utils.AppConstantes;
import com.imss.sivimss.preregconven.utils.DatosRequest;
import com.imss.sivimss.preregconven.utils.LogUtil;
import com.imss.sivimss.preregconven.utils.Response;

@Service
public class ConvenioPfServiceImpl implements ConvenioPfService {

	@Autowired
	private LogUtil logUtil;

	@Autowired
	private MyBatisConfig myBatisConfig;

	private static final Logger log = LoggerFactory.getLogger(ConvenioPfServiceImpl.class);

	private Gson gson = new Gson();

	private Usuario usuario;

	private ActualizaConvenioPersonaPFDTO convenioPersonaPFDTO;

	@Override
	public Response<Object> actualizarPFPersona(DatosRequest request, Authentication authentication)
			throws IOException {
		usuario = gson.fromJson((String) authentication.getPrincipal(), Usuario.class);
		String datosJson = request.getDatos().get(AppConstantes.DATOS).toString();
		this.convenioPersonaPFDTO = gson.fromJson(datosJson, ActualizaConvenioPersonaPFDTO.class);
		convenioPersonaPFDTO.setIdUsuario(Integer.parseInt(usuario.getIdUsuario()));
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ActualizaConvenioPFPersonaMapper convenioPFPersonaMapper = session
					.getMapper(ActualizaConvenioPFPersonaMapper.class);
			try {

				String consultaAnterior;
				String consultaNueva;
				Map<String, Object> consulta;

				// consulta dato anterior persona
				consulta = convenioPFPersonaMapper.buscarPersona(convenioPersonaPFDTO.getIdPersona());
				consultaAnterior = consulta == null ? null
						: consulta.toString();
				// actualiza persona
				convenioPFPersonaMapper.actualizarPersona(convenioPersonaPFDTO);
				// consulta actualizacion persona
				consulta = convenioPFPersonaMapper.buscarPersona(convenioPersonaPFDTO.getIdPersona());
				consultaNueva = consulta == null ? null
						: consulta.toString();
				// inserta bitacora
				convenioPFPersonaMapper.bitacora(2, "SVC_PERSONA", consultaAnterior, consultaNueva,
						Integer.parseInt(usuario.getIdUsuario()));
				// consulta domicilio
				consulta = convenioPFPersonaMapper.buscarDomicilio(convenioPersonaPFDTO.getIdDomicilio());
				consultaAnterior = consulta == null ? null
						: consulta.toString();
				// actualiza domicilio
				convenioPFPersonaMapper.actualizaDomicilio(convenioPersonaPFDTO);
				// consulta domiclio actualizacion
				consulta = convenioPFPersonaMapper.buscarDomicilio(convenioPersonaPFDTO.getIdDomicilio());
				consultaNueva = consulta == null ? null
						: consulta.toString();
				// inserta bitacora
				convenioPFPersonaMapper.bitacora(1, "SVT_DOMICILIO", consultaAnterior, consultaNueva,
						Integer.parseInt(usuario.getIdUsuario()));

				// consulta paquete
				consulta = convenioPFPersonaMapper.buscarContratoPaquete(convenioPersonaPFDTO.getIdContraPaqPF());
				consultaAnterior = consulta == null ? null
						: consulta.toString();
				// actualiza paquete convenio
				convenioPFPersonaMapper.actualizaPaqueteConvenio(convenioPersonaPFDTO);
				// consulta paquete
				consulta = convenioPFPersonaMapper.buscarContratoPaquete(convenioPersonaPFDTO.getIdContraPaqPF());
				consultaNueva = consulta == null ? null
						: consulta.toString();
				// inserta bitacora
				convenioPFPersonaMapper.bitacora(2, "SVT_CONTRA_PAQ_CONVENIO_PF", consultaAnterior, consultaNueva,
						Integer.parseInt(usuario.getIdUsuario()));

				// consulta plan
				consulta = convenioPFPersonaMapper.buscarConvenio(convenioPersonaPFDTO.getIdConvenioPF());
				consultaAnterior = consulta == null ? null
						: consulta.toString();
				// actualiza promotor
				convenioPFPersonaMapper.actualizaPromotor(convenioPersonaPFDTO);
				// consulta convenio
				consulta = convenioPFPersonaMapper.buscarConvenio(convenioPersonaPFDTO.getIdConvenioPF());
				consultaNueva = consulta == null ? null
						: consulta.toString();
				// inserta bitacora
				convenioPFPersonaMapper.bitacora(2, "SVT_CONVENIO_PF", consultaAnterior, consultaNueva,
						Integer.parseInt(usuario.getIdUsuario()));

				if (convenioPersonaPFDTO.isValidaIne()) {
					convenioPFPersonaMapper.actualizaIne(convenioPersonaPFDTO);
				}

				if (convenioPersonaPFDTO.isValidaRfc()) {
					convenioPFPersonaMapper.actualizaRFC(convenioPersonaPFDTO);
				}

				if (convenioPersonaPFDTO.isValidaCurp()) {
					convenioPFPersonaMapper.actualizaCurp(convenioPersonaPFDTO);
				}

				// consulta documentos
				consulta = convenioPFPersonaMapper.buscarDocumentos(convenioPersonaPFDTO.getIdValidaDocumento());
				consultaAnterior = consulta == null ? null
						: consulta.toString();
				// consulta documentos
				consulta = convenioPFPersonaMapper.buscarDocumentos(convenioPersonaPFDTO.getIdValidaDocumento());
				consultaNueva = consulta == null ? null
						: consulta.toString();
				// inserta bitacora
				convenioPFPersonaMapper.bitacora(2, "SVC_VALIDA_DOCS_CONVENIO_PF", consultaAnterior, consultaNueva,
						Integer.parseInt(usuario.getIdUsuario()));

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
		String datosJson = request.getDatos().get(AppConstantes.DATOS).toString();
		this.convenioPersonaPFDTO = gson.fromJson(datosJson, ActualizaConvenioPersonaPFDTO.class);
		convenioPersonaPFDTO.setIdUsuario(Integer.parseInt(usuario.getIdUsuario()));
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ActualizaConvenioPFPersonaMapper convenioPFPersonaMapper = session
					.getMapper(ActualizaConvenioPFPersonaMapper.class);

			try {
				String consultaAnterior;
				String consultaNueva;
				Map<String, Object> consulta;
				// consulta dato anterior persona
				consulta = convenioPFPersonaMapper.buscarPersona(convenioPersonaPFDTO.getIdPersona());
				consultaAnterior = consulta == null ? null
						: consulta.toString();
				// actualiza persona
				convenioPFPersonaMapper.actualizarPersona(convenioPersonaPFDTO);
				// consulta actualizacion persona
				consulta = convenioPFPersonaMapper.buscarPersona(convenioPersonaPFDTO.getIdPersona());
				consultaNueva = consulta == null ? null
						: consulta.toString();
				// inserta bitacora
				convenioPFPersonaMapper.bitacora(2, "SVC_PERSONA", consultaAnterior, consultaNueva,
						Integer.parseInt(usuario.getIdUsuario()));
				// consulta dato anterior persona
				consulta = convenioPFPersonaMapper
						.buscarContratanteBeneficiario(convenioPersonaPFDTO.getIdContratanteBeneficiario());
				consultaAnterior = consulta == null ? null
						: consulta.toString();
				// actualzia documentos
				convenioPFPersonaMapper.actualizaDocumentosBeneficiario(convenioPersonaPFDTO);

				// consulta actualizacion persona
				consulta = convenioPFPersonaMapper
						.buscarContratanteBeneficiario(convenioPersonaPFDTO.getIdContratanteBeneficiario());
				consultaNueva = consulta == null ? null
						: consulta.toString();
				// inserta bitacora
				convenioPFPersonaMapper.bitacora(2, "SVT_CONTRATANTE_BENEFICIARIOS", consultaAnterior, consultaNueva,
						Integer.parseInt(usuario.getIdUsuario()));
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
		String datosJson = request.getDatos().get(AppConstantes.DATOS).toString();
		this.convenioPersonaPFDTO = gson.fromJson(datosJson, ActualizaConvenioPersonaPFDTO.class);
		convenioPersonaPFDTO.setIdUsuario(Integer.parseInt(usuario.getIdUsuario()));
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ActualizaConvenioPFPersonaMapper convenioPFPersonaMapper = session
					.getMapper(ActualizaConvenioPFPersonaMapper.class);

			try {
				String consultaAnterior;
				String consultaNueva;
				Map<String, Object> consulta;
				// consulta empresa
				consulta = convenioPFPersonaMapper.buscarDatoEmpresa(convenioPersonaPFDTO.getIdEmpresa());
				consultaAnterior = consulta == null ? null
						: consulta.toString();

				convenioPFPersonaMapper.actualizaPlanEmpresa(convenioPersonaPFDTO);
				// consulta domicilio actualizacion
				consulta = convenioPFPersonaMapper.buscarDatoEmpresa(convenioPersonaPFDTO.getIdEmpresa());
				consultaNueva = consulta == null ? null
						: consulta.toString();
				// inserta bitacora
				convenioPFPersonaMapper.bitacora(1, "SVT_EMPRESA_CONVENIO_PF", consultaAnterior, consultaNueva,
						Integer.parseInt(usuario.getIdUsuario()));
				// consulta domicilio
				consulta = convenioPFPersonaMapper.buscarDomicilio(convenioPersonaPFDTO.getIdDomicilio());
				consultaAnterior = consulta == null ? null
						: consulta.toString();
				convenioPFPersonaMapper.actualizaDomicilio(convenioPersonaPFDTO);
				// consulta domicilio actualizacion
				consulta = convenioPFPersonaMapper.buscarDomicilio(convenioPersonaPFDTO.getIdDomicilio());
				consultaNueva = consulta == null ? null
						: consulta.toString();
				// inserta bitacora
				convenioPFPersonaMapper.bitacora(1, "SVT_DOMICILIO", consultaAnterior, consultaNueva,
						Integer.parseInt(usuario.getIdUsuario()));

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
		this.convenioPersonaPFDTO = new ActualizaConvenioPersonaPFDTO();
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
		String datosJson = request.getDatos().get(AppConstantes.DATOS).toString();
		this.convenioPersonaPFDTO = gson.fromJson(datosJson, ActualizaConvenioPersonaPFDTO.class);
		convenioPersonaPFDTO.setIdUsuario(Integer.parseInt(usuario.getIdUsuario()));
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ActualizaConvenioPFPersonaMapper convenioPFPersonaMapper = session
					.getMapper(ActualizaConvenioPFPersonaMapper.class);

			try {

				String consultaAnterior;
				String consultaNueva;
				Map<String, Object> consulta;
				// consulta plan
				consulta = convenioPFPersonaMapper.buscarConvenio(convenioPersonaPFDTO.getIdConvenioPF());
				consultaAnterior = consulta == null ? null
						: consulta.toString();
				convenioPFPersonaMapper.actualizarEstatusConvenioPF(convenioPersonaPFDTO);
				// consulta convenio
				consulta = convenioPFPersonaMapper.buscarConvenio(convenioPersonaPFDTO.getIdConvenioPF());
				consultaNueva = consulta == null ? null
						: consulta.toString();
				// inserta bitacora
				convenioPFPersonaMapper.bitacora(2, "SVT_CONVENIO_PF", consultaAnterior, consultaNueva,
						Integer.parseInt(usuario.getIdUsuario()));
				if (convenioPersonaPFDTO.getTipoContratacion() == 2) {
					convenioPFPersonaMapper.insertPagoBitacora(convenioPersonaPFDTO);
				} else {
					convenioPFPersonaMapper.insertPagoBitacoraPersona(convenioPersonaPFDTO);
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
