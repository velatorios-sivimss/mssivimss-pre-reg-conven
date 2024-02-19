package com.imss.sivimss.arquetipo.configuration.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresa;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresaBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresaBeneficiariosDocs;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXEmpresaSolicitantes;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXPersona;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXPersonaBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXPersonaBeneficiariosDocs;
import com.imss.sivimss.arquetipo.model.entity.RegistroPagoPlanPF;

/*
 * Este es un ejemplo de cómo se pueden implementar querys a través de interfaces con MyBatis.
 * */


public interface ConvenioPF {

	@Select("SELECT " + 
			"    COUNT(SP.CVE_RFC) rfc " + 
			"FROM " + 
			"    SVT_CONVENIO_PF SCP " + 
			"INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF SCPA 	ON SCP.ID_CONVENIO_PF = SCPA.ID_CONVENIO_PF " + 
			"INNER JOIN SVC_CONTRATANTE SC 					ON SCPA.ID_CONTRATANTE = SC.ID_CONTRATANTE " + 
			"INNER JOIN SVC_PERSONA SP 						ON SC.ID_PERSONA = SP.ID_PERSONA " + 
			" " + 
			"WHERE " + 
			"    SCP.ID_CONVENIO_PF != #{idConvenioPf} AND SP.CVE_RFC LIKE #{identificacion};    ")
	public Integer consultaRfcRepetido( @Param("idConvenioPf") Integer idConvenioPf,@Param("identificacion") String identificacion );

	@Select("SELECT " + 
			"    COUNT(SP.CVE_CURP) curp " + 
			"FROM " + 
			"    SVT_CONVENIO_PF SCP " + 
			"INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF SCPA 	ON SCP.ID_CONVENIO_PF = SCPA.ID_CONVENIO_PF " + 
			"INNER JOIN SVC_CONTRATANTE SC 					ON SCPA.ID_CONTRATANTE = SC.ID_CONTRATANTE " + 
			"INNER JOIN SVC_PERSONA SP 						ON SC.ID_PERSONA = SP.ID_PERSONA " + 
			" " + 
			"WHERE " + 
			"    SCP.ID_CONVENIO_PF != #{idConvenioPf} AND SP.CVE_CURP LIKE #{identificacion};  ")
	public Integer consultaCurpRepetido( @Param("idConvenioPf") Integer idConvenioPf,@Param("identificacion") String identificacion );


	@Update("UPDATE SVT_CONVENIO_PF SET IND_ACTIVO = !IND_ACTIVO WHERE ID_CONVENIO_PF = #{idConvenioPf}")
	public int activarDesactivarConvenioPF (@Param("idConvenioPf") Integer idConvenioPf);

	@Update("UPDATE SVT_PLAN_SFPA SET IND_ACTIVO = !IND_ACTIVO WHERE ID_PLAN_SFPA = #{idConvenioPf}")
	public int activarDesactivarConvenioPA (@Param("idConvenioPf") Integer idConvenioPf);
	
