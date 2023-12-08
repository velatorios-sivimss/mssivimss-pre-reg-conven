package com.imss.sivimss.arquetipo.service.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;




@Service
public class BeanQuerys {
	private static final Logger log = LoggerFactory.getLogger(BeanQuerys.class);

	public String queryPreRegistrosXEmpresa(Integer idPreReg) {
		String query= "SELECT scp.ID_CONVENIO_PF AS idConvenioPlan, secp.REF_NOMBRE AS nombre, secp.REF_RAZON_SOCIAL AS razonSoc, "
				+ "IFNULL(secp.CVE_RFC, '') AS rfc, sp.DES_PAIS AS pais, sd.REF_CP AS cp, sd.REF_COLONIA AS colonia "
				+ ", sd.REF_ESTADO AS estado, sd.REF_MUNICIPIO AS municipio, sd.REF_CALLE AS calle, sd.NUM_EXTERIOR AS numExt "
				+ ", sd.NUM_EXTERIOR AS numInt, secp.REF_TELEFONO AS telefono, secp.REF_CORREO AS correo "
				+ " FROM SVT_CONVENIO_PF scp "
				+ " JOIN SVT_EMPRESA_CONVENIO_PF secp ON secp.ID_CONVENIO_PF = scp.ID_CONVENIO_PF  "
				+ " JOIN SVC_PAIS sp ON sp.ID_PAIS = secp.ID_PAIS  "
				+ " JOIN SVT_DOMICILIO sd ON sd.ID_DOMICILIO = secp.ID_DOMICILIO  "
				+ " WHERE scp.ID_CONVENIO_PF = " + idPreReg ;
		log.info(query);
		return query;
		
	}

