package com.imss.sivimss.preregconven.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.imss.sivimss.preregconven.configuration.mapper.ActualizaConvenioPFPersonaMapper;
import com.imss.sivimss.preregconven.configuration.mapper.Consultas;
import com.imss.sivimss.preregconven.configuration.mapper.ConvenioPA;
import com.imss.sivimss.preregconven.configuration.mapper.ConvenioPF;
import com.imss.sivimss.preregconven.configuration.mapper.Empresas;
import com.imss.sivimss.preregconven.configuration.mapper.Personas;

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
		configuration.addMapper(ConvenioPF.class);
		configuration.addMapper(ConvenioPA.class);
		configuration.addMapper(Empresas.class);
		configuration.addMapper(Personas.class);
		configuration.addMapper(ActualizaConvenioPFPersonaMapper.class);

		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

		return builder.build(configuration);
	}
}