	@Select("  " +  
			"SELECT " +  
			"    IFNULL(SC.CVE_MATRICULA, '') AS matricula, SP.ID_PERSONA idPersona, SP.ID_ESTADO idEstado,SD.ID_DOMICILIO idDomicilio, " +  
			"    SCP.DES_FOLIO folioConvenio, SCPA.ID_CONTRA_PAQ_CONVENIO_PF idContraPaqPF, " +  
			"    IFNULL(SP.CVE_RFC, '') rfc, " +  
			"    IFNULL(SP.CVE_CURP, '') curp, " +  
			"    IFNULL(SP.NOM_PERSONA, '') AS nombre, " +  
			"    IFNULL(SP.NOM_PRIMER_APELLIDO, '') AS primerApellido, " +  
			"    IFNULL(SP.NOM_SEGUNDO_APELLIDO, '') AS segundoApellido, " +  
			"    IFNULL(SD.REF_CALLE, '') AS calle, " +  
			"    IFNULL(SD.NUM_EXTERIOR, '') AS numExt, " +  
			"    IFNULL(SD.NUM_INTERIOR, '') AS numInt, " +  
			"    IFNULL(SD.REF_CP, '') AS cp, " +  
			"	 IFNULL(SD.REF_COLONIA, '') AS colonia, " +  
			"	 IFNULL(SD.REF_MUNICIPIO, '') AS municipio, " +  
			"	 IFNULL(SD.REF_ESTADO, '') AS estado, " +  
			"    IFNULL(PAI.DES_PAIS, '') PAIS, " +  
			"	 SP.ID_PAIS AS idPais, " +  
			"    IFNULL(se.DES_ESTADO, '') AS lugarNac, " +  
			"	 SP.ID_ESTADO AS idLugarNac, " +  
			"    IFNULL(SP.REF_CORREO, '') AS correo, " +  
			"    IFNULL(SP.REF_TELEFONO, '') AS telCelular, " +  
			"	 SCPA.ID_PAQUETE idPaquete, " +  
			"	 IFNULL(PA.REF_PAQUETE_NOMBRE, '') tipoPaquete, " +  
			"    ENF.IND_ENFERMEDAD_PREXISTENTE enfermedadPre, " +  //
			"    IFNULL(DOC.IND_INE_AFILIADO, 0) AS docIne,  " +  
			"    IFNULL(DOC.IND_CURP, 0) AS docCurp,  " +  
			"    IFNULL(DOC.IND_RFC, 0) AS docRfc,  " +  
			"    SP.FEC_NAC AS fechaNaciemiento,  " +  
			"    SP.NUM_SEXO AS idSexo,  " +  
			"    IFNULL(SP.REF_OTRO_SEXO,'') AS otroSexo,  " +  
			"    IFNULL(SCP.ID_PROMOTOR,'') AS idPromotor,  " +  
			"    SCPA.ID_CONTRATANTE AS idContrantante,  " +  
			"    DOC.ID_VALIDACION_DOCUMENTO AS idValidaDocumento,  " +  
			"    IFNULL(ENF.REF_OTRA_ENFERMEDAD, '') otraEnfermedad, SCP.IND_ACTIVO activo " +  
			"FROM " +  
			"    SVT_CONVENIO_PF SCP " +  
			"INNER JOIN SVC_ESTATUS_CONVENIO_PF SECP 		ON SCP.ID_ESTATUS_CONVENIO = SECP.ID_ESTATUS_CONVENIO_PF " +  
			"INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF SCPA 	ON SCP.ID_CONVENIO_PF = SCPA.ID_CONVENIO_PF " +  
			"INNER JOIN SVT_PAQUETE PA 						ON PA.ID_PAQUETE = SCPA.ID_PAQUETE " +  
			"INNER JOIN SVC_CONTRATANTE SC 					ON SCPA.ID_CONTRATANTE = SC.ID_CONTRATANTE " +  
			"INNER JOIN SVT_DOMICILIO SD 					ON SC.ID_DOMICILIO = SD.ID_DOMICILIO " +  
			"INNER JOIN SVC_PERSONA SP 						ON SC.ID_PERSONA = SP.ID_PERSONA " +  
			"INNER JOIN SVC_VELATORIO V 					ON V.ID_VELATORIO = SCP.ID_VELATORIO " +  
			"LEFT JOIN SVC_PAIS PAI 						ON PAI.ID_PAIS = SP.ID_PAIS " +  
			"LEFT JOIN SVC_ESTADO se 						ON se.ID_ESTADO = SP.ID_ESTADO " +  
			"LEFT JOIN SVT_CONTRA_PAQ_CONVENIO_PF ENF		ON ENF.ID_CONVENIO_PF = SCP.ID_CONVENIO_PF " +  
			"LEFT JOIN SVC_VALIDA_DOCS_CONVENIO_PF DOC	    ON ENF.ID_CONTRA_PAQ_CONVENIO_PF = DOC.ID_CONTRA_PAQ_CONVENIO_PF " +  
			"WHERE SCP.ID_CONVENIO_PF = #{idConvenioPf} "   )
	public DetalleConvenioPFXPersona consultaDetalleConvenioXPersona( @Param("idConvenioPf") Integer idConvenioPf );

