package com.imss.sivimss.arquetipo.configuration.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresa;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresaBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresaSolicitantes;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXPersona;

/*
 * Este es un ejemplo de cómo se pueden implementar querys a través de interfaces con MyBatis.
 * */


public interface ConvenioPF {

	
	@Select(" "
	+"	SELECT	"
	+"		/* OBTENER EL DETALLE DEL CONVENIO PF */	"
	+"		SP.ID_PERSONA idPersona,	"
	+"		SC.ID_CONTRATANTE AS idContratante,	"
	+"		SD.ID_DOMICILIO idDOmicilio,	"
	+"		SCP.DES_FOLIO folioConvenio,	"
	+"		SP.CVE_CURP curp,	"
	+"		sp.CVE_RFC rfc, 	"
	+"		IFNULL(sc.CVE_MATRICULA, '') AS matricula,	"
	+"		SP.CVE_NSS AS nss,	"
	+"		SP.NOM_PERSONA AS nombre,	"
	+"		SP.NOM_PRIMER_APELLIDO AS primerApellido,	"
	+"		SP.NOM_SEGUNDO_APELLIDO AS segundoApellido,	"
	+"		CASE SP.NUM_SEXO WHEN 1 THEN 'FEMENINO' WHEN 2 THEN 'MASCULINO' ELSE IFNULL(SP.REF_OTRO_SEXO, '') END AS sexo,	"
	+"		DATE_FORMAT(SP.FEC_NAC, '%d-%m-%Y') AS fecNacimiento,	"
	+"		PAI.DES_PAIS PAIS,	"
	+"		SP.ID_PAIS AS idPais,	"
	+"		se.DES_ESTADO AS lugarNac,	"
	+"		SP.ID_ESTADO AS idLugarNac,	"
	+"		SP.REF_TELEFONO AS telCelular,	"
	+"		SP.REF_TELEFONO_FIJO AS telFijo,	"
	+"		SP.REF_CORREO AS correo,	"
	+"		SD.REF_CALLE AS calle,	"
	+"		SD.NUM_EXTERIOR AS numExt, 	"
	+"		SD.NUM_INTERIOR AS numInt,	"
	+"		SD.REF_CP AS cp,	"
	+"		SD.REF_COLONIA AS colonia,	"
	+"		SD.REF_MUNICIPIO AS municipio, 	"
	+"		SD.REF_ESTADO AS estado,	"
	+"		SCPA.ID_PAQUETE idPaquete,	"
	+"		'nomPaquete' nomPaquete,	"
	+"		SCP.ID_PROMOTOR gestionPromotor	"
	+"			"
	+"	FROM	"
	+"		SVT_CONVENIO_PF SCP	"
	+"	INNER JOIN SVC_ESTATUS_CONVENIO_PF SECP 	ON SCP.ID_ESTATUS_CONVENIO = SECP.ID_ESTATUS_CONVENIO_PF	"
	+"	INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF SCPA 	ON SCP.ID_CONVENIO_PF = SCPA.ID_CONVENIO_PF	"
	+"	INNER JOIN SVC_CONTRATANTE SC 				ON SCPA.ID_CONTRATANTE = SC.ID_CONTRATANTE	"
	+"	INNER JOIN SVT_DOMICILIO SD 				ON SC.ID_DOMICILIO = SD.ID_DOMICILIO	"
	+"	INNER JOIN SVC_PERSONA SP 					ON SC.ID_PERSONA = SP.ID_PERSONA	"
	+"	INNER JOIN SVC_VELATORIO V 					ON V.ID_VELATORIO = SCP.ID_VELATORIO	"
	+"	LEFT JOIN SVC_PAIS PAI 						ON    PAI.ID_PAIS = SP.ID_PAIS	"
	+"	LEFT JOIN SVC_ESTADO se 						ON se.ID_ESTADO = SP.ID_ESTADO 	"
	+"	"
	+"	WHERE	"
	+"		SCP.ID_CONVENIO_PF = #{idConvenioPf}")
	public DetalleConvenioPFXPersona consultaDetalleConvenioXPersona( @Param("idConvenioPf") Integer idConvenioPf );
	
