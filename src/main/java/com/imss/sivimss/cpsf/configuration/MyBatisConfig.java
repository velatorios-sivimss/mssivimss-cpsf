package com.imss.sivimss.cpsf.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.imss.sivimss.cpsf.configuration.mapper.Consultas;
import com.imss.sivimss.cpsf.configuration.mapper.PersonaMapper;
import com.imss.sivimss.cpsf.configuration.mapper.PlanSFPAMapper;
import com.imss.sivimss.cpsf.configuration.mapper.PagoLineaMapper;
import com.imss.sivimss.cpsf.configuration.mapper.PagoBitacoraMapper;
import com.imss.sivimss.cpsf.configuration.mapper.ConvenioPFMapper;
import com.imss.sivimss.cpsf.configuration.mapper.PagoDetalleMapper;
import com.imss.sivimss.cpsf.configuration.mapper.RenConvenioPFMapper;
import com.imss.sivimss.cpsf.configuration.mapper.BitacoraPAMapper;
import com.imss.sivimss.cpsf.configuration.mapper.PagoSFPAMapper;

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
	    configuration.addMapper(PlanSFPAMapper.class);
	    configuration.addMapper(PagoLineaMapper.class);
	    configuration.addMapper(PagoBitacoraMapper.class);
	    configuration.addMapper(ConvenioPFMapper.class);
	    configuration.addMapper(PagoDetalleMapper.class);
	    configuration.addMapper(RenConvenioPFMapper.class);
	    configuration.addMapper(BitacoraPAMapper.class);
	    configuration.addMapper(PagoSFPAMapper.class);
	    
	    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	    
	    
	    return builder.build(configuration);
	}
}