	@Select("  SELECT  " +   
			"BEN.ID_CONTRATANTE_BENEFICIARIOS  idBeneficiario, PER.ID_PERSONA idPersona, " +   
			"    IFNULL(PER.NOM_PERSONA, '') AS nombre, " +  
			"    IFNULL(PER.NOM_PRIMER_APELLIDO, '') AS primerApellido, " +  
			"    IFNULL(PER.NOM_SEGUNDO_APELLIDO, '') AS segundoApellido, " +  
			"TIMESTAMPDIFF(YEAR, PER.FEC_NAC, CURDATE()) AS edad,  " +   
			"IFNULL(PAR.DES_PARENTESCO, '') parentesco,  " +   
			"IFNULL(PER.CVE_CURP, '') curp,  " +   
			"IFNULL(PER.CVE_RFC, '') rfc,  " +   
			"IFNULL(PER.REF_CORREO, '') correo,  " +   
			"IFNULL(PER.REF_TELEFONO, '') telefono,  " +   
			"    PER.FEC_NAC AS fechaNaciemiento,  " +  
			"    PER.NUM_SEXO AS idSexo,  " +  
			"    IFNULL(PER.REF_OTRO_SEXO,'') AS otroSexo,  " +  
			"IFNULL(BEN.IND_INE_BENEFICIARIO, 0) docIne,  " +   
			"IFNULL(BEN.IND_ACTA_NACIMIENTO, 0) docActa,  " +   
			"PER.ID_PAIS AS idPais, " +  
			"PER.ID_ESTADO AS idEstado, " +  
			"CASE WHEN BEN.IND_ACTA_NACIMIENTO IS NULL OR 0 THEN IFNULL(BEN.REF_UBICACION_INE_BENEFICIARIO, '') ELSE IFNULL(BEN.REF_UBICACION_INE_BENEFICIARIO, '') END AS nombreArchivo,  " +   
			"IFNULL(BEN.ID_PARENTESCO, '') idParentesco,  " +   
			"PAQ.ID_CONTRATANTE idContratante  " +   
			"  " +   
			"  " +   
			"FROM  " +   
			"SVT_CONVENIO_PF PF  " +   
			"INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF PAQ ON PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF  " +   
			"LEFT JOIN SVT_CONTRATANTE_BENEFICIARIOS BEN ON BEN.ID_CONTRA_PAQ_CONVENIO_PF=PAQ.ID_CONTRA_PAQ_CONVENIO_PF  " +   
			"LEFT JOIN SVC_PARENTESCO PAR ON PAR.ID_PARENTESCO = BEN.ID_PARENTESCO  " +   
			"LEFT JOIN SVC_PERSONA PER ON PER.ID_PERSONA = BEN.ID_PERSONA  " +   
			"WHERE BEN.IND_ACTIVO = 1  " +   
			//"AND  PAR.ID_PARENTESCO IN (5,8,9,10,11,12,17)  " +   
			"AND  BEN.ID_CONTRA_PAQ_CONVENIO_PF IN  " +   
			"(  " +   
			"SELECT  " +   
			"    SCPA.ID_CONTRA_PAQ_CONVENIO_PF idPaquete  " +   
			"FROM  " +   
			"    SVT_CONVENIO_PF SCP  " +   
			"INNER JOIN SVC_ESTATUS_CONVENIO_PF SECP ON  " +   
			"    SCP.ID_ESTATUS_CONVENIO = SECP.ID_ESTATUS_CONVENIO_PF  " +   
			"INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF SCPA ON  " +   
			"    SCP.ID_CONVENIO_PF = SCPA.ID_CONVENIO_PF  " +   
			"INNER JOIN SVT_PAQUETE PA ON  " +   
			"    PA.ID_PAQUETE = SCPA.ID_PAQUETE  " +   
			"INNER JOIN SVC_CONTRATANTE SC ON  " +   
			"    SCPA.ID_CONTRATANTE = SC.ID_CONTRATANTE  " +   
			"INNER JOIN SVT_DOMICILIO SD ON  " +   
			"    SC.ID_DOMICILIO = SD.ID_DOMICILIO  " +   
			"INNER JOIN SVC_PERSONA SP ON  " +   
			"    SC.ID_PERSONA = SP.ID_PERSONA  " +   
			"INNER JOIN SVC_VELATORIO V ON  " +   
			"    V.ID_VELATORIO = SCP.ID_VELATORIO  " +   
			"LEFT JOIN SVC_PAIS PAI ON  " +   
			"    PAI.ID_PAIS = SP.ID_PAIS  " +   
			"LEFT JOIN SVC_ESTADO se ON  " +   
			"    se.ID_ESTADO = SP.ID_ESTADO  " +   
			"LEFT JOIN SVT_CONTRA_PAQ_CONVENIO_PF ENF ON  " +   
			"    ENF.ID_CONVENIO_PF = SCP.ID_CONVENIO_PF  " +   
			"WHERE  " +   
			"    SCP.ID_CONVENIO_PF = #{idConvenioPf}  )   " +   
			"" )
	public ArrayList<DetalleConvenioPFXPersonaBeneficiarios> consultaDetalleConvenioXPersonaBenficiarios( @Param("idConvenioPf") Integer idConvenioPf );
	


