package com.imss.sivimss.arquetipo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imss.sivimss.arquetipo.configuration.MyBatisConfig;
import com.imss.sivimss.arquetipo.configuration.mapper.ConvenioPA;
import com.imss.sivimss.arquetipo.configuration.mapper.ConvenioPF;
import com.imss.sivimss.arquetipo.configuration.mapper.Empresas;
import com.imss.sivimss.arquetipo.configuration.mapper.Personas;
import com.imss.sivimss.arquetipo.model.entity.BenefXPA;
import com.imss.sivimss.arquetipo.model.entity.DatosConvenio;
import com.imss.sivimss.arquetipo.model.entity.DatosEmpresa;
import com.imss.sivimss.arquetipo.model.entity.DatosEmpresaBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.DatosEmpresaSolicitante;
import com.imss.sivimss.arquetipo.model.entity.DatosPersonaBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.DatosPersonaConvenio;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresa;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresaBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresaBeneficiariosDocs;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresaSolicitantes;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXPersona;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXPersonaBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXPersonaBeneficiariosDocs;
import com.imss.sivimss.arquetipo.model.entity.PreRegistrosXPA;
import com.imss.sivimss.arquetipo.model.entity.PreRegistrosXPAConBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.PreRegistrosXPFEmpresaConSolicitantes;
import com.imss.sivimss.arquetipo.model.entity.PreRegistrosXPFPersonaConBeneficiarios;
import com.imss.sivimss.arquetipo.model.request.ActualizarConvenioPersona;
import com.imss.sivimss.arquetipo.model.request.ActualizarDatosEmpresa;
import com.imss.sivimss.arquetipo.model.request.ActualizarDatosPA;
import com.imss.sivimss.arquetipo.model.request.Flujos;
import com.imss.sivimss.arquetipo.model.request.PlanPAData;
import com.imss.sivimss.arquetipo.model.request.PlanPASustituto;
import com.imss.sivimss.arquetipo.model.request.RequestFiltroPaginado;
import com.imss.sivimss.arquetipo.model.request.UsuarioDto;
import com.imss.sivimss.arquetipo.model.request.ValidarRfcCurpContratante;
import com.imss.sivimss.arquetipo.model.response.ResponseContratanteRfcCurp;
import com.imss.sivimss.arquetipo.service.PreRegConvServiceNuevo;
import com.imss.sivimss.arquetipo.service.beans.BeanQuerys;
import com.imss.sivimss.arquetipo.utils.AppConstantes;
import com.imss.sivimss.arquetipo.utils.DatosRequest;
import com.imss.sivimss.arquetipo.utils.PaginadoUtil;
import com.imss.sivimss.arquetipo.utils.Response;
import org.springframework.security.core.Authentication;

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

	@Override
	public Response<Object> actualizarDatosPersona(DatosRequest request) {
		Gson gson = new Gson();
		String datos = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		log.info(datos);

		ActualizarConvenioPersona actualizarConvenioPersona = gson.fromJson(datos, ActualizarConvenioPersona.class);
		ArrayList<DatosPersonaBeneficiarios> beneficiarios = actualizarConvenioPersona.getBeneficiarios();
		DatosPersonaConvenio personaConvenio = actualizarConvenioPersona.getConvenio();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			Personas personasMap = session.getMapper(Personas.class);

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

				session.commit();
				log.info("==> commit() ");
				// pasar a generado id 1

				PreRegistrosXPFPersonaConBeneficiarios detalleConvenioPFPersona = consultaConveniosPFPersona(
						personaConvenio.getIdConvenioPF());
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, detalleConvenioPFPersona);
			} catch (Exception e) {
				log.info(e.getMessage());

				log.info("==> rollback() ");
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

				// session.commit();
				log.info("==> commit() ");

			} catch (Exception e) {
				log.info(e.getMessage());

				log.info("==> rollback() ");
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
	public Response<Object> actDesactConvenio(DatosRequest request) {
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
		log.info("idFlujo " + idFlujo);
		log.info("idConvenioPf " + idConvenioPf);

		int result = 0;
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPF convenios = session.getMapper(ConvenioPF.class);
			try {
				switch (idFlujo) {
					case 1:
						log.info("Flujo " + idFlujo);
						result = convenios.activarDesactivarConvenioPA(idConvenioPf);
						session.commit();

						Response<Object> aa = preRegXConvenios(request);
						PreRegistrosXPAConBeneficiarios convenioPA = (PreRegistrosXPAConBeneficiarios) aa.getDatos();
						log.info("result " + convenioPA.getPreRegistro().getActivo());

						return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,
								convenioPA.getPreRegistro().getActivo());

					case 2:
						log.info("Flujo " + idFlujo);
						result = convenios.activarDesactivarConvenioPF(idConvenioPf);
						session.commit();

						Response<Object> cc = preRegXConvenios(request);
						PreRegistrosXPFEmpresaConSolicitantes convenioPFEmpresa = (PreRegistrosXPFEmpresaConSolicitantes) cc
								.getDatos();
						log.info("result " + convenioPFEmpresa.getEmpresa().getActivo());

						return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,
								convenioPFEmpresa.getEmpresa().getActivo());

					case 3:
						log.info("Flujo " + idFlujo);

						result = convenios.activarDesactivarConvenioPF(idConvenioPf);
						session.commit();

						Response<Object> dd = preRegXConvenios(request);
						PreRegistrosXPFPersonaConBeneficiarios detalleConvenioPFPersona = (PreRegistrosXPFPersonaConBeneficiarios) dd
								.getDatos();
						log.info("result " + detalleConvenioPFPersona.getDetalleConvenioPFModel().getActivo());

						return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,
								detalleConvenioPFPersona.getDetalleConvenioPFModel().getActivo());

				}

			} catch (Exception e) {
				e.printStackTrace();
				session.rollback();
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}
		}

		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
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
		log.info("idFlujo " + idFlujo);
		log.info("idConvenioPf " + idConvenioPf);

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

		}

		return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);

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
		log.info("idConvenioPf " + idConvenioPf);

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
		log.info("idFlujo " + idFlujo);
		log.info("idConvenioPf " + idConvenioPf);

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

		}

		return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);

	}

	public ArrayList<DetalleConvenioPFXEmpresaBeneficiariosDocs> consultaConveniosPFEmpresaDocs(Integer idConvenioPf) {
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		DetalleConvenioPFXEmpresa detalleConvenioPFModel = null;
		ArrayList<DetalleConvenioPFXEmpresaSolicitantes> solicitantes = new ArrayList<>();
		PreRegistrosXPFEmpresaConSolicitantes convenioPFEmpresa = new PreRegistrosXPFEmpresaConSolicitantes();
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
	public Response<Object> actualizarDatosPA(DatosRequest request) {
		Gson gson = new Gson();
		String datos = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		log.info(datos);

		ActualizarDatosPA datosPlanPA = gson.fromJson(datos, ActualizarDatosPA.class);
		PlanPAData plan = datosPlanPA.getPlan();
		PlanPASustituto titularSustituto = datosPlanPA.getTitularSustituto();

		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();

		try(SqlSession session = sqlSessionFactory.openSession()) {
			ConvenioPA conveniosPA = session.getMapper(ConvenioPA.class);

			try {
				conveniosPA.actualizarDatosPlan(plan);
				conveniosPA.actualizarDatosContratante(plan);
				conveniosPA.actualizarDomicilioContratante(plan);

				conveniosPA.actualizarDatosSustituto(titularSustituto);
				conveniosPA.actualizarDomicilioSustituto(titularSustituto);

				session.commit();
				log.info("==> commit() ");

				// pasar a generado 1

				PreRegistrosXPAConBeneficiarios detalleConvenioPAPersona = consultaConveniosPA(plan.getIdConvenio());
				return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, detalleConvenioPAPersona);
			} catch (Exception e) {
				log.info(e.getMessage());
				
				log.info("==> rollback() ");
				return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
			}

		}

		// throw new UnsupportedOperationException("Unimplemented method 'actualizarDatosPA'");
	}

}
