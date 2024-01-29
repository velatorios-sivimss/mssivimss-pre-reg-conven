package com.imss.sivimss.arquetipo.configuration.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imss.sivimss.arquetipo.model.entity.BenefXPA;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXPersona;
import com.imss.sivimss.arquetipo.model.entity.PersonaEntityMyBatis;
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
				+ "WHERE stb.ID_TITULAR_BENEFICIARIOS =  #{idConvenioPf} " )
	public BenefXPA consultaBeneficiariosConvenioPA( @Param("idConvenioPf") Integer idConvenioPf );
}
