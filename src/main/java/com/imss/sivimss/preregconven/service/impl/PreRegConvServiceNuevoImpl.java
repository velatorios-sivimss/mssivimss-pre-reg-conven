package com.imss.sivimss.preregconven.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.imss.sivimss.preregconven.configuration.MyBatisConfig;
import com.imss.sivimss.preregconven.configuration.mapper.ConvenioPA;
import com.imss.sivimss.preregconven.configuration.mapper.ConvenioPF;
import com.imss.sivimss.preregconven.configuration.mapper.Empresas;
import com.imss.sivimss.preregconven.configuration.mapper.Personas;
import com.imss.sivimss.preregconven.model.entity.BenefXPA;
import com.imss.sivimss.preregconven.model.entity.DatosConvenio;
import com.imss.sivimss.preregconven.model.entity.DatosEmpresa;
import com.imss.sivimss.preregconven.model.entity.DatosEmpresaBeneficiarios;
import com.imss.sivimss.preregconven.model.entity.DatosEmpresaSolicitante;
import com.imss.sivimss.preregconven.model.entity.DatosPersonaBeneficiarios;
import com.imss.sivimss.preregconven.model.entity.DatosPersonaConvenio;
import com.imss.sivimss.preregconven.model.entity.DetalleConvenioPFXEmpresa;
import com.imss.sivimss.preregconven.model.entity.DetalleConvenioPFXEmpresaBeneficiarios;
import com.imss.sivimss.preregconven.model.entity.DetalleConvenioPFXEmpresaBeneficiariosDocs;
import com.imss.sivimss.preregconven.model.entity.DetalleConvenioPFXEmpresaSolicitantes;
import com.imss.sivimss.preregconven.model.entity.DetalleConvenioPFXPersona;
import com.imss.sivimss.preregconven.model.entity.DetalleConvenioPFXPersonaBeneficiarios;
import com.imss.sivimss.preregconven.model.entity.DetalleConvenioPFXPersonaBeneficiariosDocs;
import com.imss.sivimss.preregconven.model.entity.PreRegistrosXPA;
import com.imss.sivimss.preregconven.model.entity.PreRegistrosXPAConBeneficiarios;
import com.imss.sivimss.preregconven.model.entity.PreRegistrosXPFEmpresaConSolicitantes;
import com.imss.sivimss.preregconven.model.entity.PreRegistrosXPFPersonaConBeneficiarios;
import com.imss.sivimss.preregconven.model.entity.RegistroPagoPlanPF;
import com.imss.sivimss.preregconven.model.request.ActualizarConvenioPersona;
import com.imss.sivimss.preregconven.model.request.ActualizarDatosEmpresa;
import com.imss.sivimss.preregconven.model.request.ActualizarDatosPA;
import com.imss.sivimss.preregconven.model.request.Flujos;
import com.imss.sivimss.preregconven.model.request.PlanPABeneficiario;
import com.imss.sivimss.preregconven.model.request.PlanPAData;
import com.imss.sivimss.preregconven.model.request.PlanPASustituto;
import com.imss.sivimss.preregconven.model.request.RequestFiltroPaginado;
import com.imss.sivimss.preregconven.model.request.UsuarioDto;
import com.imss.sivimss.preregconven.model.request.ValidarRfcCurpContratante;
import com.imss.sivimss.preregconven.model.response.ResponseContratanteRfcCurp;
import com.imss.sivimss.preregconven.service.PreRegConvServiceNuevo;
import com.imss.sivimss.preregconven.service.beans.BeanQuerys;
import com.imss.sivimss.preregconven.utils.AppConstantes;
import com.imss.sivimss.preregconven.utils.DatosRequest;
import com.imss.sivimss.preregconven.utils.LogUtil;
import com.imss.sivimss.preregconven.utils.PaginadoUtil;
import com.imss.sivimss.preregconven.utils.Response;

@Service
public class PreRegConvServiceNuevoImpl implements PreRegConvServiceNuevo {
	private static final Logger log = LoggerFactory.getLogger(PreRegConvServiceNuevoImpl.class);

	@Autowired
	private BeanQuerys query;

	@Autowired
	private MyBatisConfig myBatisConfig;

	@Autowired
	private PaginadoUtil paginadoUtil;

	private static final String ERROR = "ERROR";
	Gson json = new Gson();

	private static final Integer PLATAFORMA_LINEA = 2;
	private static final String COMMIT = "==> commit() ";
	private static final String ROLLBACK = "==> rollback() ";
	private static final String ID_FLUJO = "idFlujo ";
	private static final String ID_CONVENIO_PF = "idConvenioPf ";
	private static final String FLUJO = "Flujo ";
	private static final String RESULT = "result ";
	