	public String queryPreRegistros() {
		String query= "SELECT sps.ID_PLAN_SFPA AS idConvenioPlan, IFNULL(sps.NUM_FOLIO_PLAN_SFPA,'') AS folioConvenio, stc.DES_TIPO_CONTRATACION AS tipoContratacion "
				+ ", IFNULL(sp.CVE_RFC,'')  AS rfc, CONCAT(sp.NOM_PERSONA, ' ', sp.NOM_PRIMER_APELLIDO  , ' ',sp.NOM_SEGUNDO_APELLIDO) AS NombreAfiliadoTitular, sp2.REF_PAQUETE_NOMBRE AS tipoPaquete "
				+ "FROM SVT_PLAN_SFPA sps  "
				+ "JOIN SVC_TIPO_CONTRATACION stc ON stc.ID_TIPO_CONTRATACION = sps.ID_TIPO_CONTRATACION  "
				+ "JOIN SVC_CONTRATANTE sc ON sc.ID_CONTRATANTE = sps.ID_TITULAR  "
				+ "JOIN SVC_PERSONA sp ON sp.ID_PERSONA = sc.ID_PERSONA  "
				+ "JOIN SVT_PAQUETE sp2 ON sp2.ID_PAQUETE = sps.ID_PAQUETE  "
				+ "JOIN SVC_ESTATUS_PLAN_SFPA seps ON seps.ID_ESTATUS_PLAN_SFPA = sps.ID_ESTATUS_PLAN_SFPA  "
				+ "WHERE sps.ID_ESTATUS_PLAN_SFPA = 8 "
				+ "UNION "
				+ "SELECT scp.ID_ESTATUS_CONVENIO AS idConvenioPlan , "
				+ "IFNULL(scp.DES_FOLIO,'') AS folioConvenio, stc.DES_TIPO_CONTRATACION AS tipoContratacion ,  "
				+ "CASE WHEN scp.IND_TIPO_CONTRATACION = 0 THEN IFNULL(secp.CVE_RFC, '') "
				+ "   WHEN scp.IND_TIPO_CONTRATACION = 1 THEN IFNULL(sp.CVE_RFC,'')  "
				+ "END AS rfc,   "
				+ "CASE WHEN scp.IND_TIPO_CONTRATACION = 0 THEN secp.REF_NOMBRE "
				+ "   WHEN scp.IND_TIPO_CONTRATACION = 1 THEN CONCAT(sp.NOM_PERSONA, ' ', sp.NOM_PRIMER_APELLIDO  , ' ',sp.NOM_SEGUNDO_APELLIDO)  "
				+ "END AS NombreAfiliadoTitular, sp2.REF_PAQUETE_NOMBRE  AS tipoPaquete "
				+ "FROM SVT_CONVENIO_PF scp  "
				+ "JOIN SVT_CONTRA_PAQ_CONVENIO_PF scpcp ON scpcp.ID_CONVENIO_PF = scp.ID_CONVENIO_PF  "
				+ "JOIN SVT_PAQUETE sp2 ON sp2.ID_PAQUETE = scpcp.ID_PAQUETE  "
				+ "JOIN SVC_TIPO_CONTRATACION stc ON stc.ID_TIPO_CONTRATACION = scp.IND_TIPO_CONTRATACION "
				+ "LEFT JOIN SVT_EMPRESA_CONVENIO_PF secp ON secp.ID_CONVENIO_PF = scp.ID_CONVENIO_PF  "
				+ "LEFT JOIN SVC_FINADO sf ON sf.ID_CONTRATO_PREVISION = scp.ID_CONVENIO_PF "
				+ "JOIN SVC_PERSONA sp ON sp.ID_PERSONA = sf.ID_PERSONA  "
				+ "WHERE scp.ID_ESTATUS_CONVENIO = 5";
		log.info(query);
		return query;
	}
	
	
	public String queryPreRegistrosXPersona(Integer idPreReg) {
		String query= "SELECT sps.ID_PLAN_SFPA AS folioConvenio, sp.CVE_CURP AS curp, sp.CVE_RFC AS rfc, IFNULL(sc.CVE_MATRICULA,'') AS matricula, sp.CVE_NSS AS nss"
				+ ", sp.NOM_PERSONA AS nombre, sp.NOM_PRIMER_APELLIDO AS primerApellido, sp.NOM_SEGUNDO_APELLIDO AS segundoApellido, sp.NUM_SEXO AS idSexo "
				+ ", DATE_FORMAT(sp.FEC_NAC, '%d-%m-%Y') AS fecNacimiento, sp2.DES_PAIS AS pais, sp.ID_PAIS AS idPais, se.DES_ESTADO AS lugarNac, sp.ID_ESTADO AS idLugarNac "
				+ ", sp.REF_TELEFONO AS telCelular, sp.REF_TELEFONO_FIJO AS telFijo, sp.REF_CORREO AS correo, sd.REF_CALLE AS calle, sd.NUM_EXTERIOR AS numExt, sd.NUM_INTERIOR AS numInt "
				+ ", sd.REF_CP AS cp, sd.REF_COLONIA AS colonia, sd.REF_MUNICIPIO AS municipio, sd.REF_ESTADO AS estado, sps.ID_PAQUETE AS idPaquete, stpm.DES_TIPO_PAGO_MENSUAL AS numPagos "
				+ " FROM SVT_PLAN_SFPA sps "
				+ " JOIN SVC_CONTRATANTE sc ON sc.ID_CONTRATANTE = sps.ID_TITULAR " 
				+ " JOIN SVC_PERSONA sp ON sp.ID_PERSONA = sc.ID_PERSONA "
				+ " JOIN SVC_PAIS sp2 ON sp2.ID_PAIS = sp.ID_PAIS "
				+ " JOIN SVC_ESTADO se ON se.ID_ESTADO = sp.ID_ESTADO " 
				+ " JOIN SVT_DOMICILIO sd ON sd.ID_DOMICILIO = sc.ID_DOMICILIO " 
				+ " JOIN SVC_TIPO_PAGO_MENSUAL stpm ON stpm.ID_TIPO_PAGO_MENSUAL = sps.ID_TIPO_PAGO_MENSUAL " 
				+ " WHERE sps.ID_PLAN_SFPA = " + idPreReg;
		log.info(query);
		return query;
	}
	
	public String queryCatPaquetes() {
		String query= "SELECT sp.ID_PAQUETE AS idPaquete, sp.REF_PAQUETE_NOMBRE AS nombrePaquete FROM SVT_PAQUETE sp WHERE sp.IND_ACTIVO = 1";
		log.info(query);
		return query;
	}