	@Select(" "
	+ "SELECT "
	+ "/* Query para obtener los beneficiarios de un paq */ "
	+ "BEN.ID_CONTRATANTE_BENEFICIARIOS idBeneficiario, "
	+ "BEN.REF_UBICACION_INE_BENEFICIARIO refUbicacionIneBeneficiario, "
	+ "BEN.REF_UBICACION_ACTA_NACIMIENTO refUbicacionActaNac, "
	+ "BEN.REF_DOC_ACTA_NACIMIENTO_BENEFICIARIO refDocActaNacBeneficiario, "
	+ "BEN.REF_DOC_INE_BENEFICIARIO refDocIneBeneficiario, "
	+ "BEN.REF_DOCUMENTO_BENEFICIARIO refDocBeneficiario "
	+ "FROM "
	+ "SVT_CONVENIO_PF PF "
	+ "INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF PAQ ON "
	+ "PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF "
	+ "INNER JOIN SVT_CONTRATANTE_BENEFICIARIOS BEN ON "
	+ "BEN.ID_CONTRA_PAQ_CONVENIO_PF = PAQ.ID_CONTRA_PAQ_CONVENIO_PF "
	+ "WHERE "
	+ "BEN.IND_ACTIVO = 1  AND BEN.ID_CONTRA_PAQ_CONVENIO_PF IN( "
		+ "SELECT "
		+ "SCPA.ID_PAQUETE idPaquete "
		+ "FROM "
		+ "SVT_CONVENIO_PF SCP "
		+ "INNER JOIN SVC_ESTATUS_CONVENIO_PF SECP ON "
		+ "SCP.ID_ESTATUS_CONVENIO = SECP.ID_ESTATUS_CONVENIO_PF "
		+ "INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF SCPA ON "
		+ "SCP.ID_CONVENIO_PF = SCPA.ID_CONVENIO_PF "
		+ "INNER JOIN SVT_PAQUETE PA ON "
		+ "PA.ID_PAQUETE = SCPA.ID_PAQUETE "
		+ "INNER JOIN SVC_CONTRATANTE SC ON "
		+ "SCPA.ID_CONTRATANTE = SC.ID_CONTRATANTE "
		+ "INNER JOIN SVT_DOMICILIO SD ON "
		+ "SC.ID_DOMICILIO = SD.ID_DOMICILIO "
		+ "INNER JOIN SVC_PERSONA SP ON "
		+ "SC.ID_PERSONA = SP.ID_PERSONA "
		+ "INNER JOIN SVC_VELATORIO V ON "
		+ "V.ID_VELATORIO = SCP.ID_VELATORIO "
		+ "LEFT JOIN SVC_PAIS PAI ON "
		+ "PAI.ID_PAIS = SP.ID_PAIS "
		+ "LEFT JOIN SVC_ESTADO se ON "
		+ "se.ID_ESTADO = SP.ID_ESTADO "
		+ "LEFT JOIN svt_contra_paq_convenio_pf ENF ON "
		+ "ENF.ID_CONVENIO_PF = SCP.ID_CONVENIO_PF "
		+ "WHERE SCP.ID_CONVENIO_PF > #{idConvenioPf})")
		public ArrayList<DetalleConvenioPFXPersonaBeneficiariosDocs> 
		consultaDetalleConvenioXPersonaBeneficiariosDocs( @Param("idConvenioPf") Integer idConvenioPf );
		
		@Select(" "
		+ "SELECT "
		+ "	IFNULL(EMP.REF_NOMBRE,'')  AS nombre, "
		+ "			IFNULL(EMP.REF_RAZON_SOCIAL,'')  AS razonSocial, "
		+ "			IFNULL(EMP.CVE_RFC,'')  AS rfc, "
		+ "			EMP.ID_PAIS AS idPais, "
		+ "			IFNULL(PAI.DES_PAIS,'')  as pais, "
		+ "			IFNULL(DOM.REF_CP,'')  AS cp, "
		+ "			IFNULL(DOM.REF_COLONIA,'')  AS colonia, "
		+ "			IFNULL(DOM.REF_ESTADO,'')  AS estado, "
		+ "			IFNULL(DOM.REF_MUNICIPIO,'')  AS municipio, "
		+ "			IFNULL(DOM.REF_CALLE,'')  AS calle, "
		+ "			IFNULL(DOM.NUM_INTERIOR,'') AS numInterior, "
		+ "			IFNULL(DOM.NUM_EXTERIOR,'')  AS numExterior, "
		+ "			IFNULL(EMP.REF_TELEFONO,'')  AS telefono, "
		+ "			IFNULL(EMP.REF_CORREO,'')  AS correo, "
		+ "			EMP.ID_CONVENIO_PF AS idConvenio, "
		+ "			EMP.ID_EMPRESA_CONVENIO_PF AS idEmpresa, "
		+ "			EMP.ID_DOMICILIO AS idDomicilio, "
		+ "			PF.ID_PROMOTOR AS idPromotor, "
		+ "			PF.DES_FOLIO folioConvenio, PF.IND_ACTIVO activo "
		+ "FROM "
		+ "			SVT_CONVENIO_PF PF "
		+ "INNER JOIN SVT_EMPRESA_CONVENIO_PF EMP ON "
		+ "			PF.ID_CONVENIO_PF = EMP.ID_CONVENIO_PF "
		+ "INNER JOIN SVT_DOMICILIO DOM ON "
		+ "			EMP.ID_DOMICILIO = DOM.ID_DOMICILIO "
		+ " INNER JOIN SVC_PAIS PAI ON PAI.ID_PAIS = EMP.ID_PAIS "
		+ "WHERE "
		+ "			PF.ID_CONVENIO_PF = #{idConvenioPf} "
		)
		public DetalleConvenioPFXEmpresa consultaDetalleConvenioXEmpresa( @Param("idConvenioPf") Integer idConvenioPf );

