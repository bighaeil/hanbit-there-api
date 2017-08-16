package com.hanbit.there.api.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// @Configuration 설정클레스 명시
// @EnableAutoConfiguration 자동으로 설정되도록
// Transaction: DB에서 하나 기능을 수행하는 단위
// @EnableTransactionManagement 를 사용하려면 PlatformTransactionManager 가 필요
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
public class DatabaseConfig {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		// transactionManager 이름을 정확하게 써야 spring이 찾을 수 있다.
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception { // javax.sql.DataSource 라이브러리 조심
		// spring이 bean으로 등록 
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml")); // mybatis-config.xml의 위치 알려주기
		sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:mybatis/mappers/**/*.xml")); // mapper는 여러개니까 뒤에 s 붙는다.
		// /**/: 어떤 이름의 폴더든지 간에, *.xml: 모든 xml 파일
		return sqlSessionFactory.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) { // 파라미터 SqlSessionFactory을 spring이 Bean 중에 찾아서 넣는다.
		// 즉 Bean 중에 request, response, DataSource 등이 이미 있는 것
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
