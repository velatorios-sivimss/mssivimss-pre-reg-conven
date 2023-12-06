package com.imss.sivimss.arquetipo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.arquetipo.configuration.MyBatisConfig;
import com.imss.sivimss.arquetipo.configuration.mapper.Consultas;
import com.imss.sivimss.arquetipo.model.request.Paginado;
import com.imss.sivimss.arquetipo.service.PeticionesPreRegConv;
import com.imss.sivimss.arquetipo.service.beans.BeanQuerys;
import com.imss.sivimss.arquetipo.utils.AppConstantes;
import com.imss.sivimss.arquetipo.utils.PaginadoUtil;
import com.imss.sivimss.arquetipo.utils.Response;


import lombok.extern.java.Log;

@Log
@Service
public class ServiciosArquetipo implements PeticionesPreRegConv {

	@Autowired
	private BeanQuerys query;

	@Autowired
	private MyBatisConfig myBatisConfig;
	
	@Autowired
	private PaginadoUtil paginadoUtil;
	

	@Override
	public Response<Object> obtenerPreRegistros(Paginado paginado) {
		
		Page<Map<String, Object>> objetoPaginado = paginadoUtil.paginado(paginado.getPagina(), paginado.getTamanio(), query.queryPreRegistros());
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, objetoPaginado);
		
	}

	@Override
	public Response<Object>  obtenerPreRegistrosXEmpresa(Integer idPreReg) {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryPreRegistrosXEmpresa(idPreReg));
			} catch (Exception e) {
				e.printStackTrace();
				return new Response<>(true, HttpStatus.OK.value(), "ERROR", 0);
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}

	@Override
	public Response<Object>  obtenerPreRegistrosXPersona(Integer idPreReg) {
		
		List<Map<String, Object>> result = new ArrayList<>();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			try {
				result = consultas.selectNativeQuery(query.queryPreRegistrosXPersona(idPreReg));
			} catch (Exception e) {
				e.printStackTrace();
				return new Response<>(true, HttpStatus.OK.value(), "ERROR", 0);
			}
		}
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, result);
	}
}
