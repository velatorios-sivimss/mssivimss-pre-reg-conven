package com.imss.sivimss.arquetipo.configuration.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imss.sivimss.arquetipo.model.entity.BenefXPA;
import com.imss.sivimss.arquetipo.model.entity.ContratanteRfcCurp;
import com.imss.sivimss.arquetipo.model.entity.DatosPersonaConvenio;
import com.imss.sivimss.arquetipo.model.entity.PreRegistrosXPA;
import com.imss.sivimss.arquetipo.model.request.PlanPAData;
import com.imss.sivimss.arquetipo.model.request.PlanPASustituto;

/*
 * Este es un ejemplo de cómo se pueden implementar querys a través de interfaces con MyBatis.
 * */


public interface ConvenioPA {

	@Select("SELECT " + 
			"    COUNT(SP.CVE_RFC) AS rfc " + 
			"     " + 
			"FROM " + 
			"    SVT_PLAN_SFPA SPS " + 
			"JOIN SVC_CONTRATANTE sc 	ON sc.ID_CONTRATANTE = SPS.ID_TITULAR " + 
			"JOIN SVC_PERSONA SP 		ON SP.ID_PERSONA = sc.ID_PERSONA " + 
			"WHERE " + 
			"    SPS.ID_PLAN_SFPA != #{idConvenioPf} " + 
			"AND " + 
			"	SP.CVE_RFC LIKE #{identificacion};   ")
	public Integer consultaRfcRepetido( @Param("idConvenioPf") Integer idConvenioPf,@Param("identificacion") String identificacion );

	@Select("SELECT " + 
			"    COUNT(SP.CVE_CURP) AS curp " + 
			"     " + 
			"FROM " + 
			"    SVT_PLAN_SFPA SPS " + 
			"JOIN SVC_CONTRATANTE sc 	ON sc.ID_CONTRATANTE = SPS.ID_TITULAR " + 
			"JOIN SVC_PERSONA SP 		ON SP.ID_PERSONA = sc.ID_PERSONA " + 
			"WHERE " + 
			"    SPS.ID_PLAN_SFPA != #{idConvenioPf} " + 
			"AND  " + 
			"	SP.CVE_CURP LIKE #{identificacion};    ")
	public Integer consultaCurpRepetido( @Param("idConvenioPf") Integer idConvenioPf,@Param("identificacion") String identificacion );

