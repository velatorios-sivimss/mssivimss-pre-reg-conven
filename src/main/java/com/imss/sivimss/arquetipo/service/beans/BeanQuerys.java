package com.imss.sivimss.arquetipo.service.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.imss.sivimss.arquetipo.model.request.RequestFiltroPaginado;




@Service
public class BeanQuerys {
	private static final Logger log = LoggerFactory.getLogger(BeanQuerys.class);

	public String queryPreRegistros(RequestFiltroPaginado request) {
		StringBuilder  query= new StringBuilder ();
		StringBuilder  queryPers= new StringBuilder ();
		String union = " UNION ";
		StringBuilder queryEmp = new StringBuilder ();
		StringBuilder queryEmpPer = new StringBuilder ();
		String rfcPer = " AND SP.CVE_RFC = '" + request.getRfc() + "'";
		
		queryPers.append("SELECT SPS.ID_PLAN_SFPA AS idConvenioPlan, IFNULL(SPS.NUM_FOLIO_PLAN_SFPA, '') AS folioConvenio, STC.DES_TIPO_CONTRATACION AS tipoContratacion , IFNULL(SP.CVE_RFC, '') AS rfc,");
		queryPers.append(" CONCAT(SP.NOM_PERSONA, ' ', SP.NOM_PRIMER_APELLIDO , ' ', SP.NOM_SEGUNDO_APELLIDO) AS NombreAfiliadoTitular, SP2.REF_PAQUETE_NOMBRE AS tipoPaquete , 'p' AS tipoConvenio, 'PA' tipo");
		queryPers.append(" FROM SVT_PLAN_SFPA SPS ");
		queryPers.append(" JOIN SVC_TIPO_CONTRATACION STC ON STC.ID_TIPO_CONTRATACION = SPS.ID_TIPO_CONTRATACION ");
		queryPers.append(" JOIN SVC_CONTRATANTE SC ON sc.ID_CONTRATANTE = SPS.ID_TITULAR ");
		queryPers.append(" JOIN SVC_PERSONA SP ON SP.ID_PERSONA = sc.ID_PERSONA ");
		queryPers.append(" JOIN SVT_PAQUETE SP2 ON SP2.ID_PAQUETE = SPS.ID_PAQUETE ");
		queryPers.append(" JOIN SVC_ESTATUS_PLAN_SFPA SEPS ON SEPS.ID_ESTATUS_PLAN_SFPA = SPS.ID_ESTATUS_PLAN_SFPA ");
		queryPers.append(" WHERE SPS.ID_ESTATUS_PLAN_SFPA = 8 ");
	
		queryEmp.append("SELECT SCP.ID_CONVENIO_PF AS idConvenioPlan, IFNULL(SCP.DES_FOLIO, '') AS folioConvenio, 'Empresa' AS tipoContratacion, IFNULL(SECP.CVE_RFC, '') AS rfc,");
		queryEmp.append(" SECP.REF_NOMBRE AS NombreAfiliadoTitular, SP2.REF_PAQUETE_NOMBRE AS tipoPaquete, 'e' AS tipoConvenio , 'PF-Empresa' tipo ");
		queryEmp.append(" FROM SVT_CONVENIO_PF SCP");
		queryEmp.append(" LEFT JOIN SVT_EMPRESA_CONVENIO_PF SECP ON SECP.ID_CONVENIO_PF = scp.ID_CONVENIO_PF");
		queryEmp.append(" JOIN SVT_CONTRA_PAQ_CONVENIO_PF SCPCP ON SCPCP.ID_CONVENIO_PF = SCP.ID_CONVENIO_PF ");
		queryEmp.append(" JOIN SVC_CONTRATANTE SC ON SC.ID_CONTRATANTE = SCPCP.ID_CONTRATANTE ");
		queryEmp.append(" LEFT JOIN SVC_PERSONA SP ON SP.ID_PERSONA = SC.ID_PERSONA ");
		queryEmp.append(" JOIN SVT_PAQUETE SP2 ON SP2.ID_PAQUETE = scpcp.ID_PAQUETE");
		queryEmp.append(" WHERE SCP.ID_ESTATUS_CONVENIO = 5 AND SCP.IND_TIPO_CONTRATACION = 0");

		queryEmpPer.append("SELECT SCP.ID_CONVENIO_PF AS idConvenioPlan, IFNULL(SCP.DES_FOLIO, '') AS folioConvenio, 'Persona' AS tipoContratacion, IFNULL(SP.CVE_RFC, '') AS rfc,");
		queryEmpPer.append(" CONCAT(SP.NOM_PERSONA, ' ', SP.NOM_PRIMER_APELLIDO , ' ', SP.NOM_SEGUNDO_APELLIDO) AS NombreAfiliadoTitular, SP2.REF_PAQUETE_NOMBRE AS tipoPaquete, 'ep' AS tipoConvenio , 'PF Persona' tipo ");
		queryEmpPer.append(" FROM SVT_CONVENIO_PF SCP");
		queryEmpPer.append(" JOIN SVT_CONTRA_PAQ_CONVENIO_PF SCPCP ON SCPCP.ID_CONVENIO_PF = SCP.ID_CONVENIO_PF ");
		queryEmpPer.append(" JOIN SVC_CONTRATANTE SC ON SC.ID_CONTRATANTE = SCPCP.ID_CONTRATANTE ");
		queryEmpPer.append(" LEFT JOIN SVC_PERSONA SP ON SP.ID_PERSONA = SC.ID_PERSONA ");
		queryEmpPer.append(" JOIN SVT_PAQUETE SP2 ON SP2.ID_PAQUETE = scpcp.ID_PAQUETE");
		queryEmpPer.append(" WHERE SCP.ID_ESTATUS_CONVENIO = 5 AND SCP.IND_TIPO_CONTRATACION = 1");
		if(request.getConvenioPSFPA() != null)
			queryPers.append(" AND SPS.NUM_FOLIO_PLAN_SFPA = '" + request.getConvenioPSFPA() + "'");	
		if (request.getConvenioPF() != null) {
			queryEmp.append(" AND SCP.DES_FOLIO = '" + request.getConvenioPF() + "'");
			queryEmpPer.append(" AND SCP.DES_FOLIO = '" + request.getConvenioPF() + "'");	
		}
		if(request.getRfc() != null) {
			queryPers.append(rfcPer);
			queryEmp.append(" AND SECP.CVE_RFC = '" + request.getRfc() + "'");
			queryEmpPer.append(rfcPer);
		}
		
		
		if((request.getConvenioPSFPA() == null && request.getConvenioPF() == null && request.getRfc() == null) || (request.getConvenioPSFPA() != null && request.getConvenioPF() != null && request.getRfc() != null) || request.getRfc() != null)
			query.append(queryPers).append(union).append(queryEmp).append(union).append(queryEmpPer);
		else if(request.getConvenioPSFPA() != null && request.getConvenioPF() == null)
			query.append(queryPers);
		else if(request.getConvenioPSFPA() == null && request.getConvenioPF() != null )
			query.append(queryEmp).append(queryEmpPer);
		
		log.info(query.toString());
		return query.toString();
	}