	public String queryBenefXEmpresa(Integer idPreReg) {
		String query= "SELECT CONCAT(sp.NOM_PERSONA, ' ', sp.NOM_PRIMER_APELLIDO, ' ', sp.NOM_SEGUNDO_APELLIDO) AS nombreCompleto "
				+ " , YEAR(CURDATE())-YEAR(sp.FEC_NAC)  AS edad, sp2.DES_PARENTESCO  AS parentesco "
				+ " , scb.ID_PARENTESCO AS idParentesco, sp.CVE_CURP AS curp, sp.CVE_RFC AS rfc, IFNULL(sp.REF_CORREO,'') AS correo "
				+ " , IFNULL(sp.REF_TELEFONO,'') AS telefono "
				+ " FROM SVT_CONTRATANTE_BENEFICIARIOS scb  "
				+ " JOIN SVT_CONTRA_PAQ_CONVENIO_PF scpcp ON scpcp.ID_CONTRA_PAQ_CONVENIO_PF = scb.ID_CONTRA_PAQ_CONVENIO_PF  "
				+ " JOIN SVT_CONVENIO_PF scp ON scp.ID_CONVENIO_PF = scpcp.ID_CONVENIO_PF  "
				+ " JOIN SVC_PERSONA sp ON sp.ID_PERSONA = scb.ID_PERSONA  "
				+ " JOIN SVC_PARENTESCO sp2 ON sp2.ID_PARENTESCO = scb.ID_PARENTESCO  "
				+ " WHERE scp.ID_CONVENIO_PF = " + idPreReg;
		log.info(query);
		return query;
	}

	public String queryTitularSustituto(Integer idTituSust) {
		String query= "SELECT sp.CVE_CURP AS curp, IFNULL(sp.CVE_RFC,'') AS rfc, IFNULL(su.CVE_MATRICULA,'') AS matricula  "
				+ ", IFNULL(sp.CVE_NSS,'') AS nss, sp.NOM_PERSONA AS nombre, sp.NOM_PRIMER_APELLIDO AS primerApellido, sp.NOM_SEGUNDO_APELLIDO AS segundoApellido "
				+ ", sp.NUM_SEXO AS idSexo, DATE_FORMAT(sp.FEC_NAC, '%d/%m/%Y') AS fecNacimiento, sp2.DES_PAIS AS pais, sp.ID_PAIS AS idPais, se.DES_ESTADO AS lugarNac "
				+ ", sp.ID_ESTADO AS idLugarNac "
				+ ", sp.REF_TELEFONO AS telCelular, sp.REF_TELEFONO_FIJO AS telFijo, sp.REF_CORREO AS correo, sd.REF_CALLE AS calle, sd.NUM_EXTERIOR AS numExt, sd.NUM_INTERIOR AS numInt "
				+ ", sd.REF_CP AS cp, sd.REF_COLONIA AS colonia, sd.REF_MUNICIPIO AS municipio, sd.REF_ESTADO AS estado "
				+ " FROM SVT_PLAN_SFPA sps  "
				+ " JOIN SVC_CONTRATANTE sc ON sc.ID_CONTRATANTE = sps.ID_TITULAR_SUBSTITUTO  "
				+ " JOIN SVC_PERSONA sp ON sp.ID_PERSONA = sc.ID_PERSONA  "
				+ " LEFT JOIN SVT_USUARIOS su ON su.ID_PERSONA = sp.ID_PERSONA  "
				+ " LEFT JOIN SVC_PAIS sp2 ON sp2.ID_PAIS = sp.ID_PAIS  "
				+ " JOIN SVC_ESTADO se ON se.ID_ESTADO = sp.ID_ESTADO  "
				+ " JOIN SVT_DOMICILIO sd ON sd.ID_DOMICILIO = sc.ID_DOMICILIO  "
				+ " JOIN SVC_TIPO_PAGO_MENSUAL stpm ON stpm.ID_TIPO_PAGO_MENSUAL = sps.ID_TIPO_PAGO_MENSUAL  "
				+ " WHERE sps.ID_PLAN_SFPA = " + idTituSust;
		log.info(query);
		return query;
	}
	
