package com.imss.sivimss.arquetipo.configuration.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.imss.sivimss.arquetipo.model.entity.BenefXPA;
import com.imss.sivimss.arquetipo.model.entity.PreRegistrosXPA;

/*
 * Este es un ejemplo de cómo se pueden implementar querys a través de interfaces con MyBatis.
 * */


public interface ConvenioPA {

		@Select(" "
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
		+ " 	CASE SP.NUM_SEXO WHEN 1 THEN 'Mujer' WHEN 2 THEN 'Hombre'  ELSE 'Otro' END AS sexo, "
		+ "		DATE_FORMAT(SP.FEC_NAC, '%d/%m/%Y') AS fecNacimiento, "
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
		+ "		SPS.IND_PROMOTOR AS gestionPromotor , SPS.IND_ACTIVO activo"
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
				+ " WHERE SPS.ID_PLAN_SFPA = #{idConvenioPf} ")
	public PreRegistrosXPA consultaDetalleConvenioPA( @Param("idConvenioPf") Integer idConvenioPf );

	@Select(" "
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
				+ " 	CASE SP.NUM_SEXO WHEN 1 THEN 'Mujer' WHEN 2 THEN 'Hombre'  ELSE 'Otro' END AS sexo, "
				+ " 	DATE_FORMAT(SP.FEC_NAC, '%d/%m/%Y') AS fecNacimiento, "
				+ " 	SP.ID_PAIS AS idPais, "
				+ "		SP2.DES_PAIS AS pais, "
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
				+ "	JOIN SVC_PAIS SP2 ON "
				+ "		SP2.ID_PAIS = SP.ID_PAIS "
				+ " JOIN SVC_ESTADO se ON "
				+ " 	se.ID_ESTADO = SP.ID_ESTADO "
				+ " JOIN SVT_DOMICILIO SD ON "
				+ " 	SD.ID_DOMICILIO = stb.ID_DOMICILIO "
				+ "WHERE stb.ID_TITULAR_BENEFICIARIOS =  #{idBeneficiario} " )
	public BenefXPA consultaBeneficiariosConvenioPA( @Param("idBeneficiario") Integer idBeneficiario );

@Select("SELECT TIT.ID_TITULAR_BENEFICIARIOS idBeneficiario, " + 
		"		TIT.REF_PERSONA referencia, " + 
		"		PER.CVE_CURP AS curp, " + 
		"		PER.CVE_RFC AS rfc, " + 
		"		TIT.CVE_MATRICULA AS matricula, " + 
		"		PER.CVE_NSS AS nss, " + 
		"		PER.NOM_PERSONA AS nombre, " + 
		"		PER.NOM_PRIMER_APELLIDO AS primerApellido, " + 
		"		PER.NOM_SEGUNDO_APELLIDO AS segundoApellido, " + 
		"		PER.NUM_SEXO AS idSexo, " + 
		" 		CASE PER.NUM_SEXO WHEN 1 THEN 'Mujer' WHEN 2 THEN 'Hombre'  ELSE 'Otro' END AS sexo, " +
		"		DATE_FORMAT(PER.FEC_NAC, '%d/%m/%Y') AS fecNacimiento, " + 
		"		PER.ID_PAIS AS idPais, " + 
		"		SP2.DES_PAIS AS pais, " +
		"		se.ID_ESTADO AS lugarNac, " + 
		"		PER.REF_TELEFONO_FIJO AS telFijo, " + 
		"		PER.REF_TELEFONO AS telCelular, " + 
		"		PER.REF_CORREO AS correo, " + 
		"		SD.REF_CALLE AS calle, " + 
		"		SD.NUM_EXTERIOR AS numExt, " + 
		"		SD.NUM_INTERIOR AS numInt, " + 
		"		PER.CVE_CURP AS cp, " + 
		"		SD.REF_COLONIA AS colonia, " + 
		"		SD.REF_MUNICIPIO AS municipio, " + 
		"		PER.ID_ESTADO AS idEstado, " + 
		"		SD.REF_ESTADO AS estado  " + 
		"FROM	SVT_TITULAR_BENEFICIARIOS TIT " + 
		"INNER	JOIN SVC_PERSONA PER ON PER.ID_PERSONA = TIT.ID_PERSONA " + 
		"INNER	JOIN SVC_PAIS SP2 ON SP2.ID_PAIS = PER.ID_PAIS " +
		"INNER	JOIN SVC_ESTADO se ON se.ID_ESTADO = PER.ID_ESTADO " + 
		"INNER	JOIN SVT_DOMICILIO SD ON SD.ID_DOMICILIO = TIT.ID_DOMICILIO  " + 
		"WHERE	TIT.ID_TITULAR_BENEFICIARIOS = #{idTitularSus} ")
	public BenefXPA consultaTitularSust( @Param("idTitularSus") Integer idTitularSus );
}
