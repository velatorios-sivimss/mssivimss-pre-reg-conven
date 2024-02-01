package com.imss.sivimss.arquetipo.configuration.mapper;

import java.util.ArrayList;
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
			"    IFNULL(sc.CVE_MATRICULA, '') AS matricula, " +  
			"    SCP.DES_FOLIO folioConvenio, " +  
			"    SP.CVE_RFC rfc, " +  
			"    SP.CVE_CURP curp, " +  
			"    SP.NOM_PERSONA AS nombre, " +  
			"    SP.NOM_PRIMER_APELLIDO AS primerApellido, " +  
			"    SP.NOM_SEGUNDO_APELLIDO AS segundoApellido, " +  
			"    SD.REF_CALLE AS calle, " +  
			"    SD.NUM_EXTERIOR AS numExt, " +  
			"    SD.NUM_INTERIOR AS numInt, " +  
			"    SD.REF_CP AS cp, " +  
			"\tSD.REF_COLONIA AS colonia, " +  
			"\tSD.REF_MUNICIPIO AS municipio, " +  
			"\tSD.REF_ESTADO AS estado, " +  
			"    PAI.DES_PAIS PAIS, " +  
			"\tSP.ID_PAIS AS idPais, " +  
			"    se.DES_ESTADO AS lugarNac, " +  
			"\tSP.ID_ESTADO AS idLugarNac, " +  
			"    SP.REF_CORREO AS correo, " +  
			"    SP.REF_TELEFONO AS telCelular, " +  
			"\tSCPA.ID_PAQUETE idPaquete, " +  
			"\tPA.REF_PAQUETE_NOMBRE tipoPaquete, " +  
			"    ENF.IND_ENFERMEDAD_PREXISTENTE enfermedadPre, " +  
			"    ENF.REF_OTRA_ENFERMEDAD otraEnfermedad, SCP.IND_ACTIVO activo " +  
			"FROM " +  
			"    SVT_CONVENIO_PF SCP " +  
			"INNER JOIN SVC_ESTATUS_CONVENIO_PF SECP \tON SCP.ID_ESTATUS_CONVENIO = SECP.ID_ESTATUS_CONVENIO_PF " +  
			"INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF SCPA \tON SCP.ID_CONVENIO_PF = SCPA.ID_CONVENIO_PF " +  
			"INNER JOIN SVT_PAQUETE \t\t\t\t\t\tPA ON PA.ID_PAQUETE = SCPA.ID_PAQUETE " +  
			"INNER JOIN SVC_CONTRATANTE SC \t\t\t\tON SCPA.ID_CONTRATANTE = SC.ID_CONTRATANTE " +  
			"INNER JOIN SVT_DOMICILIO SD \t\t\t\tON SC.ID_DOMICILIO = SD.ID_DOMICILIO " +  
			"INNER JOIN SVC_PERSONA SP \t\t\t\t\tON SC.ID_PERSONA = SP.ID_PERSONA " +  
			"INNER JOIN SVC_VELATORIO V \t\t\t\t\tON V.ID_VELATORIO = SCP.ID_VELATORIO " +  
			"LEFT JOIN SVC_PAIS PAI \t\t\t\t\t\tON PAI.ID_PAIS = SP.ID_PAIS " +  
			"LEFT JOIN SVC_ESTADO se \t\t\t\t\tON se.ID_ESTADO = SP.ID_ESTADO " +  
			"LEFT JOIN SVT_CONTRA_PAQ_CONVENIO_PF ENF\tON ENF.ID_CONVENIO_PF = SCP.ID_CONVENIO_PF " +  
			"WHERE SCP.ID_CONVENIO_PF = #{idConvenioPf} " +  
			"   ")
	public DetalleConvenioPFXPersona consultaDetalleConvenioXPersona( @Param("idConvenioPf") Integer idConvenioPf );

	@Select("  SELECT  " +   
			"BEN.ID_CONTRATANTE_BENEFICIARIOS  idBeneficiario, BEN.ID_CONTRATANTE_BENEFICIARIOS idBeneficiario,  " +   
			"CONCAT(PER.NOM_PERSONA,' ',PER.NOM_PRIMER_APELLIDO,' ',PER.NOM_SEGUNDO_APELLIDO) nombre,  " +   
			"TIMESTAMPDIFF(YEAR, PER.FEC_NAC, CURDATE()) AS edad,  " +   
			"PAR.DES_PARENTESCO,  " +   
			"PER.CVE_CURP curp,  " +   
			"PER.CVE_RFC rfc,  " +   
			"PER.REF_CORREO correo,  " +   
			"PER.REF_TELEFONO telefono,  " +   
			"PAQ.ID_CONTRATANTE idContratante  " +   
			"  " +   
			"  " +   
			"FROM  " +   
			"SVT_CONVENIO_PF PF  " +   
			"INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF PAQ ON PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF  " +   
			"INNER JOIN SVT_CONTRATANTE_BENEFICIARIOS BEN ON BEN.ID_CONTRA_PAQ_CONVENIO_PF=PAQ.ID_CONTRA_PAQ_CONVENIO_PF  " +   
			"INNER JOIN SVC_PARENTESCO PAR ON PAR.ID_PARENTESCO = BEN.ID_PARENTESCO  " +   
			"INNER JOIN SVC_PERSONA PER ON PER.ID_PERSONA = BEN.ID_PERSONA  " +   
			"WHERE BEN.IND_ACTIVO = 1  " +   
			"AND  PAR.ID_PARENTESCO IN (5,8,9,10,11,12,17)  " +   
			"AND  BEN.ID_CONTRA_PAQ_CONVENIO_PF IN  " +   
			"(  " +   
			"SELECT  " +   
			"    SCPA.ID_PAQUETE idPaquete  " +   
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
			"LEFT JOIN svt_contra_paq_convenio_pf ENF ON  " +   
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
		+ "	EMP.REF_NOMBRE AS nombre, "
		+ "			EMP.REF_RAZON_SOCIAL AS razonSocial, "
		+ "			EMP.CVE_RFC AS rfc, "
		+ "			EMP.ID_PAIS AS idPais, "
		+ "			PAI.DES_PAIS as pais, "
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
		+ "			PF.ID_PROMOTOR AS idPromotor, "
		+ "			PA.REF_PAQUETE_NOMBRE tipoPaquete, "
		+ "			PF.DES_FOLIO folioConvenio, PF.IND_ACTIVO activo "
		+ "FROM "
		+ "			SVT_CONVENIO_PF PF "
		+ "INNER JOIN SVT_EMPRESA_CONVENIO_PF EMP ON "
		+ "			PF.ID_CONVENIO_PF = EMP.ID_CONVENIO_PF "
		+ "INNER JOIN SVT_DOMICILIO DOM ON "
		+ "			EMP.ID_DOMICILIO = DOM.ID_DOMICILIO "
		+ "INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF PAQ ON PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF "
		+ " INNER JOIN SVT_PAQUETE PA ON PA.ID_PAQUETE = PAQ.ID_PAQUETE "
		+ " INNER JOIN SVC_PAIS PAI ON PAI.ID_PAIS = EMP.ID_PAIS "
		+ "WHERE "
		+ "			EMP.ID_CONVENIO_PF = #{idConvenioPf} ")
		public DetalleConvenioPFXEmpresa consultaDetalleConvenioXEmpresa( @Param("idConvenioPf") Integer idConvenioPf );

		@Select(" SELECT  " +  
				"    PAQ.ID_CONTRA_PAQ_CONVENIO_PF idPaquete, " +  
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
				"    DOM.REF_MUNICIPIO municipio, " +  
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
				"INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF PAQ ON PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF " +  
				"INNER JOIN SVC_CONTRATANTE CON ON CON.ID_CONTRATANTE = PAQ.ID_CONTRATANTE " +  
				"INNER JOIN SVC_PERSONA PER ON PER.ID_PERSONA = CON.ID_PERSONA  " +  //-- persona contratante
				"INNER JOIN SVT_DOMICILIO DOM ON DOM.ID_DOMICILIO = CON.ID_DOMICILIO  " +  //-- domicilio del contratante
				"INNER JOIN SVC_PAIS PAI ON PAI.ID_PAIS = PER.ID_PAIS " +  
				"INNER JOIN SVC_ESTADO ES ON ES.ID_ESTADO = PER.ID_ESTADO " +  
				" " +  
				"WHERE PF.ID_CONVENIO_PF = #{idConvenioPf} and CON.IND_ACTIVO = 1")
		public ArrayList<DetalleConvenioPFXEmpresaSolicitantes> 
		consultaDetalleConvenioXEmpresaSolicitantes( @Param("idConvenioPf") Integer idConvenioPf );

		@Select("SELECT  " +  
				"  	 BEN.ID_CONTRATANTE_BENEFICIARIOS idBeneficiario, " +  
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
				"    INNER JOIN SVC_ESTADO ES ON ES.ID_ESTADO = PER.ID_ESTADO " +  
				"    WHERE PF.ID_CONVENIO_PF = #{idConvenioPf} and CON.IND_ACTIVO = 1 )")
		public ArrayList<DetalleConvenioPFXEmpresaBeneficiarios> 
		consultaDetalleConvenioXEmpresaBeneficiarios( @Param("idConvenioPf") Integer idConvenioPf );

		@Select("SELECT  " +  
				"\tBEN.ID_CONTRATANTE_BENEFICIARIOS idBeneficiario, " +  
				"    BEN.REF_UBICACION_INE_BENEFICIARIO refUbicacionIneBeneficiario, " +  
				"    BEN.REF_UBICACION_ACTA_NACIMIENTO refUbicacionActaNac, " +  
				"\tBEN.REF_DOC_ACTA_NACIMIENTO_BENEFICIARIO refDocActaNacBeneficiario, " +  
				"\tBEN.REF_DOC_INE_BENEFICIARIO refDocIneBeneficiario, " +  
				"    BEN.REF_DOCUMENTO_BENEFICIARIO refDocBeneficiario " +  
				"FROM " +  
				"    SVT_CONVENIO_PF PF " +  
				"INNER JOIN SVT_CONTRA_PAQ_CONVENIO_PF PAQ ON PAQ.ID_CONVENIO_PF = PF.ID_CONVENIO_PF " +  
				"INNER JOIN SVT_CONTRATANTE_BENEFICIARIOS BEN ON BEN.ID_CONTRA_PAQ_CONVENIO_PF =PAQ.ID_CONTRA_PAQ_CONVENIO_PF " +  
				"INNER JOIN SVC_PARENTESCO PAR ON PAR.ID_PARENTESCO = BEN.ID_PARENTESCO " +  
				"INNER JOIN SVC_PERSONA PER ON PER.ID_PERSONA = BEN.ID_PERSONA  " +  
				"WHERE BEN.IND_ACTIVO = 1  " +  
				"AND \tBEN.ID_CONTRA_PAQ_CONVENIO_PF IN " +  
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
}