		@Select(" SELECT  " +  
				"    PAQ.ID_PAQUETE idPaquete, " +  
				"    IFNULL(CON.CVE_MATRICULA, '') AS matricula, " +  
				"    CON.ID_CONTRATANTE idContratante, " +  
				"    IFNULL(PER.CVE_RFC,'') rfc, " +  
				"    IFNULL(PER.CVE_CURP,'') curp, " +  
				"    IFNULL(PER.NOM_PERSONA,'') nombre, " +  
				"    IFNULL(PER.NOM_PRIMER_APELLIDO,'') primerApellido, " +  
				"    IFNULL(PER.NOM_SEGUNDO_APELLIDO,'') segundoApellido, " +  
				"    IFNULL(DOM.REF_CALLE,'') calle, " +  
				"    IFNULL(DOM.NUM_EXTERIOR,'') numExterior, " +  
				"    IFNULL(DOM.NUM_INTERIOR,'') numInterior, " +  
				"    IFNULL(DOM.REF_CP,'') cp, " +  
				"    IFNULL(DOM.REF_COLONIA,'') colonia, " +  
				"    IFNULL(DOM.REF_MUNICIPIO,'') municipio, " +  
				"    IFNULL(DOM.REF_ESTADO,'') estado, " +  
				"    IFNULL(PAI.DES_PAIS,'') pais, " +  
				"    PAI.ID_PAIS idPais, " +  
				"    IFNULL(ES.DES_ESTADO,'') lugarNac, " +  
				"    ES.ID_ESTADO idLugarNac, " +  
				"    IFNULL(PER.REF_TELEFONO,'') telefono, " +  
				"	 IFNULL(PA.REF_PAQUETE_NOMBRE,'') tipoPaquete, " +
				"    IFNULL(PER.REF_CORREO,'') correo, " +  
				"    PER.ID_PERSONA idPersona, " +  
				"    PER.ID_ESTADO idEstado, " +  
				"    PER.FEC_NAC AS fechaNaciemiento,  " +  
				"    PER.NUM_SEXO AS idSexo,  " +  
				"    IFNULL(PF.ID_PROMOTOR,null) AS idPromotor,  " +  
				"    IFNULL(PER.REF_OTRO_SEXO,'') AS otroSexo,  " +  
				"    IFNULL(DOC.IND_INE_AFILIADO, 0) AS docIne, " +  
				"    IFNULL(DOC.IND_CURP, 0) AS docCurp,  " +  
				"    IFNULL(DOC.IND_RFC, 0) AS docRfc, " +  
				"    PF.DES_FOLIO folioConvenio, " +  
				"    CON.ID_DOMICILIO idDomicilio, " +  
				"    PAQ.IND_ENFERMEDAD_PREXISTENTE enfermedadPre, " +  
				"    IFNULL(PAQ.REF_OTRA_ENFERMEDAD, '') otraEnfermedad, " +  
				"    DOC.ID_VALIDACION_DOCUMENTO AS idValidaDocumento,  " +  
				"    DOM.ID_DOMICILIO idDomicilio, PAQ.ID_CONTRA_PAQ_CONVENIO_PF idPaqueteConvenio " +  
				" " +  
				"FROM " +  
				"    SVT_CONVENIO_PF PF " +  
				"INNER JOIN SVT_EMPRESA_CONVENIO_PF EMP ON  PF.ID_CONVENIO_PF = EMP.ID_CONVENIO_PF  " +  
				"INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF PAQ ON PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF " +  
				"INNER JOIN SVC_CONTRATANTE CON ON CON.ID_CONTRATANTE = PAQ.ID_CONTRATANTE " +  
				"INNER JOIN SVC_PERSONA PER ON PER.ID_PERSONA = CON.ID_PERSONA  " +  //-- persona contratante
				"INNER JOIN SVT_DOMICILIO DOM ON DOM.ID_DOMICILIO = CON.ID_DOMICILIO  " +  //-- domicilio del contratante
				"LEFT JOIN SVC_PAIS PAI ON PAI.ID_PAIS = PER.ID_PAIS " +  
				"LEFT  JOIN SVC_ESTADO ES ON ES.ID_ESTADO = PER.ID_ESTADO " +  
				"INNER  JOIN SVT_PAQUETE PA ON PA.ID_PAQUETE = PAQ.ID_PAQUETE  " +  
				"LEFT  JOIN SVC_VALIDA_DOCS_CONVENIO_PF DOC  ON PAQ.ID_CONTRA_PAQ_CONVENIO_PF  = DOC.ID_CONTRA_PAQ_CONVENIO_PF  " +  
				"WHERE PF.ID_CONVENIO_PF = #{idConvenioPf} and CON.IND_ACTIVO = 1")
		public ArrayList<DetalleConvenioPFXEmpresaSolicitantes> 
		consultaDetalleConvenioXEmpresaSolicitantes( @Param("idConvenioPf") Integer idConvenioPf );

