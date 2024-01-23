package com.imss.sivimss.arquetipo.configuration.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXPersona;
import com.imss.sivimss.arquetipo.model.entity.PersonaEntityMyBatis;

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
	
	/*
	 * Este es un ejemplo para realizar un insert con un objeto como parámetro
	 * Esta debería ser la forma estandar para insertar nuevos registros
	 * 
	 * Se debe pasar un objeto con la anotacion @Param("out") para despues sacar los
	 * valores del objeto usando los comodines 
	 * #{out.nomPersona} -> #{nombreDelParam.nombreAtributoDeClase}
	 * 
	 * Esta expresión se utiliza para especificar el atributo del objeto que almacenará 
	 * el identificador del nuevo registro. @Options tiene mas aplicaciones, pero en este ejemplo
	 * se limita solo a obtener el id generado
	 * @Options(useGeneratedKeys = true,keyProperty = "out.idPersona", keyColumn="id")
	 * 
	 * 
	@Insert(value = "INSERT INTO SVC_PERSONA(NOM_PERSONA, NOM_PRIMER_APELLIDO, NOM_SEGUNDO_APELLIDO) "
			+ "VALUES ( #{out.nomPersona},#{out.primerApellido},#{out.segundoApellido} )")
	@Options(useGeneratedKeys = true,keyProperty = "out.idPersona", keyColumn="id")
	public int nuevoRegistroObj(@Param("out")PersonaEntityMyBatis persona);
	
	@Update(value = ""
			+ "UPDATE SVC_PERSONA  "
			+ "SET  "
			+ "	NOM_PERSONA=#{in.nomPersona}, "
			+ "	NOM_PRIMER_APELLIDO=#{in.primerApellido}, "
			+ "	NOM_SEGUNDO_APELLIDO=#{in.segundoApellido} "
			+ "WHERE ID_PERSONA=#{in.idPersona}")
	public int actualizarRegistroObj(@Param("in")PersonaEntityMyBatis persona);
	*/
}