		@Select(" "
		+ "SELECT "
		+ "SP.ID_PERSONA AS idPersona, "
		+ "		sc.ID_CONTRATANTE AS idContratante, "
		+ "		SD.ID_DOMICILIO AS idDomicilio, "
		+ "		SPS.NUM_FOLIO_PLAN_SFPA AS folioConvenio, "
		+ "		SP.CVE_CURP AS curp, "
		+ "		SP.CVE_RFC AS rfc, "
		+ "		IFNULL(sc.CVE_MATRICULA, '') AS matricula, "
		+ "		IFNULL(SP.CVE_NSS,'')  AS nss, "
		+ "		SP.NOM_PERSONA AS nombre, "
		+ "		SP.NOM_PRIMER_APELLIDO AS primerApellido, "
		+ "		SP.NOM_SEGUNDO_APELLIDO AS segundoApellido, "
		+ "		SP.NUM_SEXO AS idSexo, "
		+ " 	CASE SP.NUM_SEXO WHEN 1 THEN 'Mujer' WHEN 2 THEN 'Hombre'  ELSE 'Otro' END AS sexo, "
		+ "		SP.REF_OTRO_SEXO otroSexo, "
		+ "		DATE_FORMAT(SP.FEC_NAC, '%d/%m/%Y') AS fecNacimiento, "
		+ "		SP2.DES_PAIS AS pais, "
		+ "		SP.ID_PAIS AS idPais, "
		+ "		IFNULL(se.DES_ESTADO,'') AS lugarNac, "
		+ "		IFNULL(SP.ID_ESTADO,'') AS idLugarNac, "
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
		+ "		IFNULL(SPS.ID_TITULAR_SUBSTITUTO,0) AS idTitularSust, "
		+ "		IFNULL(SPS.ID_BENEFICIARIO_1,0) AS beneficiario1, "
		+ "		IFNULL(SPS.ID_BENEFICIARIO_2,0) AS beneficiario2, "
		+ "		SPS.IND_PROMOTOR AS gestionPromotor , SPS.IND_ACTIVO activo, "
		+ "		SP.ID_PERSONA idPersonaContratante, SD.ID_DOMICILIO idDomicilio "
		+ "	FROM "
		+ "		SVT_PLAN_SFPA SPS "
		+ "	JOIN SVC_CONTRATANTE sc ON "
		+ "		sc.ID_CONTRATANTE = SPS.ID_TITULAR "
		+ "	JOIN SVC_PERSONA SP ON "
		+ "		SP.ID_PERSONA = sc.ID_PERSONA "
		+ "	LEFT JOIN SVC_PAIS SP2 ON "
		+ "		SP2.ID_PAIS = SP.ID_PAIS "
		+ "	LEFT JOIN SVC_ESTADO se ON "
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
				+ " 	IFNULL(SP.CVE_RFC ,'') AS rfc, "
				+ " 	stb.CVE_MATRICULA AS matricula, "
				+ " 	IFNULL(SP.CVE_NSS ,'') AS nss, "
				+ " 	SP.NOM_PERSONA AS nombre, "
				+ " 	SP.NOM_PRIMER_APELLIDO AS primerApellido, "
				+ " 	SP.NOM_SEGUNDO_APELLIDO AS segundoApellido, "
				+ " 	SP.NUM_SEXO AS idSexo, "
				+ " 	CASE SP.NUM_SEXO WHEN 1 THEN 'Mujer' WHEN 2 THEN 'Hombre'  ELSE 'Otro' END AS sexo, "
				+ "		SP.REF_OTRO_SEXO otroSexo, "
				+ " 	DATE_FORMAT(SP.FEC_NAC, '%d/%m/%Y') AS fecNacimiento, "
				+ " 	SP.ID_PAIS AS idPais, "
				+ "		SP2.DES_PAIS AS pais, "
				+ " 	se.ID_ESTADO AS idLugarNac, "
				+ " 	IFNULL(se.DES_ESTADO,'') AS lugarNac, "
				+ " 	IFNULL(SP.REF_TELEFONO_FIJO,'') AS telFijo, "
				+ " 	IFNULL(SP.REF_TELEFONO,'') AS telCelular, "
				+ " 	IFNULL(SP.REF_CORREO,'') AS correo, "
				+ " 	IFNULL(SD.REF_CALLE,'') AS calle, "
				+ " 	IFNULL(SD.NUM_EXTERIOR,'')  AS numExt, "
				+ " 	IFNULL(SD.NUM_INTERIOR,'')  AS numInt, "
				+ " 	IFNULL(SD.REF_CP,'')  AS cp, "
				+ " 	IFNULL(SD.REF_COLONIA,'')  AS colonia, "
				+ " 	IFNULL(SD.REF_MUNICIPIO,'')  AS municipio, "
				+ " 	IFNULL(SP.ID_ESTADO,0) AS idEstado, "
				+ " 	IFNULL(SD.REF_ESTADO,'') AS estado, SP.ID_PERSONA idPersonaTitular, SD.ID_DOMICILIO idDomicilio"
				+ " FROM "
				+ " 	SVT_TITULAR_BENEFICIARIOS stb "
				+ " JOIN SVC_PERSONA SP ON "
				+ " 	SP.ID_PERSONA = stb.ID_PERSONA "
				+ "	JOIN SVC_PAIS SP2 ON "
				+ "		SP2.ID_PAIS = SP.ID_PAIS "
				+ " LEFT JOIN SVC_ESTADO se ON "
				+ " 	se.ID_ESTADO = SP.ID_ESTADO "
				+ " LEFT JOIN SVT_DOMICILIO SD ON "
				+ " 	SD.ID_DOMICILIO = stb.ID_DOMICILIO "
				+ "WHERE stb.ID_TITULAR_BENEFICIARIOS =  #{idBeneficiario} " )
	public BenefXPA consultaBeneficiariosConvenioPA( @Param("idBeneficiario") Long idBeneficiario );

@Select("SELECT  " +
		"		TIT.ID_TITULAR_BENEFICIARIOS idBeneficiario,  " +
		"		TIT.REF_PERSONA referencia,  " +
		"		TIT.CVE_MATRICULA AS matricula,  " +
		"		 " +
		"		PER.ID_PERSONA idPersonaTitular, " +
		"		PER.CVE_CURP AS curp,  " +
		"		PER.CVE_RFC AS rfc,  " +
		"		PER.CVE_NSS AS nss,  " +
		"		PER.NOM_PERSONA AS nombre,  " +
		"		PER.NOM_PRIMER_APELLIDO AS primerApellido,  " +
		"		PER.NOM_SEGUNDO_APELLIDO AS segundoApellido,  " +
		"		PER.NUM_SEXO AS idSexo,  " +
		"		DATE_FORMAT(PER.FEC_NAC, '%d/%m/%Y') AS fecNacimiento,  " +
		"		PER.ID_PAIS AS idPais,  " +
		"		PER.REF_TELEFONO_FIJO AS telFijo,  " +
		"		PER.REF_TELEFONO AS telCelular,  " +
		"		PER.REF_CORREO AS correo,  " +
		//"		PER.CVE_CURP AS cp,  " +
		"		PER.ID_ESTADO AS idEstado,  " +
		"		PER.REF_OTRO_SEXO otroSexo,  " +
		"				 " +
		"		PAI.DES_PAIS AS pais,  " +
		"		 " +
		"		EST.ID_ESTADO AS idLugarNac,  " +
		"		EST.DES_ESTADO AS lugarNac,  " +
		"		 " +
		"		DOM.ID_DOMICILIO idDomicilio, " +
		"		DOM.REF_CALLE AS calle,  " +
		"		DOM.NUM_EXTERIOR AS numExt,  " +
		"		DOM.NUM_INTERIOR AS numInt,  " +
		"		DOM.REF_COLONIA AS colonia,  " +
		"		DOM.REF_MUNICIPIO AS municipio,  " +
		"		DOM.REF_ESTADO AS estado, DOM.REF_CP cp   " +
		"		 " +
		"FROM	SVT_TITULAR_BENEFICIARIOS TIT  " +
		"INNER	JOIN SVC_PERSONA PER ON PER.ID_PERSONA = TIT.ID_PERSONA  " +
		"INNER	JOIN SVC_PAIS PAI ON PAI.ID_PAIS = PER.ID_PAIS  " +
		"INNER	JOIN SVC_ESTADO EST ON EST.ID_ESTADO = PER.ID_ESTADO  " +
		"INNER	JOIN SVT_DOMICILIO DOM ON DOM.ID_DOMICILIO = TIT.ID_DOMICILIO   " +
		"WHERE	TIT.ID_TITULAR_BENEFICIARIOS = #{idTitularSus}")
	public BenefXPA consultaTitularSust( @Param("idTitularSus") Long idTitularSus );