		@Select("SELECT  " +  
				"  	 BEN.ID_CONTRATANTE_BENEFICIARIOS idBeneficiario, PER.ID_PERSONA idPersona, " +  
				"    IFNULL(PER.NOM_PERSONA,'') nombre, " +  
				"    IFNULL(PER.NOM_PRIMER_APELLIDO,'') primerApellido, " +  
				"    IFNULL(PER.NOM_SEGUNDO_APELLIDO,'') segundoApellido, " +   
				"    TIMESTAMPDIFF(YEAR, PER.FEC_NAC, CURDATE()) AS edad, " +  
				"    IFNULL(PAR.DES_PARENTESCO,'') parentesco, " +  
				"    IFNULL(PER.CVE_CURP,'')  curp, " +  
				"    IFNULL(PER.CVE_RFC ,'') rfc, " +  
				"    IFNULL(PER.REF_CORREO,'')  correo, " +  
				"    IFNULL(PER.REF_TELEFONO,'')  telefono, " +  
				"    PER.FEC_NAC AS fechaNaciemiento,  " +  
				"    PER.NUM_SEXO AS idSexo,  " +  
				"    IFNULL(PER.REF_OTRO_SEXO,'') AS otroSexo,  " +  
				"    IFNULL(BEN.IND_INE_BENEFICIARIO, 0) docIne,  " +   
				"    IFNULL(BEN.IND_ACTA_NACIMIENTO, 0) docActa,  " +   
				"    PER.ID_PAIS AS idPais, " +  
				"    PER.ID_ESTADO AS idEstado, " +  
		        "    CASE WHEN BEN.IND_ACTA_NACIMIENTO IS NULL OR 0 THEN IFNULL(BEN.REF_UBICACION_INE_BENEFICIARIO, '') ELSE IFNULL(BEN.REF_UBICACION_INE_BENEFICIARIO, '') END AS nombreArchivo,  " +   
                "    IFNULL(BEN.ID_PARENTESCO, '') idParentesco,  " +  
				"    PAQ.ID_CONTRATANTE idContratante " +  
				" " +  
				" " +  
				"FROM " +  
				"    SVT_CONVENIO_PF PF " +  
				"INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF PAQ ON PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF " +  
				"INNER JOIN SVT_CONTRATANTE_BENEFICIARIOS BEN ON BEN.ID_CONTRA_PAQ_CONVENIO_PF =PAQ.ID_CONTRA_PAQ_CONVENIO_PF " +  
				"INNER JOIN SVC_PARENTESCO PAR ON PAR.ID_PARENTESCO = BEN.ID_PARENTESCO " +  
				"INNER JOIN SVC_PERSONA PER ON PER.ID_PERSONA = BEN.ID_PERSONA  " +  // -- Persona BENEFICIARIO
				"WHERE BEN.IND_ACTIVO = 1  " +  
				"AND  BEN.ID_CONTRA_PAQ_CONVENIO_PF IN " +  
				"( " +  
				"    SELECT  " +  
				"        PAQ.ID_CONTRA_PAQ_CONVENIO_PF idPaquete " +  
				"    FROM " +  
				"        SVT_CONVENIO_PF PF " +  
				"    INNER JOIN SVT_EMPRESA_CONVENIO_PF EMP ON  PF.ID_CONVENIO_PF = EMP.ID_CONVENIO_PF  " +  
				"    INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF PAQ ON PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF " +  
				"    INNER JOIN SVC_CONTRATANTE CON ON CON.ID_CONTRATANTE = PAQ.ID_CONTRATANTE " +  
				"    INNER JOIN SVC_PERSONA PER ON PER.ID_PERSONA = CON.ID_PERSONA  " +  // -- persona contratante
				"    INNER JOIN SVT_DOMICILIO DOM ON DOM.ID_DOMICILIO = CON.ID_DOMICILIO  " +  // -- domicilio del contratante
				"    INNER JOIN SVC_PAIS PAI ON PAI.ID_PAIS = PER.ID_PAIS " +  
				"    LEFT  JOIN SVC_ESTADO ES ON ES.ID_ESTADO = PER.ID_ESTADO " +  
				"    WHERE PF.ID_CONVENIO_PF = #{idConvenioPf} and CON.IND_ACTIVO = 1 )")
		public ArrayList<DetalleConvenioPFXEmpresaBeneficiarios> 
		consultaDetalleConvenioXEmpresaBeneficiarios( @Param("idConvenioPf") Integer idConvenioPf );