	public String queryBeneficiarios(Integer idPreReg) {
		String query= "SELECT sps.ID_PLAN_SFPA, sp.CVE_CURP AS curp, IFNULL(sp.CVE_RFC,'') AS rfc, IFNULL(su.CVE_MATRICULA,'') AS matricula, sp.CVE_NSS AS nss "
				+ ", sp.NOM_PERSONA AS nombre, sp.NOM_PRIMER_APELLIDO AS primerApellido, sp.NOM_SEGUNDO_APELLIDO AS segundoApellido, sp.NUM_SEXO AS sexo "
				+ ", DATE_FORMAT(sp.FEC_NAC, '%d-%m-%Y') AS fecNacimiento, sp2.DES_PAIS AS pais, sp.ID_PAIS AS idPais, se.DES_ESTADO AS lugarNac "
				+ ", sp.ID_ESTADO AS idLugarNac, sp.REF_TELEFONO AS telCelular, sp.REF_TELEFONO_FIJO AS telFijo, sp.REF_CORREO AS correo, sd.REF_CALLE AS calle, sd.NUM_EXTERIOR AS numExt, sd.NUM_INTERIOR AS numInt "
				+ ", sd.REF_CP AS cp, sd.REF_COLONIA AS colonia, sd.REF_MUNICIPIO AS municipio, sd.REF_ESTADO AS estado  "
				+ " FROM SVT_PLAN_SFPA sps  "
				+ " JOIN SVC_CONTRATANTE sc ON sc.ID_CONTRATANTE = sps.ID_BENEFICIARIO_1  "
				+ " JOIN SVC_PERSONA sp ON sc.ID_PERSONA = sp.ID_PERSONA  "
				+ " LEFT JOIN SVT_USUARIOS su ON su.ID_PERSONA = sp.ID_PERSONA  "
				+ " LEFT JOIN SVC_PAIS sp2 ON sp2.ID_PAIS = sp.ID_PAIS  "
				+ " LEFT JOIN SVC_ESTADO se ON se.ID_ESTADO = sp.ID_ESTADO  "
				+ " LEFT JOIN SVT_DOMICILIO sd ON sd.ID_DOMICILIO = sc.ID_DOMICILIO  "
				+ " WHERE sps.ID_PLAN_SFPA = " + idPreReg 
				+ " UNION "
				+ " SELECT sps.ID_PLAN_SFPA, sp.CVE_CURP AS curp, IFNULL(sp.CVE_RFC,'') AS rfc, IFNULL(su.CVE_MATRICULA,'') AS matricula, sp.CVE_NSS AS nss "
				+ ", sp.NOM_PERSONA AS nombre, sp.NOM_PRIMER_APELLIDO AS primerApellido, sp.NOM_SEGUNDO_APELLIDO AS segundoApellido, sp.NUM_SEXO AS sexo "
				+ ", DATE_FORMAT(sp.FEC_NAC, '%d-%m-%Y') AS fecNacimiento, sp2.DES_PAIS AS pais, sp.ID_PAIS AS idPais, se.DES_ESTADO AS lugarNac "
				+ ", sp.ID_ESTADO AS idLugarNac, sp.REF_TELEFONO AS telCelular, sp.REF_TELEFONO_FIJO AS telFijo, sp.REF_CORREO AS correo, sd.REF_CALLE AS calle, sd.NUM_EXTERIOR AS numExt, sd.NUM_INTERIOR AS numInt "
				+ ", sd.REF_CP AS cp, sd.REF_COLONIA AS colonia, sd.REF_MUNICIPIO AS municipio, sd.REF_ESTADO AS estado  "
				+ " FROM SVT_PLAN_SFPA sps  "
				+ " JOIN SVC_CONTRATANTE sc ON sc.ID_CONTRATANTE = sps.ID_BENEFICIARIO_2 "
				+ " JOIN SVC_PERSONA sp ON sc.ID_PERSONA = sp.ID_PERSONA  "
				+ " LEFT JOIN SVT_USUARIOS su ON su.ID_PERSONA = sp.ID_PERSONA  "
				+ " LEFT JOIN SVC_PAIS sp2 ON sp2.ID_PAIS = sp.ID_PAIS  "
				+ " LEFT JOIN SVC_ESTADO se ON se.ID_ESTADO = sp.ID_ESTADO  "
				+ " LEFT JOIN SVT_DOMICILIO sd ON sd.ID_DOMICILIO = sc.ID_DOMICILIO  "
				+ " WHERE sps.ID_PLAN_SFPA = " + idPreReg;
		log.info(query);
		return query;
	}

	public String queryCatPromotores() {
		String query= "SELECT sp.ID_PROMOTOR AS idPromotor, sp.NUM_EMPLEDO AS numEmpleado "
				+ ", CONCAT_WS(' ' ,sp.NOM_PROMOTOR, sp.NOM_PAPELLIDO, sp.NOM_SAPELLIDO) AS nombrePromotor "
				+ " FROM SVT_PROMOTOR sp WHERE sp.IND_ACTIVO = 1";
		log.info(query);
		return query;
	}
}
