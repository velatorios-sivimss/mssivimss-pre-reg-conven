package com.imss.sivimss.arquetipo.configuration.mapper;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.imss.sivimss.arquetipo.model.entity.DatosEmpresa;
import com.imss.sivimss.arquetipo.model.entity.DatosEmpresaBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.DatosEmpresaSolicitante;
import com.imss.sivimss.arquetipo.model.entity.DatosPersonaBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.DatosPersonaConvenio;

/*
 * Este es un ejemplo de cómo se pueden implementar querys a través de interfaces con MyBatis.
 * */


public interface Personas {

	/* 
	
	UPDATE
    SVC_PERSONA
	SET
		NOM_PERSONA 		= #{persona.nombre},
		NOM_PRIMER_APELLIDO = #{persona.primerApe},
		NOM_SEGUNDO_APELLIDO= #{persona.segunApe},
		CVE_CURP 			= #{persona.curp},
		CVE_RFC 			= #{persona.rfc},
		REF_TELEFONO 		= #{persona.telefonoSol},
		REF_CORREO 			= #{persona.correoSol},
		ID_PAIS 			= #{persona.idPais},
		ID_ESTADO 			= #{persona.idEstado}
		
	WHERE
		ID_PERSONA 			= #{persona.idPersona} ;

	UPDATE
		SVT_DOMICILIO
	SET
		
		REF_CALLE 		= #{persona.calle},
		NUM_EXTERIOR 	= #{persona.numExt},
		NUM_INTERIOR 	= #{persona.numInt},
		REF_CP 			= #{persona.cp},
		REF_COLONIA 	= #{persona.colonia},
		REF_MUNICIPIO 	= #{persona.municipio},
		REF_ESTADO 		= #{persona.estado}
		
	WHERE
		ID_DOMICILIO = #{persona.idDomicilio} ;


	UPDATE
		SVT_CONTRA_PAQ_CONVENIO_PF
	SET
		
		IND_ENFERMEDAD_PREXISTENTE 	= #{persona.indEnfermedad},
		REF_OTRA_ENFERMEDAD 		= #{persona.otraEnfermedad},
		ID_PAQUETE 					= #{persona.idPaquete},
		
	WHERE
		ID_CONTRA_PAQ_CONVENIO_PF = #{persona.idContraPaqPF}	
	*/

	@Update(
		"UPDATE " + 
			"    SVC_PERSONA " + 
		"SET " + 
		"	NOM_PERSONA 		= #{persona.nombre}, " + 
		"	NOM_PRIMER_APELLIDO = #{persona.primerApe}, " + 
		"	NOM_SEGUNDO_APELLIDO= #{persona.segunApe}, " + 
		"	CVE_CURP 			= #{persona.curp}, " + 
		"	CVE_RFC 			= #{persona.rfc}, " + 
		"	REF_TELEFONO 		= #{persona.telefono}, " + 
		"	REF_CORREO 			= #{persona.correo}, " + 
		"	ID_PAIS 			= #{persona.idPais}, " + 
		"	ID_ESTADO 			= #{persona.idEstado} " + 
		"" + 
		"WHERE " + 
		"	ID_PERSONA 			= #{persona.idPersona} ;"
		)	
	public int actualizarDatosContratante (@Param("persona") DatosPersonaConvenio empresa);

	@Update(
		"UPDATE " + 
		"	SVT_DOMICILIO " + 
		"SET " + 
		" " + 
		"	REF_CALLE 		= #{persona.calle}, " + 
		"	NUM_EXTERIOR 	= #{persona.numExt}, " + 
		"	NUM_INTERIOR 	= #{persona.numInt}, " + 
		"	REF_CP 			= #{persona.cp}, " + 
		"	REF_COLONIA 	= #{persona.colonia}, " + 
		"	REF_MUNICIPIO 	= #{persona.municipio}, " + 
		"	REF_ESTADO 		= #{persona.estado} " + 
		" " + 
		"WHERE " + 
		"ID_DOMICILIO = #{persona.idDomicilio} ;"
		)	
	public int actualizarDomicilioContratante (@Param("persona") DatosPersonaConvenio empresa);

	@Update(
		"UPDATE " + 
		"	SVT_CONTRA_PAQ_CONVENIO_PF " + 
		"SET " + 
		" " + 
		"	IND_ENFERMEDAD_PREXISTENTE 	= #{persona.indEnfermedad}, " + 
		"	REF_OTRA_ENFERMEDAD 		= #{persona.otraEnfermedad}, " + 
		"	ID_PAQUETE 					= #{persona.idPaquete} " + 
		" " + 
		"WHERE " + 
		"	ID_CONTRA_PAQ_CONVENIO_PF = #{persona.idContraPaqPF} "
		)	
	public int actualizarPaqueteContratante (@Param("persona") DatosPersonaConvenio empresa);

	@Update(
			"UPDATE " + 
			"	SVT_CONTRATANTE_BENEFICIARIOS " + 
			"SET     " + 
			"	ID_PARENTESCO = #{beneficiario.idParentesco} " + 
			"WHERE     " + 
			"	ID_CONTRATANTE_BENEFICIARIOS = #{beneficiario.idBeneficiario} ;"
			)	
	public int actualizarBeneficiariosParentesco (@Param("beneficiario") DatosPersonaBeneficiarios empresa);

	@Update(
			"UPDATE  " + 
			"	SVC_PERSONA  " + 
			"SET  " + 
			"   CVE_CURP 			= #{beneficiario.curp},  " + 
			"	CVE_RFC 			= #{beneficiario.rfc},  " + 
			"	REF_TELEFONO 		= #{beneficiario.telefono},  " + 
			"   REF_CORREO 			= #{beneficiario.correo} " + 
			"WHERE  " + 
			"	ID_PERSONA 			= #{beneficiario.idPersona} ;"
			)	
	public int actualizarBeneficiarios (@Param("beneficiario") DatosPersonaBeneficiarios empresa);
}
