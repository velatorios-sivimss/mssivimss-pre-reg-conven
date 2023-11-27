package com.imss.sivimss.arquetipo.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.imss.sivimss.arquetipo.configuration.mapper.Consultas;
import com.imss.sivimss.arquetipo.configuration.mapper.PersonaMapper;

@Service
public class MyBatisConfig {
	
	@Value("${spring.datasource.driverClassName}") 
	private String DRIVER;
	
	@Value("${spring.datasource.url}")
	private String URL;
	
	@Value("${spring.datasource.username}")
	private String USERNAME;
	
	@Value("${spring.datasource.password}")
	private String PASSWORD;
	
	@Value("${enviroment}")
	private String ENVIROMENT;
	
	public SqlSessionFactory buildqlSessionFactory() {
	    DataSource dataSource = new PooledDataSource(DRIVER, URL, USERNAME, PASSWORD);

	    Environment environment = new Environment(ENVIROMENT, new JdbcTransactionFactory(), dataSource);
	        
	    Configuration configuration = new Configuration(environment);
	    configuration.addMapper(Consultas.class);
	    configuration.addMapper(PersonaMapper.class);

	    
	    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	    
	    
	    return builder.build(configuration);
	}
}
