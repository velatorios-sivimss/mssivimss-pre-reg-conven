package com.imss.sivimss.arquetipo.service.impl;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.arquetipo.configuration.MyBatisConfig;
import com.imss.sivimss.arquetipo.configuration.mapper.Consultas;
import com.imss.sivimss.arquetipo.configuration.mapper.ConvenioPF;
import com.imss.sivimss.arquetipo.model.entity.DetalleConvenioPFXPersona;
import com.imss.sivimss.arquetipo.model.request.RequestFiltroPaginado;
import com.imss.sivimss.arquetipo.service.PreRegConvServiceNuevo;
import com.imss.sivimss.arquetipo.service.beans.BeanQuerys;
import com.imss.sivimss.arquetipo.utils.AppConstantes;
import com.imss.sivimss.arquetipo.utils.PaginadoUtil;
import com.imss.sivimss.arquetipo.utils.Response;

@Service
public class PreRegConvServiceNuevoImpl implements PreRegConvServiceNuevo {
	@Autowired
	private BeanQuerys query;

	@Autowired
	private MyBatisConfig myBatisConfig;
	
	@Autowired
	private PaginadoUtil paginadoUtil;
	
	private static final String ERROR = "ERROR"; 
	@Override
	public Response<Object> obtenerPreRegistros(RequestFiltroPaginado request) {
		Page<Map<String, Object>> objetoPaginado = paginadoUtil.paginado(request.getPagina(), request.getTamanio(), query.queryPreRegistros(request));
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, objetoPaginado);
	}

	@Override
	public Response<Object> obtenerPreRegistrosXPersona(Integer idFlujo,Integer idConvenioPf) {
		DetalleConvenioPFXPersona detalleConvenioPFModel = null;

		if ( idFlujo != null && (idFlujo>0 || idFlujo<=3) ){
			SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
			
			try(SqlSession session = sqlSessionFactory.openSession()) {
				ConvenioPF convenios = session.getMapper(ConvenioPF.class);
				try {
					switch (idFlujo) {
						
					case 2:
						detalleConvenioPFModel = convenios.consultaDetalleConvenioXEmpresa(idConvenioPf);
//						localhost:8080/mssivimss-pre-reg-conven/v1/sivimss/buscar/2/2
							break;
							
					case 3:
						detalleConvenioPFModel = convenios.consultaDetalleConvenioXPersona(idConvenioPf);
//						localhost:8080/mssivimss-pre-reg-conven/v1/sivimss/buscar/3/14
							break;
					
						default:
							break;
					}
					
					
						
				
					
				} catch (Exception e) {
					e.printStackTrace();
					return new Response<>(true, HttpStatus.OK.value(), ERROR, 0);
				}
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, detalleConvenioPFModel);
	}

}
