package com.imss.sivimss.arquetipo.configuration.mapper;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.imss.sivimss.arquetipo.model.entity.DatosEmpresa;
import com.imss.sivimss.arquetipo.model.entity.DatosEmpresaBeneficiarios;
import com.imss.sivimss.arquetipo.model.entity.DatosEmpresaSolicitante;

/*
 * Este es un ejemplo de cómo se pueden implementar querys a través de interfaces con MyBatis.
 * */

public interface Empresas {

	@Update("UPDATE " +
			"     " +
			"    SVT_EMPRESA_CONVENIO_PF " +
			"SET " +
			"    REF_NOMBRE 		= #{empresa.nombreEmpresa}, " +
			"    REF_RAZON_SOCIAL 	= #{empresa.razonSocial}, " +
			"    CVE_RFC 			= #{empresa.rfc}, " +
			"    ID_PAIS 			= #{empresa.idPaisEmpresa}, " +
			"    REF_TELEFONO 		= #{empresa.telefonoEmpresa}, " +
			"    REF_CORREO 		= #{empresa.correoEmpresa} " +
			"WHERE " +
			"    ID_EMPRESA_CONVENIO_PF =( " +
			"    SELECT " +
			"        EMP.ID_EMPRESA_CONVENIO_PF " +
			"    FROM " +
			"        SVT_CONVENIO_PF PF " +
			"    INNER JOIN SVT_EMPRESA_CONVENIO_PF EMP ON PF.ID_CONVENIO_PF = EMP.ID_CONVENIO_PF " +
			"    INNER JOIN SVT_DOMICILIO DOM 			ON EMP.ID_DOMICILIO = DOM.ID_DOMICILIO " +
			"    INNER JOIN SVC_PAIS PAI 				ON PAI.ID_PAIS = EMP.ID_PAIS " +
			"    WHERE " +
			"        PF.ID_CONVENIO_PF = #{empresa.idConvenioPf}  " +
			");")
	public int actualizarDatosEmpresa(@Param("empresa") DatosEmpresa empresa);

	@Update("UPDATE " +
			"    SVT_DOMICILIO " +
			"SET " +
			"	 REF_CP 			= #{empresa.cp} , " +
			"    REF_COLONIA 		= #{empresa.colonia} , " +
			"    REF_ESTADO 		= #{empresa.estado} , " +
			"    REF_MUNICIPIO 		= #{empresa.municipio} , " +
			"    REF_CALLE 			= #{empresa.calle} , " +
			"    NUM_INTERIOR 		= #{empresa.numInterior} , " +
			"    NUM_EXTERIOR 		= #{empresa.numExterior}  " +
			"WHERE " +
			"	ID_DOMICILIO = ( " +
			"    SELECT " +
			"        EMP.ID_DOMICILIO " +
			"    FROM " +
			"        SVT_CONVENIO_PF PF " +
			"    INNER JOIN SVT_EMPRESA_CONVENIO_PF EMP ON PF.ID_CONVENIO_PF = EMP.ID_CONVENIO_PF " +
			"    INNER JOIN SVT_DOMICILIO DOM 			ON EMP.ID_DOMICILIO =  DOM.ID_DOMICILIO " +
			"    INNER JOIN SVC_PAIS PAI 				ON PAI.ID_PAIS = EMP.ID_PAIS " +
			"    WHERE " +
			"        PF.ID_CONVENIO_PF = #{empresa.idConvenioPf2}  " +
			");  ")
	public int actualizarDomicilioEmpresa(@Param("empresa") DatosEmpresa empresa);

	@Update("UPDATE " +
			"	 SVC_PERSONA " +
			"SET " +
			"	 CVE_RFC 				= #{solicitante.rfc}, " +
			"    CVE_CURP 				= #{solicitante.curp}, " +
			"    NOM_PERSONA 			= #{solicitante.nombre}, " +
			"    NOM_PRIMER_APELLIDO 	= #{solicitante.primerApe}, " +
			"    NOM_SEGUNDO_APELLIDO 	= #{solicitante.segunApe}, " +
			"    REF_TELEFONO 			= #{solicitante.telefonoSol}, " +
			"    REF_CORREO 			= #{solicitante.correoSol}, " +
			"    ID_PAIS 				= #{solicitante.idPaisSolicitante}, " +
			"    ID_ESTADO 				= #{solicitante.idEstadoSolicitante} " +
			"WHERE " +
			"ID_PERSONA 			= #{solicitante.idPersona} ;")
	public int actualizarSolicitante(@Param("solicitante") DatosEmpresaSolicitante solicitante);

	@Update("UPDATE " +
			"    SVT_DOMICILIO " +
			"SET " +
			"	 REF_CP 			= #{solicitante.cp} , " +
			"    REF_COLONIA 		= #{solicitante.colonia} , " +
			"    REF_ESTADO 		= #{solicitante.estado} , " +
			"    REF_MUNICIPIO 		= #{solicitante.municipio} , " +
			"    REF_CALLE 			= #{solicitante.calle} , " +
			"    NUM_INTERIOR 		= #{solicitante.numInterior} , " +
			"    NUM_EXTERIOR 		= #{solicitante.numExterior}  " +
			"WHERE " +
			"ID_DOMICILIO  		= #{solicitante.idDomicilio} ;")
	public int actualizarDomicilioSolicitante(@Param("solicitante") DatosEmpresaSolicitante empresa);