	@Autowired
	private LogUtil logUtil;

	@Override
	public Response<Object> actualizarDatosPersona(DatosRequest request, Authentication authentication) {
		UsuarioDto usuarioDto = json.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
		Integer idUsuario = usuarioDto.getIdUsuario();
		Integer idVelatorio = usuarioDto.getIdVelatorio();
		Gson gson = new Gson();
		String datos = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		log.info(datos);

		ActualizarConvenioPersona actualizarConvenioPersona = gson.fromJson(datos, ActualizarConvenioPersona.class);
		ArrayList<DatosPersonaBeneficiarios> beneficiarios = actualizarConvenioPersona.getBeneficiarios();
		DatosPersonaConvenio personaConvenio = actualizarConvenioPersona.getConvenio();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			Personas personasMap = session.getMapper(Personas.class);
			ConvenioPF convenios = session.getMapper(ConvenioPF.class);

			try {
				log.info(" == >> Persona " + personaConvenio.getIdPersona());
				personasMap.actualizarDatosContratante(personaConvenio);
				personasMap.actualizarDomicilioContratante(personaConvenio);
				personasMap.actualizarPaqueteContratante(personaConvenio);

				for (DatosPersonaBeneficiarios beneficiario : beneficiarios) {
					log.info(" == >> Persona " + beneficiario.getIdPersona());
					personasMap.actualizarBeneficiariosParentesco(beneficiario);
					personasMap.actualizarBeneficiarios(beneficiario);
				}

				Double importe = convenios.consultaImportePaquetesConvenio(personaConvenio.getIdConvenioPF());
				RegistroPagoPlanPF registro = new RegistroPagoPlanPF();

				registro.setIdConvenioPf(personaConvenio.getIdConvenioPF());
				registro.setIdFlujo(1);
				registro.setIdVelatorio(idVelatorio); // revalidar
				registro.setNomContratante(personaConvenio.getNombreCompleto()); // revalidar
				registro.setCveFolio("x.x"); // revalidar
				registro.setImporte(importe);
				registro.setCvdEstatusPago(1); // revalidar
				registro.setIdUsuarioAlta(idUsuario);

				session.commit();
				// convenios.insertaPago(registro);
				log.info(COMMIT);
				// pasar a generado id 1

				PreRegistrosXPFPersonaConBeneficiarios detalleConvenioPFPersona = consultaConveniosPFPersona(
						personaConvenio.getIdConvenioPF());
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, detalleConvenioPFPersona);
			} catch (Exception e) {
				log.info(e.getMessage());

				log.info(ROLLBACK);
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}