	/* 
	@Select(" "
			+ "SELECT "
			+ "	SC.ID_PERSONA idPersona, "
			+ "    SC.ID_CONTRATANTE idContratante,  "
			+ "    SD.ID_DOMICILIO idDomicilio, "
			+ "    SCP.DES_FOLIO folioConvenio, "
			+ "    IFNULL(SPE.CVE_CURP, '') AS curp, "
			+ "    IFNULL(SPE.CVE_RFC, '') AS rfc, "
			+ "	IFNULL(SC.CVE_MATRICULA, '') AS matricula, "
			+ "    SPE.CVE_NSS nss, "
			+ "    SPE.NOM_PERSONA AS nombre, "
			+ "    SPE.NOM_PRIMER_APELLIDO AS primerApellido, "
			+ "    SPE.NOM_SEGUNDO_APELLIDO AS segundoApellido, "
			+ "    CASE SPE.NUM_SEXO WHEN 1 THEN 'FEMENINO' WHEN 2 THEN 'MASCULINO' ELSE IFNULL(SPE.REF_OTRO_SEXO, '') END AS sexo, "
			+ "    DATE_FORMAT(SPE.FEC_NAC, '%d-%m-%Y') AS fecNacimiento, "
			+ "    PAI.DES_PAIS pais, "
			+ "    PAI.ID_PAIS idPAis, "
			+ "    se.DES_ESTADO AS lugarNac, "
			+ "    se.ID_ESTADO idLugarNac, "
			+ "    SPE.REF_TELEFONO telCelular, "
			+ "    SPE.REF_TELEFONO_FIJO telFIjo, "
			+ "    SPE.REF_CORREO correo, "
			+ "    SD.REF_CALLE calle, "
			+ "    SD.NUM_INTERIOR numInt, "
			+ "    SD.NUM_EXTERIOR numExt, "
			+ "    SD.REF_CP cp, "
			+ "    SD.REF_COLONIA colonia, "
			+ "    SD.REF_MUNICIPIO municipio,  "
			+ "    se.DES_ESTADO estado, "
			+ "    SCPCP.ID_PAQUETE, "
			+ "    'nomPaquete' nomPaquete, "
			+ "    scp.ID_PROMOTOR gestionPromotor "
			+ "FROM "
			+ "    SVT_CONVENIO_PF SCP "
			+ "INNER JOIN SVT_EMPRESA_CONVENIO_PF SCPE 	ON SCP.ID_CONVENIO_PF = SCPE.ID_CONVENIO_PF "
			+ "INNER JOIN SVT_DOMICILIO SD 				ON SCPE.ID_DOMICILIO = SD.ID_DOMICILIO "
			+ "INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF SCPCP ON SCPCP.ID_CONVENIO_PF = SCP.ID_CONVENIO_PF "
			+ "INNER JOIN SVC_CONTRATANTE SC 				ON SC.ID_CONTRATANTE = SCPCP.ID_CONTRATANTE "
			+ "INNER JOIN SVC_PERSONA SPE 					ON SC.ID_PERSONA = SPE.ID_PERSONA "
			+ "LEFT JOIN SVC_PAIS PAI 						ON    PAI.ID_PAIS = SPE.ID_PAIS	 "
			+ "LEFT JOIN SVC_ESTADO se 					ON se.ID_ESTADO = SPE.ID_ESTADO "
			+ "WHERE SCPE.ID_CONVENIO_PF = #{idConvenioPf} ")
		public DetalleConvenioPFXPersona consultaDetalleConvenioXEmpresa( @Param("idConvenioPf") Integer idConvenioPf );
		*/
		
		@Select(" "
		+ "SELECT "
		+ "	EMP.REF_NOMBRE AS nombre, "
		+ "			EMP.REF_RAZON_SOCIAL AS razonSocial, "
		+ "			EMP.CVE_RFC AS rfc, "
		+ "			EMP.ID_PAIS AS idPais, "
		+ "			DOM.REF_CP AS cp, "
		+ "			DOM.REF_COLONIA AS colonia, "
		+ "			DOM.REF_ESTADO AS estado, "
		+ "			DOM.REF_MUNICIPIO AS municipio, "
		+ "			DOM.REF_CALLE AS calle, "
		+ "			DOM.NUM_INTERIOR AS numInterior, "
		+ "			DOM.NUM_EXTERIOR AS numExterior, "
		+ "			EMP.REF_TELEFONO AS telefono, "
		+ "			EMP.REF_CORREO AS correo, "
		+ "			EMP.ID_CONVENIO_PF AS idConvenio, "
		+ "			EMP.ID_EMPRESA_CONVENIO_PF AS idEmpresa, "
		+ "			PF.ID_PROMOTOR AS idPromotor "
		+ "FROM "
		+ "			SVT_CONVENIO_PF PF "
		+ "INNER JOIN SVT_EMPRESA_CONVENIO_PF EMP ON "
		+ "			PF.ID_CONVENIO_PF = EMP.ID_CONVENIO_PF "
		+ "INNER JOIN SVT_DOMICILIO DOM ON "
		+ "			EMP.ID_DOMICILIO = DOM.ID_DOMICILIO "
		+ "WHERE "
		+ "			EMP.ID_CONVENIO_PF = #{idConvenioPf} ")
		public DetalleConvenioPFXEmpresa consultaDetalleConvenioXEmpresa( @Param("idConvenioPf") Integer idConvenioPf );