	@Update(
		"UPDATE\r\n" + //
						"\tSVT_PLAN_SFPA\r\n" + //
						"SET\t\r\n" + //
						"\tID_TIPO_PAGO_MENSUAL    = #{plan.idPagonMensual},\r\n" + //
						"    ID_PAQUETE              = #{plan.idPaquete},\r\n" + //
						"    \r\n" + //
						"    ID_TITULAR_SUBSTITUTO   = #{plan.idTitularSust},\r\n" + //
						"    IND_TITULAR_SUBSTITUTO  = #{plan.indTitularSut}\r\n" + //
						"WHERE\r\n" + //
						"\tID_PLAN_SFPA = #{plan.idConvenio}"
		)	
	public int actualizarDatosPlan (@Param("plan") PlanPAData empresa);

	@Update(
		"UPDATE\r\n" + //
						"\tSVC_PERSONA\r\n" + //
						"SET\r\n" + //
						"\t\r\n" + //
						"    CVE_CURP                = #{plan.curp},\r\n" + //
						"    CVE_RFC                 = #{plan.rfc},\r\n" + //
						"    CVE_NSS                 = #{plan.nss},\r\n" + //
						"    NOM_PERSONA             = #{plan.nombre},\r\n" + //
						"    NOM_PRIMER_APELLIDO     = #{plan.primApellido},\r\n" + //
						"    NOM_SEGUNDO_APELLIDO    = #{plan.segApellido},\r\n" + //
						"    NUM_SEXO                = #{plan.numSex},\r\n" + //
						"    REF_OTRO_SEXO           = #{plan.oreoSex},\r\n" + //
						"    FEC_NAC                 = #{plan.fecNac},\r\n" + //
						"    ID_PAIS                 = #{plan.idPais},\r\n" + //
						"    ID_ESTADO               = #{plan.idEstado},\r\n" + //
						"    REF_TELEFONO            = #{plan.telefono},\r\n" + //
						"    REF_TELEFONO_FIJO       = #{plan.telefonoFij},\r\n" + //
						"    REF_CORREO              = #{plan.correo}\r\n" + //
						"WHeRE\t\r\n" + //
						"\tID_PERSONA = #{plan.idPersonaContratante}\r\n" + //
						"    ;    "
		)	
	public int actualizarDatosContratante (@Param("plan") PlanPAData empresa);