		// throw new UnsupportedOperationException("Unimplemented method
		// 'actualizarDatosPersona'");
	}

	@Override
	public Response<Object> actualizarDatosEmpresa(DatosRequest request, Authentication authentication) {
		UsuarioDto usuarioDto = json.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
		Integer idUsuario = usuarioDto.getIdUsuario();
		Integer idVelatorio = usuarioDto.getIdVelatorio();
		Gson gson = new Gson();
		String datos = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		log.info(datos);

		ActualizarDatosEmpresa actualizarDatosEmpresa = gson.fromJson(datos, ActualizarDatosEmpresa.class);
		DatosEmpresa datosEmpresa = actualizarDatosEmpresa.getEmpresa();
		ArrayList<DatosEmpresaSolicitante> solicitantes = actualizarDatosEmpresa.getSolicitantes();
		ArrayList<DatosEmpresaBeneficiarios> beneficiarios = actualizarDatosEmpresa.getBeneficiarios();

		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			Empresas empresasMap = session.getMapper(Empresas.class);
			ConvenioPF convenios = session.getMapper(ConvenioPF.class);

			try {

				datosEmpresa.setIdUsuario(idUsuario);
				empresasMap.actualizarDatosEmpresa(datosEmpresa);
				empresasMap.actualizarDomicilioEmpresa(datosEmpresa);
				// se agrega el actualizar documentos

				for (DatosEmpresaSolicitante solicitante : solicitantes) {
					solicitante.setIdUsuario(idUsuario);
					log.info("persona:{}" + solicitante.getIdPersona());
					empresasMap.actualizarSolicitante(solicitante);
					empresasMap.actualizarDomicilioSolicitante(solicitante);
					if (solicitante.isActualizaCurp()) {
						// actualiza el archivo del curp
						log.info("actualizando archivo curp {}", solicitante.getIdPersona());
						empresasMap.actualizarArchivoCurp(solicitante);
					}
					if (solicitante.isActualizaIne()) {
						// actualiza archivo ine
						log.info("actualizando archivo ine {}", solicitante.getIdPersona());
						empresasMap.actualizarArchivoIne(solicitante);
					}

					if (solicitante.isActualizaRFC()) {
						// actualiza archivo rfc
						log.info("actualizando archivo rfc {}", solicitante.getIdPersona());
						empresasMap.actualizarArchivoRfc(solicitante);
					}
				}

				for (DatosEmpresaBeneficiarios beneficiario : beneficiarios) {
					beneficiario.setIdUsuario(idUsuario);
					log.info("persona: {} " + beneficiario.getIdPersona());
					empresasMap.actualizarBeneficiarios(beneficiario);
					empresasMap.actualizarBeneficiarios2(beneficiario);

					if (beneficiario.isValidaIne() || beneficiario.isValidaActa()) {
						// actualiza archivos beneficiarios
						log.info("actualizando Archivos Beneficiario {}", beneficiario.getIdPersona());
						empresasMap.actualizarArchivoBeneficiario(beneficiario);
					}

				}

				// cambio de estatus al plan pre fune
				log.info("Se cambia el estatus de plan pre fune a Generado ");
				empresasMap.actualizarEstatusConvenioPF(datosEmpresa);

				Double importe = convenios.consultaImportePaquetesConvenio(datosEmpresa.getIdConvenioPf());
				RegistroPagoPlanPF registro = new RegistroPagoPlanPF();

				registro.setIdConvenioPf(datosEmpresa.getIdConvenioPf());
				registro.setIdFlujo(2);
				registro.setIdVelatorio(idVelatorio);
				registro.setNomContratante(datosEmpresa.getNombreEmpresa());
				registro.setCveFolio(datosEmpresa.getFolioConvenioPf());
				registro.setImporte(importe);
				registro.setCvdEstatusPago(2);
				registro.setIdUsuarioAlta(idUsuario);
				registro.setIdPlataforma(PLATAFORMA_LINEA);
				convenios.insertaPago(registro);
				session.commit();

				log.info(COMMIT);

			} catch (Exception e) {
				log.info(e.getMessage());

				log.info(ROLLBACK);
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}

			PreRegistrosXPFEmpresaConSolicitantes detalleConvenioPFEmpresa = consultaConveniosPFEmpresa(
					datosEmpresa.getIdConvenioPf(), 1);
			return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, detalleConvenioPFEmpresa);

		}

	}

	@Override
	public Response<Object> validarRfcCurpContratante(DatosRequest request) {
		Gson gson = new Gson();
		String datos = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		log.info(datos);
		ValidarRfcCurpContratante request1 = new ValidarRfcCurpContratante();

		request1 = gson.fromJson(datos, ValidarRfcCurpContratante.class);
		String rfc = request1.getRfc();
		String curp = request1.getCurp();
		Integer idConvenio = request1.getIdConvenio();
		Integer idFlujo = request1.getIdFlujo();

		log.info("rfc " + rfc);
		log.info("curp " + curp);
		log.info("convenio " + idConvenio);

		ResponseContratanteRfcCurp resp = new ResponseContratanteRfcCurp();

		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPA conveniosPA = session.getMapper(ConvenioPA.class);
			ConvenioPF conveniosPF = session.getMapper(ConvenioPF.class);

			try {
				Integer contratanteRfc = 0;
				Integer contratanteCurp = 0;
				switch (idFlujo) {
					case 1:
						contratanteRfc = conveniosPA.consultaRfcRepetido(idConvenio, rfc);
						contratanteCurp = conveniosPA.consultaCurpRepetido(idConvenio, curp);

						resp.setCurp(curp);
						resp.setRfc(rfc);
						resp.setIdConvenio(idConvenio);

						if (contratanteRfc > 0) {
							resp.isRfcDuplicado();
						}
						if (contratanteCurp > 0) {
							resp.isCurpDuplicada();
						}
						break;

					case 2:
						contratanteRfc = conveniosPF.consultaRfcRepetido(idConvenio, rfc);
						contratanteCurp = conveniosPF.consultaCurpRepetido(idConvenio, curp);

						resp.setCurp(curp);
						resp.setRfc(rfc);
						resp.setIdConvenio(idConvenio);

						if (contratanteRfc > 0) {
							resp.isRfcDuplicado();
						}
						if (contratanteCurp > 0) {
							resp.isCurpDuplicada();
						}

						break;

					case 3:
						contratanteRfc = conveniosPF.consultaRfcRepetido(idConvenio, rfc);
						contratanteCurp = conveniosPF.consultaCurpRepetido(idConvenio, curp);

						resp.setCurp(curp);
						resp.setRfc(rfc);
						resp.setIdConvenio(idConvenio);

						if (contratanteRfc > 0) {
							resp.isRfcDuplicado();
						}
						if (contratanteCurp > 0) {
							resp.isCurpDuplicada();
						}

						break;

					default:
						break;
				}

			} catch (Exception e) {
				session.rollback();
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}

			return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, resp);
		}
	}

	@Override
	public Response<Object> actDesactConvenio(DatosRequest request, Authentication authentication)
			throws IOException {
		/*
		 * localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/activar/desactivar/1/28
		 * localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/activar/desactivar/2/9
		 * localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/activar/desactivar/3/1
		 * 
		 */

		Gson gson = new Gson();
		String datos = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		log.info(datos);
		Flujos request1 = new Flujos();

		request1 = gson.fromJson(datos, Flujos.class);
		Integer idFlujo = request1.getIdFLujo();
		Integer idConvenioPf = request1.getIdConvenio();
		log.info(ID_FLUJO + idFlujo);
		log.info(ID_CONVENIO_PF + idConvenioPf);

		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPF convenios = session.getMapper(ConvenioPF.class);
			try {
				switch (idFlujo) {
					case 1:
						log.info(FLUJO + idFlujo);
						convenios.activarDesactivarConvenioPA(idConvenioPf);
						session.commit();

						Response<Object> aa = preRegXConvenios(request);
						PreRegistrosXPAConBeneficiarios convenioPA = (PreRegistrosXPAConBeneficiarios) aa.getDatos();
						log.info(RESULT + convenioPA.getPreRegistro().getActivo());

						return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,
								convenioPA.getPreRegistro().getActivo());

					case 2:
						log.info(FLUJO + idFlujo);
						convenios.activarDesactivarConvenioPF(idConvenioPf);
						session.commit();

						Response<Object> cc = preRegXConvenios(request);
						PreRegistrosXPFEmpresaConSolicitantes convenioPFEmpresa = (PreRegistrosXPFEmpresaConSolicitantes) cc
								.getDatos();
						log.info(RESULT + convenioPFEmpresa.getEmpresa().getActivo());

						return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,
								convenioPFEmpresa.getEmpresa().getActivo());

					case 3:
						log.info(FLUJO + idFlujo);

						convenios.activarDesactivarConvenioPF(idConvenioPf);
						session.commit();

						Response<Object> dd = preRegXConvenios(request);
						PreRegistrosXPFPersonaConBeneficiarios detalleConvenioPFPersona = (PreRegistrosXPFPersonaConBeneficiarios) dd
								.getDatos();
						log.info(RESULT + detalleConvenioPFPersona.getDetalleConvenioPFModel().getActivo());

						return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,
								detalleConvenioPFPersona.getDetalleConvenioPFModel().getActivo());
						
					default: return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);

				}

			} catch (Exception e) {
				log.info("{}", e.getMessage());
				logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(),
						AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
						authentication);
				session.rollback();
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}

	}

	@Override
	public Response<Object> obtenerPreRegistros(DatosRequest paginado) {

		Gson gson = new Gson();
		RequestFiltroPaginado request = gson.fromJson(String.valueOf(paginado.getDatos().get(AppConstantes.DATOS)),
				RequestFiltroPaginado.class);
		Integer pagina = Integer.parseInt(paginado.getDatos().get("pagina").toString());
		Integer tamanio = Integer.parseInt(paginado.getDatos().get("tamanio").toString());

		Page<Map<String, DatosConvenio>> objetoPaginado = paginadoUtil.paginadoConvenio(pagina, tamanio,
				query.queryPreRegistros(request));
		List<Map<String, DatosConvenio>> aa = objetoPaginado.getContent();
		String respuesta = gson.toJson(aa);
		System.out.println(respuesta);

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, objetoPaginado);
	}

	@Override
	public Response<Object> preRegXConvenios(DatosRequest request) {
		Gson gson = new Gson();
		String datos = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		log.info(datos);
		Flujos request1 = new Flujos();

		request1 = gson.fromJson(datos, Flujos.class);
		Integer idFlujo = request1.getIdFLujo();
		Integer idConvenioPf = request1.getIdConvenio();
		log.info(ID_FLUJO + idFlujo);
		log.info(ID_CONVENIO_PF + idConvenioPf);

		switch (idFlujo) {
			case 1:
				PreRegistrosXPAConBeneficiarios preRegistro = consultaConveniosPA(idConvenioPf);
				// localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/buscar/1/30
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, preRegistro);
			case 2:
				Integer seccion = request1.getSeccion();
				PreRegistrosXPFEmpresaConSolicitantes detalleConvenioPFEmpresa = consultaConveniosPFEmpresa(
						idConvenioPf, seccion);
				// localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/buscar/2/2
				// localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/buscar/2/9
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, detalleConvenioPFEmpresa);
			case 3:
				PreRegistrosXPFPersonaConBeneficiarios detalleConvenioPFPersona = consultaConveniosPFPersona(
						idConvenioPf);
				// localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/buscar/3/14
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, detalleConvenioPFPersona);
			default: return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);

		}

	}

	public PreRegistrosXPAConBeneficiarios consultaConveniosPA(Integer idConvenioPf) {
		PreRegistrosXPA consultaPreRegistrosXPA = new PreRegistrosXPA();
		PreRegistrosXPAConBeneficiarios preRegistro = new PreRegistrosXPAConBeneficiarios();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPA conveniosPA = session.getMapper(ConvenioPA.class);
			try {
				consultaPreRegistrosXPA = conveniosPA.consultaDetalleConvenioPA(idConvenioPf);
				preRegistro.setPreRegistro(consultaPreRegistrosXPA);

				if (consultaPreRegistrosXPA != null) {
					ArrayList<BenefXPA> beneficiarios = new ArrayList<>();

					if (consultaPreRegistrosXPA.getBeneficiario1() != null
							&& consultaPreRegistrosXPA.getBeneficiario1() > 0) {
						BenefXPA beneficiarioPA1 = conveniosPA
								.consultaBeneficiariosConvenioPA(consultaPreRegistrosXPA.getBeneficiario1());
						beneficiarios.add(beneficiarioPA1);
					}

					if (consultaPreRegistrosXPA.getBeneficiario2() != null
							&& consultaPreRegistrosXPA.getBeneficiario2() > 0) {
						BenefXPA beneficiarioPA2 = conveniosPA
								.consultaBeneficiariosConvenioPA(consultaPreRegistrosXPA.getBeneficiario2());
						beneficiarios.add(beneficiarioPA2);
					}

					if (consultaPreRegistrosXPA.getIdTitularSust() != null
							&& consultaPreRegistrosXPA.getIdTitularSust() > 0) {
						BenefXPA titularSustituto = conveniosPA
								.consultaTitularSust(consultaPreRegistrosXPA.getIdTitularSust());
						preRegistro.setSustituto(titularSustituto);
					}

					preRegistro.setBeneficiarios(beneficiarios);
				}

			} catch (Exception e) {
				log.error(ERROR, e);
				return null;
			}
		}

		return preRegistro;
	}

	public PreRegistrosXPFEmpresaConSolicitantes consultaConveniosPFEmpresa(Integer idConvenioPf, Integer seccion) {
		log.info(ID_CONVENIO_PF + idConvenioPf);

		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		DetalleConvenioPFXEmpresa detalleConvenioPFModel = new DetalleConvenioPFXEmpresa();
		ArrayList<DetalleConvenioPFXEmpresaSolicitantes> solicitantes = new ArrayList<>();
		PreRegistrosXPFEmpresaConSolicitantes convenioPFEmpresa = new PreRegistrosXPFEmpresaConSolicitantes();
		ArrayList<DetalleConvenioPFXEmpresaBeneficiarios> beneficiarios = new ArrayList<>();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPF convenios = session.getMapper(ConvenioPF.class);
			try {
				switch (seccion) {
					case 1:
						detalleConvenioPFModel = convenios.consultaDetalleConvenioXEmpresa(idConvenioPf);
						convenioPFEmpresa.setEmpresa(detalleConvenioPFModel != null ? detalleConvenioPFModel
								: new DetalleConvenioPFXEmpresa());
						solicitantes = convenios.consultaDetalleConvenioXEmpresaSolicitantes(idConvenioPf);
						break;

					case 2:
						beneficiarios = convenios.consultaDetalleConvenioXEmpresaBeneficiarios(idConvenioPf);

						break;

					default:
						break;
				}

				convenioPFEmpresa.setSolicitantes(
						solicitantes != null ? solicitantes : new ArrayList<DetalleConvenioPFXEmpresaSolicitantes>());
				convenioPFEmpresa.setBeneficiarios(beneficiarios != null ? beneficiarios
						: new ArrayList<DetalleConvenioPFXEmpresaBeneficiarios>());
			} catch (Exception e) {
				log.error(ERROR, e);
				return null;
			}
		}
		return convenioPFEmpresa;
	}

	public PreRegistrosXPFPersonaConBeneficiarios consultaConveniosPFPersona(Integer idConvenioPf) {
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		DetalleConvenioPFXPersona detalleConvenioPFModel = new DetalleConvenioPFXPersona();
		ArrayList<DetalleConvenioPFXPersonaBeneficiarios> beneficiarios = new ArrayList<>();
		PreRegistrosXPFPersonaConBeneficiarios preRegistros = new PreRegistrosXPFPersonaConBeneficiarios();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPF convenios = session.getMapper(ConvenioPF.class);
			try {
				detalleConvenioPFModel = convenios.consultaDetalleConvenioXPersona(idConvenioPf);
				beneficiarios = convenios.consultaDetalleConvenioXPersonaBenficiarios(idConvenioPf);

				preRegistros.setBeneficiarios(beneficiarios);
				preRegistros.setDetalleConvenioPFModel(detalleConvenioPFModel);
			} catch (Exception e) {
				log.error(ERROR, e);
				return null;
			}
		}
		return preRegistros;
	}

	@Override
	public Response<Object> preRegXConveniosDocs(DatosRequest request) {
		Gson gson = new Gson();
		String datos = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		log.info(datos);
		Flujos request1 = new Flujos();

		request1 = gson.fromJson(datos, Flujos.class);
		Integer idFlujo = request1.getIdFLujo();
		Integer idConvenioPf = request1.getIdConvenio();
		log.info(ID_FLUJO + idFlujo);
		log.info(ID_CONVENIO_PF + idConvenioPf);

		switch (idFlujo) {
			case 1:
				PreRegistrosXPAConBeneficiarios preRegistro = consultaConveniosPA(idConvenioPf);
				// localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/buscar/1/30
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, preRegistro);
			case 2:
				ArrayList<DetalleConvenioPFXEmpresaBeneficiariosDocs> detalleConvenioPFEmpresa = consultaConveniosPFEmpresaDocs(
						idConvenioPf);
				// localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/buscar/docs/2/9
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, detalleConvenioPFEmpresa);
			case 3:
				ArrayList<DetalleConvenioPFXPersonaBeneficiariosDocs> detalleConvenioPFPersona = consultaConveniosPFPersonaDocs(
						idConvenioPf);
				// localhost:8001/mssivimss-pre-reg-conven/v1/sivimss/buscar/3/14
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, detalleConvenioPFPersona);
			default: return new Response<>(true, HttpStatus.OK.value(), ERROR, 0); 

		}

	}

	public ArrayList<DetalleConvenioPFXEmpresaBeneficiariosDocs> consultaConveniosPFEmpresaDocs(Integer idConvenioPf) {
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		ArrayList<DetalleConvenioPFXEmpresaBeneficiariosDocs> beneficiarios = new ArrayList<>();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPF convenios = session.getMapper(ConvenioPF.class);
			try {

				beneficiarios = convenios.consultaDetalleConvenioXEmpresaBeneficiariosDocs(idConvenioPf);

			} catch (Exception e) {
				log.error(ERROR, e);
				return null;
			}
		}
		return beneficiarios;
	}

	public ArrayList<DetalleConvenioPFXPersonaBeneficiariosDocs> consultaConveniosPFPersonaDocs(Integer idConvenioPf) {
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		ArrayList<DetalleConvenioPFXPersonaBeneficiariosDocs> beneficiariosDocs;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPF convenios = session.getMapper(ConvenioPF.class);
			try {

				beneficiariosDocs = convenios.consultaDetalleConvenioXPersonaBeneficiariosDocs(idConvenioPf);

			} catch (Exception e) {
				log.error(ERROR, e);
				return null;
			}
		}
		return beneficiariosDocs;
	}

	@Override
	public Response<Object> actualizarDatosPA(DatosRequest request, Authentication authentication) {
		Gson gson = new Gson();
		String datos = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		log.info(datos);

		UsuarioDto usuarioDto = json.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
		Integer idUsuario = usuarioDto.getIdUsuario();

		ActualizarDatosPA datosPlanPA = gson.fromJson(datos, ActualizarDatosPA.class);
		PlanPAData plan = datosPlanPA.getPlan();
		PlanPASustituto titularSustituto = datosPlanPA.getTitularSustituto();
		PlanPABeneficiario beneficiario1 = datosPlanPA.getBeneficiario1();
		PlanPABeneficiario beneficiario2 = datosPlanPA.getBeneficiario2();
		
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPA conveniosPA = session.getMapper(ConvenioPA.class);

			try {
				String consultaAnterior;
				String consultaNueva;
				Map<String, Object> consulta;
				
				// plan
				// consulta dato anterior plan
				consulta = conveniosPA.buscarContratoPa(plan.getIdConvenio());
				consultaAnterior = consulta == null ? null
						: consulta.toString();
				conveniosPA.actualizarDatosPlan(plan);
				// consulta actualizacion plan
				consulta = conveniosPA.buscarContratoPa(plan.getIdConvenio());
				consultaNueva = consulta == null ? null
						: consulta.toString();
				// inserta bitacora
				conveniosPA.bitacora(2, "SVT_PLAN_SFPA", consultaAnterior, consultaNueva,
						usuarioDto.getIdUsuario());

				// persona
				// consulta dato anterior persona
				consulta = conveniosPA.buscarPersona(plan.getIdPersonaContratante());
				consultaAnterior = consulta == null ? null
						: consulta.toString();

				conveniosPA.actualizarDatosContratante(plan);
				// consulta actualizacion persona
				consulta = conveniosPA.buscarPersona(plan.getIdPersonaContratante());
				consultaNueva = consulta == null ? null
						: consulta.toString();
				// inserta bitacora
				conveniosPA.bitacora(2, "SVC_PERSONA", consultaAnterior, consultaNueva,
						usuarioDto.getIdUsuario());

				// domcilio	
				// consulta dato anterior domcilio
				consulta = conveniosPA.buscarDomicilio(plan.getIdDomicilio());
				consultaAnterior = consulta == null ? null
						: consulta.toString();
				conveniosPA.actualizarDomicilioContratante(plan);
				// consulta actualizacion domcilio
				consulta = conveniosPA.buscarDomicilio(plan.getIdDomicilio());
				consultaNueva = consulta == null ? null
						: consulta.toString();
				// inserta bitacora
				conveniosPA.bitacora(2, "SVT_DOMICILIO", consultaAnterior, consultaNueva,
						usuarioDto.getIdUsuario());


				if (titularSustituto != null) {
					// persona
					// consulta dato anterior persona
					consulta = conveniosPA.buscarPersona(titularSustituto.getIdPersonaTitular());
					consultaAnterior = consulta == null ? null
							: consulta.toString();

					conveniosPA.actualizarDatosSustituto(titularSustituto);
					// consulta actualizacion persona
					consulta = conveniosPA.buscarPersona(titularSustituto.getIdPersonaTitular());
					consultaNueva = consulta == null ? null
							: consulta.toString();
					// inserta bitacora
					conveniosPA.bitacora(2, "SVC_PERSONA", consultaAnterior, consultaNueva,
							usuarioDto.getIdUsuario());
					// domicilio
					// consulta dato anterior domcilio
					consulta = conveniosPA.buscarDomicilio(titularSustituto.getIdDomicilio());
					consultaAnterior = consulta == null ? null
							: consulta.toString();
					conveniosPA.actualizarDomicilioSustituto(titularSustituto);
					// consulta actualizacion domcilio
					consulta = conveniosPA.buscarDomicilio(titularSustituto.getIdDomicilio());
					consultaNueva = consulta == null ? null
							: consulta.toString();
					// inserta bitacora
					conveniosPA.bitacora(2, "SVT_DOMICILIO", consultaAnterior, consultaNueva,
							usuarioDto.getIdUsuario());
				}

				if (beneficiario1 != null) {
					// persona
					// consulta dato anterior persona
					consulta = conveniosPA.buscarPersona(beneficiario1.getIdPersonaTitular());
					consultaAnterior = consulta == null ? null
							: consulta.toString();
					conveniosPA.actualizarDatosBeneficiario(beneficiario1);
					// consulta actualizacion persona
					consulta = conveniosPA.buscarPersona(beneficiario1.getIdPersonaTitular());
					consultaNueva = consulta == null ? null
							: consulta.toString();
							
					// inserta bitacora
					conveniosPA.bitacora(2, "SVC_PERSONA", consultaAnterior, consultaNueva,
							usuarioDto.getIdUsuario());		
					// domicilio
					// consulta dato anterior domcilio
					consulta = conveniosPA.buscarDomicilio(beneficiario1.getIdDomicilio());
					consultaAnterior = consulta == null ? null
							: consulta.toString();
					conveniosPA.actualizarDomicilioBeneficiario(beneficiario1);
					// consulta actualizacion domcilio
					consulta = conveniosPA.buscarDomicilio(beneficiario1.getIdDomicilio());
					consultaNueva = consulta == null ? null
							: consulta.toString();
					// inserta bitacora
					conveniosPA.bitacora(2, "SVT_DOMICILIO", consultaAnterior, consultaNueva,
							usuarioDto.getIdUsuario());
				}

				if (beneficiario2 != null) {
					// persona
					// consulta dato anterior persona
					consulta = conveniosPA.buscarPersona(beneficiario2.getIdPersonaTitular());
					consultaAnterior = consulta == null ? null
							: consulta.toString();
					conveniosPA.actualizarDatosBeneficiario(beneficiario2);
					// consulta actualizacion persona
					consulta = conveniosPA.buscarPersona(beneficiario2.getIdPersonaTitular());
					consultaNueva = consulta == null ? null
							: consulta.toString();
							
					// inserta bitacora
					conveniosPA.bitacora(2, "SVC_PERSONA", consultaAnterior, consultaNueva,
							usuarioDto.getIdUsuario());		
					// domcilio
					// consulta dato anterior domcilio
					consulta = conveniosPA.buscarDomicilio(beneficiario2.getIdDomicilio());
					consultaAnterior = consulta == null ? null
							: consulta.toString();
					conveniosPA.actualizarDomicilioBeneficiario(beneficiario2);
					// consulta actualizacion domcilio
					consulta = conveniosPA.buscarDomicilio(beneficiario2.getIdDomicilio());
					consultaNueva = consulta == null ? null
							: consulta.toString();
					// inserta bitacora
					conveniosPA.bitacora(2, "SVT_DOMICILIO", consultaAnterior, consultaNueva,
							usuarioDto.getIdUsuario());
				}

				// plan
				// pasar a generado 1
				// consulta dato anterior plan
				consulta = conveniosPA.buscarContratoPa(plan.getIdConvenio());
				consultaAnterior = consulta == null ? null
						: consulta.toString();
				conveniosPA.actualizarEstatusConvenio(idUsuario, plan.getIdConvenio());
				// consulta actualizacion plan
				consulta = conveniosPA.buscarContratoPa(plan.getIdConvenio());
				consultaNueva = consulta == null ? null
						: consulta.toString();
				// inserta bitacora
				conveniosPA.bitacora(2, "SVT_PLAN_SFPA", consultaAnterior, consultaNueva,
						usuarioDto.getIdUsuario());
				session.commit();
			

				PreRegistrosXPAConBeneficiarios detalleConvenioPAPersona = consultaConveniosPA(plan.getIdConvenio());
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, detalleConvenioPAPersona);
			} catch (Exception e) {
				log.info(e.getMessage());

				log.info(ROLLBACK);
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}

		}

		// throw new UnsupportedOperationException("Unimplemented method
		// 'actualizarDatosPA'");
	}

	@Override
	public Response<Object> descargarDocumentos(DatosRequest request, Authentication authentication)
			throws IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode datos = mapper.readTree(request.getDatos().get(AppConstantes.DATOS)
					.toString());
			Integer idPaqueteConvenio = datos.get("idPaqueteConvenio").asInt();
			Integer idContratante = datos.get("idContratante").asInt();
			Integer idPersona = datos.get("idPersona").asInt();
			Integer tipoDocumento = datos.get("tipoDocumento").asInt();
			Integer tipoPersona = datos.get("tipoPersona").asInt();

			switch (tipoPersona) {
				case 1:

					return this.consultarDocumentoContratante(idPaqueteConvenio, idContratante, tipoDocumento);

				case 2:

					return this.consultarDocumentoBeneficiario(idPaqueteConvenio, idPersona, tipoDocumento);

			}

			return new Response<>(true, HttpStatus.INTERNAL_SERVER_ERROR.value(), AppConstantes.OCURRIO_ERROR_GENERICO,
					Arrays.asList());

		} catch (Exception e) {
			log.info(ERROR, e.getCause().getMessage());
			logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(),
					AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
					authentication);
			return new Response<>(true, HttpStatus.INTERNAL_SERVER_ERROR.value(), AppConstantes.OCURRIO_ERROR_GENERICO,
					Arrays.asList());
		}

	}

	private Response<Object> consultarDocumentoContratante(Integer idPaqueteConvenio, Integer idContratante,
			Integer tipoDocumento) {
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();

		String documento = null;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPF convenios = session.getMapper(ConvenioPF.class);
			switch (tipoDocumento) {
				case 1:
					documento = convenios.consultaInePDFContratante(idPaqueteConvenio, idContratante, 1);
					break;
				case 2:
					documento = convenios.consultaCurpPDFContratante(idPaqueteConvenio, idContratante, 1);
					break;
				case 3:
					documento = convenios.consultaRfcPDFContratante(idPaqueteConvenio, idContratante, 1);
					break;

				default:
					break;
			}
		}
		return new Response<>(false, HttpStatus.OK.value(), "Exito", documento);
	}

	private Response<Object> consultarDocumentoBeneficiario(Integer idPaqueteConvenio, Integer idPersona,
			Integer tipoDocumento) {
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();

		String documento = null;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPF convenios = session.getMapper(ConvenioPF.class);
			switch (tipoDocumento) {
				case 1:
					documento = convenios.consultaInePDFBeneficiario(idPaqueteConvenio, idPersona, 1);
					break;

				case 4:
					documento = convenios.consultaActaPDFBeneficiario(idPaqueteConvenio, idPersona, 1);
					break;

				default:
					break;
			}
		}
		return new Response<>(false, HttpStatus.OK.value(), "Exito", documento);
	}

}