		@Select("SELECT  " +  
				"	BEN.ID_CONTRATANTE_BENEFICIARIOS idBeneficiario, " +  
				"   BEN.REF_UBICACION_INE_BENEFICIARIO refUbicacionIneBeneficiario, " +  
				"   BEN.REF_UBICACION_ACTA_NACIMIENTO refUbicacionActaNac, " +  
				"	BEN.REF_DOC_ACTA_NACIMIENTO_BENEFICIARIO refDocActaNacBeneficiario, " +  
				"	BEN.REF_DOC_INE_BENEFICIARIO refDocIneBeneficiario, " +  
				"   BEN.REF_DOCUMENTO_BENEFICIARIO refDocBeneficiario " +  
				"FROM " +  
				"    SVT_CONVENIO_PF PF " +  
				"INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF PAQ ON PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF " +  
				"INNER JOIN SVT_CONTRATANTE_BENEFICIARIOS BEN ON BEN.ID_CONTRA_PAQ_CONVENIO_PF =PAQ.ID_CONTRA_PAQ_CONVENIO_PF " +  
				"INNER JOIN SVC_PARENTESCO PAR ON PAR.ID_PARENTESCO = BEN.ID_PARENTESCO " +  
				"INNER JOIN SVC_PERSONA PER ON PER.ID_PERSONA = BEN.ID_PERSONA  " +  
				"WHERE BEN.IND_ACTIVO = 1  " +  
				"AND 	BEN.ID_CONTRA_PAQ_CONVENIO_PF IN " +  
				"( " +  
				"    SELECT  " +  
				"        PAQ.ID_CONTRA_PAQ_CONVENIO_PF idPaquete " +  
				"    FROM " +  
				"        SVT_CONVENIO_PF PF " +  
				"    INNER JOIN SVT_EMPRESA_CONVENIO_PF EMP ON  PF.ID_CONVENIO_PF = EMP.ID_CONVENIO_PF  " +  
				"    INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF PAQ ON PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF " +  
				"    INNER JOIN SVC_CONTRATANTE CON ON CON.ID_CONTRATANTE = PAQ.ID_CONTRATANTE " +  
				"    INNER JOIN SVC_PERSONA PER ON PER.ID_PERSONA = CON.ID_PERSONA " +  
				"    INNER JOIN SVT_DOMICILIO DOM ON DOM.ID_DOMICILIO = CON.ID_DOMICILIO " +  
				"    INNER JOIN SVC_PAIS PAI ON PAI.ID_PAIS = PER.ID_PAIS " +  
				"    INNER JOIN SVC_ESTADO ES ON ES.ID_ESTADO = PER.ID_ESTADO " +  
				" " +  
				"    WHERE PF.ID_CONVENIO_PF = #{idConvenioPf}  and CON.IND_ACTIVO = 1 )")
		public ArrayList<DetalleConvenioPFXEmpresaBeneficiariosDocs> 
		consultaDetalleConvenioXEmpresaBeneficiariosDocs( @Param("idConvenioPf") Integer idConvenioPf );

		@Select("SELECT " +
				"    SUM(PA.MON_PRECIO) " +
				"FROM " +
				"    SVT_CONVENIO_PF PF " +
				"LEFT JOIN SVT_EMPRESA_CONVENIO_PF EMP ON " +
				"    PF.ID_CONVENIO_PF = EMP.ID_CONVENIO_PF " +
				"LEFT JOIN SVT_CONTRA_PAQ_CONVENIO_PF PAQ ON " +
				"    PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF " +
				"INNER JOIN SVT_PAQUETE PA ON " +
				"    PA.ID_PAQUETE = PAQ.ID_PAQUETE " +
				"     " +
				"    WHERE PF.ID_CONVENIO_PF = #{idConvenioPf}")
		public Double consultaImportePaquetesConvenio( @Param("idConvenioPf") Integer idConvenioPf );

		@Insert("INSERT INTO SVT_PAGO_BITACORA( " +
				"    ID_REGISTRO, " +
				"    ID_FLUJO_PAGOS, " +
				"    ID_VELATORIO, " +
				"    FEC_ODS, " +
				"    NOM_CONTRATANTE,  " +
				"    CVE_FOLIO, " +
				"    IMP_VALOR, " +
				"    CVE_ESTATUS_PAGO, " +
				"    ID_USUARIO_ALTA, " +
				"    ID_PLATAFORMA " +
				") " +
				"VALUES( " +
				"	#{registroPagoPlanPF.idConvenioPf}, " +
				"	#{registroPagoPlanPF.idFlujo}, " +
				"	#{registroPagoPlanPF.idVelatorio}, " +
				"	NOW(), " +
				"	#{registroPagoPlanPF.nomContratante}, " +
				"	#{registroPagoPlanPF.cveFolio}, " +
				"	#{registroPagoPlanPF.importe}, " +
				"	#{registroPagoPlanPF.cvdEstatusPago}, " +
				"	#{registroPagoPlanPF.idUsuarioAlta}, " +
				"	#{registroPagoPlanPF.idPlataforma} ) " +
				";" )
		public int insertaPago (@Param("registroPagoPlanPF") RegistroPagoPlanPF registroCOnvenio);
		
