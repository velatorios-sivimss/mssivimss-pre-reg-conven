package com.imss.sivimss.arquetipo.utils;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.imss.sivimss.arquetipo.configuration.MyBatisConfig;
import com.imss.sivimss.arquetipo.configuration.mapper.Consultas;
import com.imss.sivimss.arquetipo.model.entity.DatosConvenio;
import com.imss.sivimss.arquetipo.service.beans.BeanQuerys;

import lombok.extern.java.Log;

@Log
@Service
public class PaginadoUtil {
	private static final Logger log = LoggerFactory.getLogger(BeanQuerys.class);

	@Autowired
	private MyBatisConfig myBatisConfig;
	
	public Page<Map<String, Object>> paginado(Integer pagina, Integer tamanio, String query){
		
		Page<Map<String, Object>> objetoMapeado = null;
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		String queryPage = query + " LIMIT " + (pagina*tamanio) + ", " + tamanio;
		String queryConteo = "SELECT COUNT(*) AS conteo FROM (" + query + ") tem ";
		List<Map<String, Object>> resp;
		List<Map<String, Object>> respTotal;
		Pageable pageable = PageRequest.of(pagina, tamanio);
		
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			
			Consultas consultas = session.getMapper(Consultas.class);
			resp = consultas.selectNativeQuery(queryPage);
			respTotal = consultas.selectNativeQuery(queryConteo);
			
			Integer conteo =  Integer.parseInt( respTotal.get(0).get("conteo").toString() );
			objetoMapeado = new PageImpl<>(resp, pageable, conteo);
		}
		
		return objetoMapeado;
		
	}
	
	public Page<Map<String, DatosConvenio>> paginadoConvenio(Integer pagina, Integer tamanio, String query){
		
		Page<Map<String, DatosConvenio>> objetoMapeado = null;
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		String queryPage = query + " LIMIT " + (pagina*tamanio) + ", " + tamanio;
		String queryConteo = "SELECT COUNT(*) AS conteo FROM (" + query + ") tem ";
		List<Map<String, DatosConvenio>> resp;
		List<Map<String, Object>> respTotal;
		Pageable pageable = PageRequest.of(pagina, tamanio);
		
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			
			try {
				Consultas consultas = session.getMapper(Consultas.class);
				resp = consultas.selectNativeQueryConvenios(queryPage);
				respTotal = consultas.selectNativeQuery(queryConteo);
				
				

				Integer conteo =  Integer.parseInt( respTotal.get(0).get("conteo").toString() );
				objetoMapeado = new PageImpl<>(resp, pageable, conteo);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return objetoMapeado;
		
	}
	
}