	public String queryPreRegistrosXEmpresa(Integer idPreReg) {
		String query= "SELECT SCP.ID_CONVENIO_PF AS idConvenioPlan, SECP.REF_NOMBRE AS nombre, SECP.REF_RAZON_SOCIAL AS razonSoc, "
				+ "IFNULL(SECP.CVE_RFC, '') AS rfc, SP.DES_PAIS AS pais, SD.REF_CP AS cp, SD.REF_COLONIA AS colonia "
				+ ", SD.REF_ESTADO AS estado, SD.REF_MUNICIPIO AS municipio, SD.REF_CALLE AS calle, SD.NUM_EXTERIOR AS numExt "
				+ ", SD.NUM_EXTERIOR AS numInt, SECP.REF_TELEFONO AS telefono, SECP.REF_CORREO AS correo "
				+ " FROM SVT_CONVENIO_PF SCP "
				+ " JOIN SVT_EMPRESA_CONVENIO_PF SECP ON SECP.ID_CONVENIO_PF = SCP.ID_CONVENIO_PF  "
				+ " JOIN SVC_PAIS SP ON SP.ID_PAIS = SECP.ID_PAIS  "
				+ " JOIN SVT_DOMICILIO SD ON SD.ID_DOMICILIO = SECP.ID_DOMICILIO  "
				+ " WHERE SCP.ID_CONVENIO_PF = " + idPreReg ;
		log.info(query);
		return query;
		
	}