		@Select("SELECT DOC.REF_DOC_INE_AFILIADO AS documento "
				+ " FROM SVT_CONTRA_PAQ_CONVENIO_PF SCPCP "
				+ " INNER JOIN SVC_VALIDA_DOCS_CONVENIO_PF DOC  ON SCPCP.ID_CONTRA_PAQ_CONVENIO_PF  = DOC.ID_CONTRA_PAQ_CONVENIO_PF  "
				+"WHERE " + 
				"    SCPCP.ID_CONTRA_PAQ_CONVENIO_PF = #{idPaqueteConvenio} AND SCPCP.ID_CONTRATANTE = #{idContratante}  AND DOC.IND_INE_AFILIADO = #{tipoDocumento};    ")
		public String consultaInePDFContratante( @Param("idPaqueteConvenio") Integer idPaqueteConvenio,@Param("idContratante") Integer idContratante,@Param("tipoDocumento") Integer tipoDocumento  );

		@Select("SELECT DOC.REF_DOC_RFC_AFILIADO AS documento "
				+ " FROM SVT_CONTRA_PAQ_CONVENIO_PF SCPCP "
				+ " INNER JOIN SVC_VALIDA_DOCS_CONVENIO_PF DOC  ON SCPCP.ID_CONTRA_PAQ_CONVENIO_PF  = DOC.ID_CONTRA_PAQ_CONVENIO_PF  "
				+"WHERE " + 
				"    SCPCP.ID_CONTRA_PAQ_CONVENIO_PF = #{idPaqueteConvenio} AND SCPCP.ID_CONTRATANTE = #{idContratante}  AND DOC.IND_RFC = #{tipoDocumento};    ")
		public String consultaRfcPDFContratante( @Param("idPaqueteConvenio") Integer idPaqueteConvenio,@Param("idContratante") Integer idContratante,@Param("tipoDocumento") Integer tipoDocumento  );
		
		@Select("SELECT DOC.REF_DOC_CURP_AFILIADO AS documento "
				+ " FROM SVT_CONTRA_PAQ_CONVENIO_PF SCPCP "
				+ " INNER JOIN SVC_VALIDA_DOCS_CONVENIO_PF DOC  ON SCPCP.ID_CONTRA_PAQ_CONVENIO_PF  = DOC.ID_CONTRA_PAQ_CONVENIO_PF  "
				+"WHERE " + 
				"    SCPCP.ID_CONTRA_PAQ_CONVENIO_PF = #{idPaqueteConvenio} AND SCPCP.ID_CONTRATANTE = #{idContratante}  AND DOC.IND_CURP = #{tipoDocumento};    ")
		public String consultaCurpPDFContratante( @Param("idPaqueteConvenio") Integer idPaqueteConvenio,@Param("idContratante") Integer idContratante,@Param("tipoDocumento") Integer tipoDocumento  );
		
		
		@Select("SELECT SCB.REF_DOCUMENTO_BENEFICIARIO  AS documento "
				+ " FROM SVT_CONTRA_PAQ_CONVENIO_PF SCPCP "
				+ " INNER JOIN SVT_CONTRATANTE_BENEFICIARIOS SCB  ON SCPCP.ID_CONTRA_PAQ_CONVENIO_PF  = SCB.ID_CONTRA_PAQ_CONVENIO_PF   "
				+"WHERE " + 
				"    SCPCP.ID_CONTRA_PAQ_CONVENIO_PF = #{idPaqueteConvenio} AND SCB.ID_PERSONA = #{idPersona}  AND SCB.IND_INE_BENEFICIARIO = #{tipoDocumento};    ")
		public String consultaInePDFBeneficiario( @Param("idPaqueteConvenio") Integer idPaqueteConvenio,@Param("idPersona") Integer idPersona,@Param("tipoDocumento") Integer tipoDocumento  );

		@Select("SELECT SCB.REF_DOCUMENTO_BENEFICIARIO  AS documento "
				+ " FROM SVT_CONTRA_PAQ_CONVENIO_PF SCPCP "
				+ " INNER JOIN SVT_CONTRATANTE_BENEFICIARIOS SCB  ON SCPCP.ID_CONTRA_PAQ_CONVENIO_PF  = SCB.ID_CONTRA_PAQ_CONVENIO_PF   "
				+"WHERE " + 
				"    SCPCP.ID_CONTRA_PAQ_CONVENIO_PF = #{idPaqueteConvenio} AND SCB.ID_PERSONA = #{idPersona}  AND SCB.IND_ACTA_NACIMIENTO = #{tipoDocumento};    ")
		public String consultaActaPDFBeneficiario( @Param("idPaqueteConvenio") Integer idPaqueteConvenio,@Param("idPersona") Integer idPersona,@Param("tipoDocumento") Integer tipoDocumento  );

		

}