	@Update("UPDATE " +
			"	SVT_CONTRATANTE_BENEFICIARIOS " +
			"SET     " +
			"	ID_PARENTESCO = #{beneficiario.idParentesco} " +
			"WHERE     " +
			"	ID_CONTRATANTE_BENEFICIARIOS = #{beneficiario.idBeneficiario} ;")
	public int actualizarBeneficiarios(@Param("beneficiario") DatosEmpresaBeneficiarios empresa);

	@Update("UPDATE  " +
			"	SVC_PERSONA  " +
			"SET  " +
			"	NOM_PERSONA 		= #{beneficiario.nombre},  " +
			"   NOM_PRIMER_APELLIDO = #{beneficiario.primerApe},  " +
			"   NOM_SEGUNDO_APELLIDO= #{beneficiario.segunApe},  " +
			"   CVE_CURP 			= #{beneficiario.curp},  " +
			"	CVE_RFC 			= #{beneficiario.rfc},  " +
			"	REF_TELEFONO 		= #{beneficiario.telefonoSol},  " +
			"   REF_CORREO 			= #{beneficiario.correoSol} " +
			"WHERE  " +
			"	ID_PERSONA 			= #{beneficiario.idPersona} ;")
	public int actualizarBeneficiarios2(@Param("beneficiario") DatosEmpresaBeneficiarios empresa);

	@Update("UPDATE SVC_VALIDA_DOCS_CONVENIO_PF SET IND_INE_AFILIADO=1," +
			"REF_UBICACION_INE = #{beneficiario.archivoIne}," +
			"ID_USUARIO_MODIFICA= #{beneficiario.idUsuario}," +
			"FEC_ACTUALIZACION = CURRENT_DATE()" +
			"WHERE ID_VALIDACION_DOCUMENTO = #{beneficiario.idValidaDocumento}")
	public int actualizarArchivoIne(@Param("in") DatosEmpresaSolicitante empresa);

	@Update("UPDATE SVC_VALIDA_DOCS_CONVENIO_PF SET IND_CURP=1," +
			"REF_UBICACION_CURP = #{beneficiario.archivoCurp}," +
			"ID_USUARIO_MODIFICA= #{beneficiario.idUsuario}," +
			"FEC_ACTUALIZACION = CURRENT_DATE()" +
			"WHERE ID_VALIDACION_DOCUMENTO = #{beneficiario.idValidaDocumento}")
	public int actualizarArchivoCurp(@Param("in") DatosEmpresaSolicitante empresa);

	@Update("UPDATE SVC_VALIDA_DOCS_CONVENIO_PF SET IND_RFC=1," +
			"REF_UBICACION_RFC = #{beneficiario.archivoRFC}," +
			"ID_USUARIO_MODIFICA= #{beneficiario.idUsuario}," +
			"FEC_ACTUALIZACION = CURRENT_DATE()" +
			"WHERE ID_VALIDACION_DOCUMENTO = #{beneficiario.idValidaDocumento}")
	public int actualizarArchivoRfc(@Param("in") DatosEmpresaSolicitante empresa);

	@Update(value = ""
			+ "UPDATE SVT_CONTRATANTE_BENEFICIARIOS  "
			+ "SET  "
			+ "FEC_ACTUALIZACION = CURRENT_DATE(), "
			+ "IND_ACTIVO = 1, "
			+ "ID_USUARIO_MODIFICA = #{in.idUsuario} ,"
			+ "IND_INE_BENEFICIARIO = #{in.validaIne} ,"
			+ "REF_UBICACION_INE_BENEFICIARIO = #{in.nombreIne}, "
			+ "IND_ACTA_NACIMIENTO  = #{in.validaActa},"
			+ "REF_UBICACION_ACTA_NACIMIENTO=#{in.nombreActa},"
			+ "REF_DOCUMENTO_BENEFICIARIO=#{in.documento} "
			+ "WHERE ID_CONTRATANTE_BENEFICIARIOS = (SELECT cb.ID_CONTRATANTE_BENEFICIARIOS " +
			" from SVT_CONTRATANTE_BENEFICIARIOS cb " +
			" WHERE cb.ID_CONTRA_PAQ_CONVENIO_PF= (" +
			" SELECT pc.ID_CONTRA_PAQ_CONVENIO_PF " +
			" FROM SVT_CONVENIO_PF pf " +
			" join SVT_CONTRA_PAQ_CONVENIO_PF pc ON pc.ID_CONVENIO_PF=pf.ID_CONVENIO_PF" +
			" WHERE pf.ID_CONVENIO_PF = #{in.idConvenio})) "
			+ " AND ID_PERSONA = #{in.idPersona}")
	public int actualizarArchivoBeneficiario(@Param("in") DatosEmpresaBeneficiarios persona);

}