	@Update(
		"UPDATE\r\n" + //
						"\tSVT_DOMICILIO\r\n" + //
						"SET\r\n" + //
						"\t\r\n" + //
						"    REF_CALLE    = #{plan.calle},\r\n" + //
						"    NUM_EXTERIOR = #{plan.numExt},\r\n" + //
						"    NUM_INTERIOR = #{plan.numInt},\r\n" + //
						"    REF_CP       = #{plan.cp},\r\n" + //
						"    REF_COLONIA  = #{plan.colonia},\r\n" + //
						"    REF_MUNICIPIO= #{plan.municipio},\r\n" + //
						"    REF_ESTADO   = #{plan.estado}\r\n" + //
						"WHERE\t\r\n" + //
						"\tID_DOMICILIO = #{plan.idDomicilio}"
		)	
	public int actualizarDomicilioContratante (@Param("plan") PlanPAData empresa);

	@Update(
		"UPDATE\r\n" + //
						"\tSVC_PERSONA\r\n" + //
						"SET\r\n" + //
						"\t\r\n" + //
						"    CVE_CURP            = #{titSust.curp}, \r\n" + //
						"    CVE_RFC             = #{titSust.rfc}, \r\n" + //
						"    CVE_NSS             = #{titSust.nss}, \r\n" + //
						"    NOM_PERSONA         = #{titSust.nombre}, \r\n" + //
						"    NOM_PRIMER_APELLIDO = #{titSust.primerApe}, \r\n" + //
						"    NOM_SEGUNDO_APELLIDO= #{titSust.segunApe}, \r\n" + //
						"    NUM_SEXO            = #{titSust.sexo}, \r\n" + //
						"    FEC_NAC             = #{titSust.fechaNac}, \r\n" + //
						"    ID_PAIS             = #{titSust.idPais}, \r\n" + //
						"    REF_TELEFONO_FIJO   = #{titSust.telefonoFijo}, \r\n" + //
						"    REF_TELEFONO        = #{titSust.telefono}, \r\n" + //
						"    REF_CORREO          = #{titSust.correo}, \r\n" + //
						"    ID_ESTADO           = #{titSust.idEstado}, \r\n" + //
						"    REF_OTRO_SEXO       = #{titSust.otroSexo}\r\n" + //
						"    \r\n" + //
						"WHERE\t\r\n" + //
						"\tID_PERSONA = #{titSust.idPersonaTitular}   "
		)	
	public int actualizarDatosSustituto (@Param("titSust") PlanPASustituto empresa);

	@Update(
		"UPDATE \r\n" + //
						"\tSVT_DOMICILIO \r\n" + //
						"SET \r\n" + //
						" \r\n" + //
						"\tREF_CALLE \t\t= #{titSust.calle}, \r\n" + //
						"\tNUM_EXTERIOR \t= #{titSust.numExt}, \r\n" + //
						"\tNUM_INTERIOR \t= #{titSust.numInt}, \r\n" + //
						"\tREF_COLONIA \t= #{titSust.colonia}, \r\n" + //
						"\tREF_MUNICIPIO \t= #{titSust.municipio}, \r\n" + //
						"\tREF_ESTADO \t\t= #{titSust.estado} \r\n" + //
						" \r\n" + //
						"WHERE \r\n" + //
						"\tID_DOMICILIO = #{titSust.idDomicilio} ;"
		)	
	public int actualizarDomicilioSustituto (@Param("titSust") PlanPASustituto empresa);

	@Update("UPDATE SVT_PLAN_SFPA SET ID_ESTATUS_PLAN_SFPA  = 1 , " +
			"ID_USUARIO_MODIFICA= #{idUsuario}, " +
			"FEC_ACTUALIZACION = CURRENT_DATE() " +
			"WHERE ID_PLAN_SFPA  = #{idConvenio} ")
	public int actualizarEstatusConvenio(@Param("idUsuario") Integer idUsuario,@Param("idConvenio") Integer idConvenio );
}