		@Select(" SELECT  " +  
				"  " +  
				"     " +  
				"    PAQ.ID_CONTRA_PAQ_CONVENIO_PF idPaquete, " +  
				"     " +  
				"    PER.CVE_RFC rfc, " +  
				"    PER.CVE_CURP curp, " +  
				"    PER.NOM_PERSONA nombre, " +  
				"    PER.NOM_PRIMER_APELLIDO primerApellido, " +  
				"    PER.NOM_SEGUNDO_APELLIDO segundoApellido, " +  
				"    DOM.REF_CALLE calle, " +  
				"    DOM.NUM_EXTERIOR numExterior, " +  
				"    DOM.NUM_INTERIOR numInterior, " +  
				"    DOM.REF_CP cp, " +  
				"    DOM.REF_COLONIA colonia, " +  
				"    DOM.REF_MUNICIPIO, " +  
				"    DOM.REF_ESTADO estado, " +  
				"    PAI.DES_PAIS pais, " +  
				"    PAI.ID_PAIS idPais, " +  
				"    ES.DES_ESTADO lugarNac, " +  
				"    ES.ID_ESTADO idLugarNac, " +  
				"    PER.REF_TELEFONO telefono, " +  
				"    PER.REF_CORREO correo " +  
				" " +  
				"FROM " +  
				"    SVT_CONVENIO_PF PF " +  
				"INNER JOIN SVT_EMPRESA_CONVENIO_PF EMP ON  PF.ID_CONVENIO_PF = EMP.ID_CONVENIO_PF  " +  
				"INNER JOIN svt_contra_paq_convenio_pf PAQ ON PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF " +  
				"INNER JOIN SVC_CONTRATANTE CON ON CON.ID_CONTRATANTE = PAQ.ID_CONTRATANTE " +  
				"INNER JOIN SVC_PERSONA PER ON PER.ID_PERSONA = CON.ID_PERSONA  " +  //-- persona contratante
				"INNER JOIN SVT_DOMICILIO DOM ON DOM.ID_DOMICILIO = CON.ID_DOMICILIO  " +  //-- domicilio del contratante
				"INNER JOIN SVC_PAIS PAI ON PAI.ID_PAIS = PER.ID_PAIS " +  
				"INNER JOIN svc_estado ES ON ES.ID_ESTADO = PER.ID_ESTADO " +  
				" " +  
				"WHERE PF.ID_CONVENIO_PF = #{idConvenioPf} and CON.IND_ACTIVO = 1")
		public ArrayList<DetalleConvenioPFXEmpresaSolicitantes> 
		consultaDetalleConvenioXEmpresaSolicitantes( @Param("idConvenioPf") Integer idConvenioPf );

		@Select("SELECT  " +  
				"  " +  
				"    CONCAT(PER.NOM_PERSONA,' ',PER.NOM_PRIMER_APELLIDO,' ',PER.NOM_SEGUNDO_APELLIDO) nombre, " +  
				"    TIMESTAMPDIFF(YEAR, PER.FEC_NAC, CURDATE()) AS edad, " +  
				"    PAR.DES_PARENTESCO, " +  
				"    PER.CVE_CURP curp, " +  
				"    PER.CVE_RFC rfc, " +  
				"    PER.REF_CORREO correo, " +  
				"    PER.REF_TELEFONO telefono, " +  
				"    PAQ.ID_CONTRATANTE idContratante " +  
				" " +  
				" " +  
				"FROM " +  
				"    SVT_CONVENIO_PF PF " +  
				"INNER JOIN svt_contra_paq_convenio_pf PAQ ON PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF " +  
				"INNER JOIN svt_contratante_beneficiarios BEN ON BEN.ID_CONTRA_PAQ_CONVENIO_PF =PAQ.ID_CONTRA_PAQ_CONVENIO_PF " +  
				"INNER JOIN svc_parentesco PAR ON PAR.ID_PARENTESCO = BEN.ID_PARENTESCO " +  
				"INNER JOIN SVC_PERSONA PER ON PER.ID_PERSONA = BEN.ID_PERSONA  " +  // -- Persona BENEFICIARIO
				"WHERE BEN.IND_ACTIVO = 1  " +  
				"AND  BEN.ID_CONTRA_PAQ_CONVENIO_PF IN " +  
				"( " +  
				"    SELECT  " +  
				"        PAQ.ID_CONTRA_PAQ_CONVENIO_PF idPaquete " +  
				"    FROM " +  
				"        SVT_CONVENIO_PF PF " +  
				"    INNER JOIN SVT_EMPRESA_CONVENIO_PF EMP ON  PF.ID_CONVENIO_PF = EMP.ID_CONVENIO_PF  " +  
				"    INNER JOIN svt_contra_paq_convenio_pf PAQ ON PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF " +  
				"    INNER JOIN SVC_CONTRATANTE CON ON CON.ID_CONTRATANTE = PAQ.ID_CONTRATANTE " +  
				"    INNER JOIN SVC_PERSONA PER ON PER.ID_PERSONA = CON.ID_PERSONA  " +  // -- persona contratante
				"    INNER JOIN SVT_DOMICILIO DOM ON DOM.ID_DOMICILIO = CON.ID_DOMICILIO  " +  // -- domicilio del contratante
				"    INNER JOIN SVC_PAIS PAI ON PAI.ID_PAIS = PER.ID_PAIS " +  
				"    INNER JOIN svc_estado ES ON ES.ID_ESTADO = PER.ID_ESTADO " +  
				"    WHERE PF.ID_CONVENIO_PF = #{idConvenioPf} and CON.IND_ACTIVO = 1 )")
		public ArrayList<DetalleConvenioPFXEmpresaBeneficiarios> 
		consultaDetalleConvenioXEmpresaBeneficiarios( @Param("idConvenioPf") Integer idConvenioPf );
}
