package com.imss.sivimss.preregconven.configuration.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imss.sivimss.preregconven.model.request.ActualizaConvenioPersonaPFDTO;

public interface ActualizaConvenioPFPersonaMapper {

        @Update(value = "UPDATE   SVC_PERSONA  " +
                        " SET   CVE_RFC  =  #{in.rfc}  ," +
                        " CVE_CURP  =  #{in.curp}  ," +
                        " NOM_PERSONA  =  #{in.nombre}  ," +
                        " NOM_PRIMER_APELLIDO  =  #{in.primerApe}  ," +
                        " NOM_SEGUNDO_APELLIDO  =  #{in.segunApe}  ," +
                        " NUM_SEXO  = #{in.idSexo}," +
                        " REF_OTRO_SEXO  = #{in.otroSexo}," +
                        " FEC_NAC  =  #{in.fechaNaciemiento} ," +
                        " ID_PAIS  = #{in.idPais}," +
                        " ID_ESTADO  = #{in.idEstado}," +
                        " REF_TELEFONO  =  #{in.telefono}  ,          " +
                        " REF_CORREO  =  #{in.correo} ," +
                        " FEC_ACTUALIZACION  = CURRENT_DATE()," +
                        " ID_USUARIO_MODIFICA  = #{in.idUsuario}         " +
                        " WHERE   ID_PERSONA   = #{in.idPersona}")
        public int actualizarPersona(@Param("in") ActualizaConvenioPersonaPFDTO parametros);