	public String queryPreRegPersonasEmpresa(Integer idPreReg) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT SC.ID_CONTRATANTE AS idContratante, IFNULL(SC.CVE_MATRICULA, '') AS matricula, IFNULL(SPE.CVE_RFC, '') AS rfc, IFNULL(SPE.CVE_CURP, '') AS curp,");
		query.append(" SPE.NOM_PERSONA AS nombre, SPE.NOM_PRIMER_APELLIDO AS primerApellido, SPE.NOM_SEGUNDO_APELLIDO AS segundoApellido, SD2.REF_CALLE AS calle,");
		query.append(" SD2.NUM_EXTERIOR AS numExt, SD2.NUM_INTERIOR AS numInt, SD2.REF_CP AS cp, SD2.REF_COLONIA AS colonia, SD2.REF_MUNICIPIO AS municipio, SD2.REF_ESTADO AS estado,");
		query.append(" SPE.ID_PAIS AS idPais, SPA.DES_PAIS AS pais, SPE.REF_CORREO AS correo, SPE.REF_TELEFONO AS telefono, SCPCP.IND_ENFERMEDAD_PREXISTENTE AS idenferpre");
		query.append(" FROM SVT_CONVENIO_PF SCP");
		query.append(" JOIN SVT_CONTRA_PAQ_CONVENIO_PF SCPCP ON SCPCP.ID_CONVENIO_PF = SCP.ID_CONVENIO_PF");
		query.append(" JOIN SVT_EMPRESA_CONVENIO_PF SCPE ON SCP.ID_CONVENIO_PF = SCPE.ID_CONVENIO_PF");
		query.append(" JOIN SVC_CONTRATANTE SC ON SC.ID_CONTRATANTE = SCPCP.ID_CONTRATANTE");
		query.append(" JOIN SVT_DOMICILIO SD2 ON SD2.ID_DOMICILIO = SC.ID_DOMICILIO");
		query.append(" JOIN SVC_PERSONA SPE ON SC.ID_PERSONA = SPE.ID_PERSONA");
		query.append(" LEFT JOIN SVC_PAIS SPA ON SPA.ID_PAIS = SPE.ID_PAIS  ");
		query.append(" WHERE SCPE.ID_CONVENIO_PF = " + idPreReg);
		log.info("{}",query.toString());
		return query.toString();
	}
	
	public String queryDocsEmpresa(Integer idPreReg) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT TD.idDoc, TD.totalDocs AS numeroDocumento, 'INE del afiliado' AS tipoDocumento, SVDCP.REF_UBICACION_INE AS nombreDoc, SVDCP.REF_DOC_INE_AFILIADO AS documento");
		query.append(" FROM SVC_VALIDA_DOCS_CONVENIO_PF SVDCP");
		query.append(" JOIN (SELECT LPAD(COUNT(SVDCPT.ID_CONVENIO_PF),4,'0') AS totalDocs, MAX(SVDCPT.ID_VALIDACION_DOCUMENTO) AS idDoc FROM SVC_VALIDA_DOCS_CONVENIO_PF SVDCPT");
		query.append("       WHERE (SVDCPT.REF_DOC_INE_AFILIADO IS NOT NULL OR SVDCPT.REF_DOC_INE_AFILIADO != '') AND SVDCPT.ID_CONVENIO_PF = " + idPreReg + " ) TD on TD.idDoc = SVDCP.ID_VALIDACION_DOCUMENTO");
		query.append(" UNION");
		query.append(" SELECT TD.idDoc, TD.totalDocs AS numeroDocumento, 'CURP del afiliado' AS tipoDocumento, SVDCP.REF_UBICACION_CURP AS nombreDoc, SVDCP.REF_DOC_CURP_AFILIADO AS documento");
		query.append(" FROM SVC_VALIDA_DOCS_CONVENIO_PF SVDCP");
		query.append(" JOIN (SELECT LPAD(COUNT(SVDCPT.ID_CONVENIO_PF),4,'0') AS totalDocs, MAX(SVDCPT.ID_VALIDACION_DOCUMENTO) AS idDoc FROM SVC_VALIDA_DOCS_CONVENIO_PF SVDCPT");
		query.append("       WHERE (SVDCPT.REF_DOC_CURP_AFILIADO IS NOT NULL OR SVDCPT.REF_DOC_CURP_AFILIADO != '')  AND SVDCPT.ID_CONVENIO_PF = " + idPreReg + " ) TD on TD.idDoc = SVDCP.ID_VALIDACION_DOCUMENTO");
		query.append(" UNION");
		query.append(" SELECT TD.idDoc, TD.totalDocs AS numeroDocumento, 'RFC del afiliado' AS tipoDocumento, SVDCP.REF_UBICACION_RFC AS nombreDoc, SVDCP.REF_DOC_RFC_AFILIADO AS documento");
		query.append(" FROM SVC_VALIDA_DOCS_CONVENIO_PF SVDCP");
		query.append(" JOIN (SELECT LPAD(COUNT(SVDCPT.ID_CONVENIO_PF),4,'0') AS totalDocs, MAX(SVDCPT.ID_VALIDACION_DOCUMENTO) AS idDoc FROM SVC_VALIDA_DOCS_CONVENIO_PF SVDCPT");
		query.append("       WHERE (SVDCPT.REF_DOC_RFC_AFILIADO IS NOT NULL OR SVDCPT.REF_DOC_RFC_AFILIADO != '') AND SVDCPT.ID_CONVENIO_PF = " + idPreReg + " ) TD on TD.idDoc = SVDCP.ID_VALIDACION_DOCUMENTO");
				
		log.info(query.toString());
		return query.toString();
	}
	
	
	
	
	public String queryPreRegistrosXPersona(Integer idPreReg) {
		String query= ""
		+ "SELECT "
		+ "SP.ID_PERSONA AS idPersona, "
		+ "		sc.ID_CONTRATANTE AS idContratante, "
		+ "		SD.ID_DOMICILIO AS idDomicilio, "
		+ "		SPS.ID_PLAN_SFPA AS folioConvenio, "
		+ "		SP.CVE_CURP AS curp, "
		+ "		SP.CVE_RFC AS rfc, "
		+ "		IFNULL(sc.CVE_MATRICULA, '') AS matricula, "
		+ "		SP.CVE_NSS AS nss, "
		+ "		SP.NOM_PERSONA AS nombre, "
		+ "		SP.NOM_PRIMER_APELLIDO AS primerApellido, "
		+ "		SP.NOM_SEGUNDO_APELLIDO AS segundoApellido, "
		+ "		SP.NUM_SEXO AS idSexo, "
		+ "		DATE_FORMAT(SP.FEC_NAC, '%d-%m-%Y') AS fecNacimiento, "
		+ "		SP2.DES_PAIS AS pais, "
		+ "		SP.ID_PAIS AS idPais, "
		+ "		se.DES_ESTADO AS lugarNac, "
		+ "		SP.ID_ESTADO AS idLugarNac, "
		+ "		SP.REF_TELEFONO AS telCelular, "
		+ "		SP.REF_TELEFONO_FIJO AS telFijo, "
		+ "		SP.REF_CORREO AS correo, "
		+ "		SD.REF_CALLE AS calle, "
		+ "		SD.NUM_EXTERIOR AS numExt, "
		+ "		SD.NUM_INTERIOR AS numInt, "
		+ "		SD.REF_CP AS cp, "
		+ "		SD.REF_COLONIA AS colonia, "
		+ "		SD.REF_MUNICIPIO AS municipio, "
		+ "		SD.REF_ESTADO AS estado, "
		+ "		SPS.ID_PAQUETE AS idPaquete, "
		+ "		stpm.DES_TIPO_PAGO_MENSUAL AS numPagos, "
		+ "		SP3.REF_PAQUETE_NOMBRE AS nomPaquete, "
		+ "		SPS.IND_TITULAR_SUBSTITUTO AS titularSust, "
		+ "		SPS.ID_TITULAR_SUBSTITUTO AS idTitularSust, "
		+ "		SPS.ID_BENEFICIARIO_1 AS beneficiario1, "
		+ "		SPS.ID_BENEFICIARIO_2 AS beneficiario2, "
		+ "		SPS.IND_PROMOTOR AS gestionPromotor "
		+ "	FROM "
		+ "		SVT_PLAN_SFPA SPS "
		+ "	JOIN SVC_CONTRATANTE sc ON "
		+ "		sc.ID_CONTRATANTE = SPS.ID_TITULAR "
		+ "	JOIN SVC_PERSONA SP ON "
		+ "		SP.ID_PERSONA = sc.ID_PERSONA "
		+ "	JOIN SVC_PAIS SP2 ON "
		+ "		SP2.ID_PAIS = SP.ID_PAIS "
		+ "	JOIN SVC_ESTADO se ON "
		+ "		se.ID_ESTADO = SP.ID_ESTADO "
		+ "	JOIN SVT_DOMICILIO SD ON "
		+ "		SD.ID_DOMICILIO = sc.ID_DOMICILIO "
		+ "	JOIN SVC_TIPO_PAGO_MENSUAL stpm ON "
		+ "		stpm.ID_TIPO_PAGO_MENSUAL = SPS.ID_TIPO_PAGO_MENSUAL "
		+ "	JOIN SVT_PAQUETE SP3 ON "
		+ "		SP3.ID_PAQUETE = SPS.ID_PAQUETE "
				+ " WHERE SPS.ID_PLAN_SFPA = " + idPreReg;
		log.info(query);
		return query;
	}
	
	public String queryCatPaquetes() {
		String query= "SELECT SP.ID_PAQUETE AS idPaquete, SP.REF_PAQUETE_NOMBRE AS nombrePaquete FROM SVT_PAQUETE SP WHERE SP.IND_ACTIVO = 1";
		log.info(query);
		return query;
	}

	public String queryBenefXEmpresa(Integer idPreReg) {
		String query= "SELECT CONCAT(SP.NOM_PERSONA, ' ', SP.NOM_PRIMER_APELLIDO, ' ', SP.NOM_SEGUNDO_APELLIDO) AS nombreCompleto "
				+ " , YEAR(CURDATE())-YEAR(SP.FEC_NAC)  AS edad, SP2.DES_PARENTESCO  AS parentesco "
				+ " , SCB.ID_PARENTESCO AS idParentesco, SP.CVE_CURP AS curp, SP.CVE_RFC AS rfc, IFNULL(SP.REF_CORREO,'') AS correo "
				+ " , IFNULL(SP.REF_TELEFONO,'') AS telefono "
				+ " FROM SVT_CONTRATANTE_BENEFICIARIOS SCB  "
				+ " JOIN SVT_CONTRA_PAQ_CONVENIO_PF SCPCP ON SCPCP.ID_CONTRA_PAQ_CONVENIO_PF = SCB.ID_CONTRA_PAQ_CONVENIO_PF  "
				+ " JOIN SVC_PERSONA SP ON SP.ID_PERSONA = SCB.ID_PERSONA  "
				+ " JOIN SVC_PARENTESCO SP2 ON SP2.ID_PARENTESCO = SCB.ID_PARENTESCO  "
				+ " WHERE SCB.IND_ACTIVO = 1 AND SCPCP.ID_CONVENIO_PF = " + idPreReg;
		log.info(query);
		return query;
	}

	public String queryTitularSustituto(Integer idTituSust) {
		String query= "" +
				"SELECT "
				+ "		SP.CVE_CURP AS curp, "
				+ "		SP.CVE_RFC AS rfc, "
				+ "		stb.CVE_MATRICULA AS matricula, "
				+ "		SP.CVE_NSS AS nss, "
				+ "		SP.NOM_PERSONA AS nombre, "
				+ "		SP.NOM_PRIMER_APELLIDO AS primerApellido, "
				+ "		SP.NOM_SEGUNDO_APELLIDO AS segundoApellido, "
				+ "		SP.NUM_SEXO AS idSexo, "
				+ "		SP.FEC_NAC AS fecNacimiento, "
				+ "		SP.ID_PAIS AS idPais, "
				+ "		se.ID_ESTADO AS lugarNac, "
				+ "		SP.REF_TELEFONO_FIJO AS telFijo, "
				+ "		SP.REF_TELEFONO AS telCelular, "
				+ "		SP.REF_CORREO AS correo, "
				+ "		SD.REF_CALLE AS calle, "
				+ "		SD.NUM_EXTERIOR AS numExt, "
				+ "		SD.NUM_INTERIOR AS numInt, "
				+ "		SP.CVE_CURP AS cp, "
				+ "		SD.REF_COLONIA AS colonia, "
				+ "		SD.REF_MUNICIPIO AS municipio, "
				+ "		SP.ID_ESTADO AS idEstado, "
				+ "		SD.REF_ESTADO AS estado "
				+ "	FROM "
				+ "		SVT_TITULAR_BENEFICIARIOS stb "
				+ "	JOIN SVC_PERSONA SP ON "
				+ "		SP.ID_PERSONA = stb.ID_PERSONA "
				+ "JOIN SVC_ESTADO se ON "
				+ "		se.ID_ESTADO = SP.ID_ESTADO "
				+ "	JOIN SVT_DOMICILIO SD ON "
				+ "		SD.ID_DOMICILIO = stb.ID_DOMICILIO "
				+ " WHERE SPS.ID_PLAN_SFPA = " + idTituSust;
		log.info(query);
		return query;
	}
	public String queryBenefxPersona(Integer idBenef) {
		String query= ""
				+ " SELECT "
				+ " 	stb.ID_TITULAR_BENEFICIARIOS idBeneficiario, "
				+ "		SP.CVE_CURP AS curp, "
				+ " 	SP.CVE_RFC AS rfc, "
				+ " 	stb.CVE_MATRICULA AS matricula, "
				+ " 	SP.CVE_NSS AS nss, "
				+ " 	SP.NOM_PERSONA AS nombre, "
				+ " 	SP.NOM_PRIMER_APELLIDO AS primerApellido, "
				+ " 	SP.NOM_SEGUNDO_APELLIDO AS segundoApellido, "
				+ " 	SP.NUM_SEXO AS idSexo, "
				+ " 	SP.FEC_NAC AS fecNacimiento, "
				+ " 	SP.ID_PAIS AS idPais, "
				+ " 	se.ID_ESTADO AS lugarNac, "
				+ " 	SP.REF_TELEFONO_FIJO AS telFijo, "
				+ " 	SP.REF_TELEFONO AS telCelular, "
				+ " 	SP.REF_CORREO AS correo, "
				+ " 	SD.REF_CALLE AS calle, "
				+ " 	SD.NUM_EXTERIOR AS numExt, "
				+ " 	SD.NUM_INTERIOR AS numInt, "
				+ " 	SP.CVE_CURP AS cp, "
				+ " 	SD.REF_COLONIA AS colonia, "
				+ " 	SD.REF_MUNICIPIO AS municipio, "
				+ " 	SP.ID_ESTADO AS idEstado, "
				+ " 	SD.REF_ESTADO AS estado "
				+ " FROM "
				+ " 	SVT_TITULAR_BENEFICIARIOS stb "
				+ " JOIN SVC_PERSONA SP ON "
				+ " 	SP.ID_PERSONA = stb.ID_PERSONA "
				+ " JOIN SVC_ESTADO se ON "
				+ " 	se.ID_ESTADO = SP.ID_ESTADO "
				+ " JOIN SVT_DOMICILIO SD ON "
				+ " 	SD.ID_DOMICILIO = stb.ID_DOMICILIO "
				+ "WHERE stb.ID_TITULAR_BENEFICIARIOS = " + idBenef ;
		log.info(query);
		return query;
	}
	public String queryBeneficiarios1(Integer idPreReg) {
		String query= "SELECT SPS.ID_PLAN_SFPA, SP.CVE_CURP AS curp, IFNULL(SP.CVE_RFC,'') AS rfc, IFNULL(su.CVE_MATRICULA,'') AS matricula, SP.CVE_NSS AS nss "
				+ ", SP.NOM_PERSONA AS nombre, SP.NOM_PRIMER_APELLIDO AS primerApellido, SP.NOM_SEGUNDO_APELLIDO AS segundoApellido, SP.NUM_SEXO AS sexo "
				+ ", DATE_FORMAT(SP.FEC_NAC, '%d-%m-%Y') AS fecNacimiento, SP2.DES_PAIS AS pais, SP.ID_PAIS AS idPais, se.DES_ESTADO AS lugarNac "
				+ ", SP.ID_ESTADO AS idLugarNac, SP.REF_TELEFONO AS telCelular, SP.REF_TELEFONO_FIJO AS telFijo, SP.REF_CORREO AS correo, SD.REF_CALLE AS calle, SD.NUM_EXTERIOR AS numExt, SD.NUM_INTERIOR AS numInt "
				+ ", SD.REF_CP AS cp, SD.REF_COLONIA AS colonia, SD.REF_MUNICIPIO AS municipio, SD.REF_ESTADO AS estado  "
				+ " FROM SVT_PLAN_SFPA SPS  "
				+ " JOIN SVC_CONTRATANTE sc ON sc.ID_CONTRATANTE = SPS.ID_BENEFICIARIO_1  "
				+ " JOIN SVC_PERSONA SP ON sc.ID_PERSONA = SP.ID_PERSONA  "
				+ " LEFT JOIN SVT_USUARIOS su ON su.ID_PERSONA = SP.ID_PERSONA  "
				+ " LEFT JOIN SVC_PAIS SP2 ON SP2.ID_PAIS = SP.ID_PAIS  "
				+ " LEFT JOIN SVC_ESTADO se ON se.ID_ESTADO = SP.ID_ESTADO  "
				+ " LEFT JOIN SVT_DOMICILIO SD ON SD.ID_DOMICILIO = sc.ID_DOMICILIO  "
				+ " WHERE SPS.ID_PLAN_SFPA = " + idPreReg 
				+ " UNION "
				+ " SELECT SPS.ID_PLAN_SFPA, SP.CVE_CURP AS curp, IFNULL(SP.CVE_RFC,'') AS rfc, IFNULL(su.CVE_MATRICULA,'') AS matricula, SP.CVE_NSS AS nss "
				+ ", SP.NOM_PERSONA AS nombre, SP.NOM_PRIMER_APELLIDO AS primerApellido, SP.NOM_SEGUNDO_APELLIDO AS segundoApellido, SP.NUM_SEXO AS sexo "
				+ ", DATE_FORMAT(SP.FEC_NAC, '%d-%m-%Y') AS fecNacimiento, SP2.DES_PAIS AS pais, SP.ID_PAIS AS idPais, se.DES_ESTADO AS lugarNac "
				+ ", SP.ID_ESTADO AS idLugarNac, SP.REF_TELEFONO AS telCelular, SP.REF_TELEFONO_FIJO AS telFijo, SP.REF_CORREO AS correo, SD.REF_CALLE AS calle, SD.NUM_EXTERIOR AS numExt, SD.NUM_INTERIOR AS numInt "
				+ ", SD.REF_CP AS cp, SD.REF_COLONIA AS colonia, SD.REF_MUNICIPIO AS municipio, SD.REF_ESTADO AS estado  "
				+ " FROM SVT_PLAN_SFPA SPS  "
				+ " JOIN SVC_CONTRATANTE sc ON sc.ID_CONTRATANTE = SPS.ID_BENEFICIARIO_2 "
				+ " JOIN SVC_PERSONA SP ON sc.ID_PERSONA = SP.ID_PERSONA  "
				+ " LEFT JOIN SVT_USUARIOS su ON su.ID_PERSONA = SP.ID_PERSONA  "
				+ " LEFT JOIN SVC_PAIS SP2 ON SP2.ID_PAIS = SP.ID_PAIS  "
				+ " LEFT JOIN SVC_ESTADO se ON se.ID_ESTADO = SP.ID_ESTADO  "
				+ " LEFT JOIN SVT_DOMICILIO SD ON SD.ID_DOMICILIO = sc.ID_DOMICILIO  "
				+ " WHERE SPS.ID_PLAN_SFPA = " + idPreReg;
		log.info(query);
		return query;
	}

	public String queryCatPromotores() {
		String query= "SELECT SP.ID_PROMOTOR AS idPromotor, SP.NUM_EMPLEDO AS numEmpleado "
				+ ", CONCAT_WS(' ' ,SP.NOM_PROMOTOR, SP.NOM_PAPELLIDO, SP.NOM_SAPELLIDO) AS nombrePromotor "
				+ " FROM SVT_PROMOTOR SP WHERE SP.IND_ACTIVO = 1";
		log.info(query);
		return query;
	}
	
	
	public String queryActDesactConvenioPer(Integer idPreReg) {
		String query= "UPDATE SVT_PLAN_SFPA SET IND_ACTIVO = !IND_ACTIVO WHERE ID_PLAN_SFPA = " + idPreReg;
		log.info(query);
		return query;
	}
}