        @Update(value = "UPDATE SVT_DOMICILIO SET  " +
                        " REF_CALLE =  #{in.calle}  ," +
                        " NUM_EXTERIOR =  #{in.numExt}  ," +
                        " NUM_INTERIOR =  #{in.numInt}  ," +
                        " REF_CP =  #{in.cp}  ," +
                        " REF_COLONIA =  #{in.colonia}  ," +
                        " REF_MUNICIPIO =  #{in.municipio}  ," +
                        " REF_ESTADO =  #{in.estado}  ,   " +
                        " ID_USUARIO_MODIFICA =#{in.idUsuario}," +
                        " FEC_ACTUALIZACION = CURRENT_DATE()" +
                        " WHERE ID_DOMICILIO = #{in.idDomicilio} ")
        public int actualizaDomicilio(@Param("in") ActualizaConvenioPersonaPFDTO parametros);

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
                        + "REF_DOCUMENTO_BENEFICIARIO=#{in.documento}, "
                        + "ID_PARENTESCO=#{in.idParentesco} "
                        + "WHERE ID_CONTRATANTE_BENEFICIARIOS = #{in.idContratanteBeneficiario}  "
                        + " AND ID_PERSONA = #{in.idPersona}")
        public int actualizaDocumentosBeneficiario(@Param("in") ActualizaConvenioPersonaPFDTO parametros);

        @Update(value = " UPDATE   SVT_CONTRA_PAQ_CONVENIO_PF  " +
                        "SET " +
                        " IND_ENFERMEDAD_PREXISTENTE  =  #{in.idEnfermedad}," +
                        " REF_OTRA_ENFERMEDAD  =  #{in.otraEnfermedad}," +
                        " ID_PAQUETE  =  #{in.idPaquete}, " +
                        " ID_USUARIO_MODIFICA  =  #{in.idUsuario}, " +
                        " FEC_ACTUALIZACION  = CURRENT_DATE() " +
                        " WHERE   ID_CONTRA_PAQ_CONVENIO_PF  =  #{in.idContraPaqPF} " +
                        " AND ID_CONTRATANTE  =  #{in.idContrantante} " +
                        " AND ID_CONVENIO_PF = #{in.idConvenioPF} ")
        public int actualizaPaqueteConvenio(@Param("in") ActualizaConvenioPersonaPFDTO parametros);

        @Update(value = " UPDATE SVC_VALIDA_DOCS_CONVENIO_PF SET " +
                        " REF_UBICACION_CURP =   #{in.nombreCurp}  ," +
                        " REF_DOC_CURP_AFILIADO =  #{in.archivoCurp}," +
                        " ID_USUARIO_MODIFICA =  #{in.idUsuario}," +
                        " FEC_ACTUALIZACION = CURRENT_DATE()" +
                        " WHERE ID_VALIDACION_DOCUMENTO =  #{in.idValidaDocumento}" +
                        " AND  ID_CONTRA_PAQ_CONVENIO_PF =  #{in.idContraPaqPF}" +
                        " AND ID_CONVENIO_PF =  #{in.idConvenioPF}")
        public int actualizaCurp(@Param("in") ActualizaConvenioPersonaPFDTO parametros);

        @Update(value = " UPDATE SVC_VALIDA_DOCS_CONVENIO_PF SET " +
                        " REF_UBICACION_RFC =   #{in.nombreRfc}  ," +
                        " REF_DOC_RFC_AFILIADO =  #{in.archivoRfc} , " +
                        " ID_USUARIO_MODIFICA =  #{in.idUsuario}," +
                        " FEC_ACTUALIZACION = CURRENT_DATE()" +
                        " WHERE ID_VALIDACION_DOCUMENTO =  #{in.idValidaDocumento}" +
                        " AND  ID_CONTRA_PAQ_CONVENIO_PF =  #{in.idContraPaqPF}" +
                        " AND ID_CONVENIO_PF =  #{in.idConvenioPF}")
        public int actualizaRFC(@Param("in") ActualizaConvenioPersonaPFDTO parametros);

        @Update(value = " UPDATE SVC_VALIDA_DOCS_CONVENIO_PF SET " +
                        " REF_UBICACION_INE =   #{in.nombreIne}  ," +
                        " REF_DOC_INE_AFILIADO =  #{in.archivoIne}," +
                        " ID_USUARIO_MODIFICA =  #{in.idUsuario}," +
                        " FEC_ACTUALIZACION = CURRENT_DATE()" +
                        " WHERE ID_VALIDACION_DOCUMENTO =  #{in.idValidaDocumento}" +
                        " AND  ID_CONTRA_PAQ_CONVENIO_PF =  #{in.idContraPaqPF}" +
                        " AND ID_CONVENIO_PF =  #{in.idConvenioPF}")
        public int actualizaIne(@Param("in") ActualizaConvenioPersonaPFDTO parametros);

        @Update(value = " UPDATE SVC_CONTRATANTE" +
                        " SET ID_PERSONA = #{in.idPersona}," +
                        " CVE_MATRICULA = #{in.matricula}, " +
                        " FEC_ACTUALIZACION = CURRENT_DATE()," +
                        " ID_USUARIO_MODIFICA = #{in.idUsuario}" +
                        " WHERE ID_CONTRATANTE  = #{in.idContrantante}")
        public int actualizaContratante(@Param("in") ActualizaConvenioPersonaPFDTO parametros);

        @Update("UPDATE SVT_CONVENIO_PF SET ID_ESTATUS_CONVENIO = 1 , " +
                        "ID_USUARIO_MODIFICA= #{in.idUsuario}, " +
                        "FEC_ACTUALIZACION = CURRENT_DATE() " +
                        "WHERE ID_CONVENIO_PF = #{in.idConvenioPF} ")
        public int actualizarEstatusConvenioPF(@Param("in") ActualizaConvenioPersonaPFDTO parametros);

        @Update("UPDATE SVT_CONVENIO_PF SET ID_PROMOTOR = #{in.idPromotor} , " +
                        "ID_USUARIO_MODIFICA= #{in.idUsuario}, " +
                        "FEC_ACTUALIZACION = CURRENT_DATE() " +
                        "WHERE ID_CONVENIO_PF = #{in.idConvenioPF} ")
        public int actualizaPromotor(@Param("in") ActualizaConvenioPersonaPFDTO parametros);

        @Update("UPDATE   SVT_EMPRESA_CONVENIO_PF  " +
                        "SET REF_NOMBRE   = #{in.nombre}," +
                        " REF_RAZON_SOCIAL   = #{in.razonSocial}," +
                        " CVE_RFC   = #{in.rfc}," +
                        " ID_PAIS   = #{in.idPais}," +
                        " ID_DOMICILIO   = #{in.idDomicilio}," +
                        " REF_TELEFONO   = #{in.telefono}," +
                        " REF_CORREO   = #{in.correo} ," +
                        " ID_USUARIO_MODIFICA   = #{in.idUsuario}," +
                        " FEC_ACTUALIZACION   = CURRENT_DATE() " +
                        " WHERE    ID_EMPRESA_CONVENIO_PF   = #{in.idEmpresa} " +
                        " AND ID_CONVENIO_PF   = #{in.idConvenioPF}")
        public int actualizaPlanEmpresa(@Param("in") ActualizaConvenioPersonaPFDTO parametros);

        @Insert("INSERT INTO SVT_PAGO_BITACORA (ID_REGISTRO,ID_FLUJO_PAGOS,ID_VELATORIO,FEC_ODS,NOM_CONTRATANTE, \r\n"
                        + "                    CVE_FOLIO,IMP_VALOR,CVE_ESTATUS_PAGO,ID_USUARIO_ALTA) SELECT scp.ID_CONVENIO_PF as idConvenio,2,scp.ID_VELATORIO as velatorio,CURRENT_DATE(), s.REF_NOMBRE as nombreEmpresa, scp.DES_FOLIO as folio,sum(sp.MON_PRECIO) as total,2,#{in.idUsuario}\r\n"
                        + "FROM SVT_EMPRESA_CONVENIO_PF s  \r\n"
                        + "inner join SVT_CONVENIO_PF scp  on scp.ID_CONVENIO_PF  = s.ID_CONVENIO_PF  \r\n"
                        + "inner join SVT_CONTRA_PAQ_CONVENIO_PF scpcp on scpcp.ID_CONVENIO_PF = scp.ID_CONVENIO_PF  \r\n"
                        + "inner join SVT_PAQUETE sp on scpcp.ID_PAQUETE = sp.ID_PAQUETE  " +
                        " AND scp.ID_CONVENIO_PF   = #{in.idConvenioPF}")
        @Options(useGeneratedKeys = true, keyProperty = "in.idPagoBitacora", keyColumn = "ID_PAGO_BITACORA")
        public int insertPagoBitacora(@Param("in") ActualizaConvenioPersonaPFDTO parametros);

        @Insert("INSERT INTO SVT_PAGO_BITACORA (ID_REGISTRO,ID_FLUJO_PAGOS,ID_VELATORIO,FEC_ODS,NOM_CONTRATANTE, \r\n"
                        + "                    CVE_FOLIO,IMP_VALOR,CVE_ESTATUS_PAGO,ID_USUARIO_ALTA) \r\n"
                        + "SELECT scp.ID_CONVENIO_PF as idConvenio,2,scp.ID_VELATORIO as velatorio,CURRENT_DATE(), CONCAT(ps.NOM_PERSONA,' ',ps.NOM_PRIMER_APELLIDO,' ',ps.NOM_SEGUNDO_APELLIDO) \r\n"
                        + "as nombreContratante, scp.DES_FOLIO as folio,sum(sp.MON_PRECIO) as total,2,1\r\n"
                        + "from SVT_CONVENIO_PF scp  \r\n"
                        + "inner join SVT_CONTRA_PAQ_CONVENIO_PF scpcp on scpcp.ID_CONVENIO_PF = scp.ID_CONVENIO_PF  \r\n"
                        + "inner join SVC_CONTRATANTE sc on sc.ID_CONTRATANTE=scpcp.ID_CONTRATANTE\r\n"
                        + "inner join SVC_PERSONA ps on ps.ID_PERSONA=sc.ID_PERSONA\r\n"
                        + "inner join SVT_PAQUETE sp on scpcp.ID_PAQUETE = sp.ID_PAQUETE  "
                        + " AND scp.ID_CONVENIO_PF   = #{inp.idConvenioPF} ")
        @Options(useGeneratedKeys = true, keyProperty = "inp.idPagoBitacora", keyColumn = "ID_PAGO_BITACORA")
        public int insertPagoBitacoraPersona(@Param("inp") ActualizaConvenioPersonaPFDTO parametros);

        @Select(value = "SELECT * from " +
                        "   SVC_PERSONA where ID_PERSONA = #{idPersona} ")
        public Map<String, Object> buscarPersona(@Param("idPersona") Integer idPersona);

        @Insert(value = " INSERT INTO SVH_BITACORA (ID_TIPO_TRANSACCION,DES_TABLA, DES_DATO_AFECTADO, DES_DATO_ACTUAL, ID_USUARIO) values ("
                        +
                        " #{tipoTransaccion} ," +
                        " #{nombreTabla} ," +
                        " #{valorAnterior} ," +
                        " #{valorActual} ," +
                        " #{IdUsuario} " +
                        " )")

        public int bitacora(@Param("tipoTransaccion") Integer tipoTransaccion, @Param("nombreTabla") String nombreTabla,
                        @Param("valorAnterior") String valorAnterior,
                        @Param("valorActual") String valorActual, @Param("IdUsuario") Integer IdUsuario);

        @Select(value = "SELECT * from " +
                        "   SVT_DOMICILIO where ID_DOMICILIO = #{idDomicilio} ")
        public Map<String, Object> buscarDomicilio(@Param("idDomicilio") Integer idDomicilio);

        @Select(value = "SELECT * from " +
                        "   SVT_CONTRA_PAQ_CONVENIO_PF where ID_CONTRA_PAQ_CONVENIO_PF = #{idPaqueteContrato} ")
        public Map<String, Object> buscarContratoPaquete(@Param("idPaqueteContrato") Integer idPaqueteContrato);

        @Select(value = "SELECT * from " +
                        "   SVT_CONVENIO_PF where ID_CONVENIO_PF = #{idConvenio} ")
        public Map<String, Object> buscarConvenio(@Param("idConvenio") Integer idConvenio);

        @Select(value = "SELECT * from " +
                        "   SVC_VALIDA_DOCS_CONVENIO_PF where ID_VALIDACION_DOCUMENTO = #{idDocumento} ")
        public Map<String, Object> buscarDocumentos(@Param("idDocumento") Integer idDocumento);

        @Select(value = "SELECT * from " +
                        "   SVT_CONTRATANTE_BENEFICIARIOS where ID_CONTRATANTE_BENEFICIARIOS = #{idContratante} ")
        public Map<String, Object> buscarContratanteBeneficiario(@Param("idContratante") Integer idContratante);

        @Select(value = "SELECT * from " +
                        "   SVT_EMPRESA_CONVENIO_PF where ID_EMPRESA_CONVENIO_PF = #{idEmpresa} ")
        public Map<String, Object> buscarDatoEmpresa(@Param("idEmpresa") Integer idEmpresa);

}
