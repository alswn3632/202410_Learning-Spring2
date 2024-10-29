package com.ezen.spring.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages = {"com.ezen.spring.dao"})
@EnableTransactionManagement
public class RootConfig {
	// @Autowired : 해당 객체를 스프링 객체로 생성 (클래스 형태) 
	@Autowired
	ApplicationContext applicationContext;
	
	// DB에 관련된 설정
	// 사용자가 만든 객체를 스프링이 인지하도록 설정 => @Bean (메서드 형태)
	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		// com.mysql.cj.jdbc.Driver 대신 log4jdbc 드라이버를 사용할 것임.
		// log4jdbc 드라이버 : DB의 흐름을 로그로 찍어주는 드라이버.
		// springdb / springUser / mysql
		// https://log4jdbc.brunorozendo.com/ 에서 드라이버 이름과 URL 정보를 찾을 수 있다.
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/springdb2");
		hikariConfig.setUsername("springUser");
		hikariConfig.setPassword("mysql");
		
		// ------ Hikari 필수 설정 ------
		// 한번에 설정할 최대 커넥션 개수
		hikariConfig.setMaximumPoolSize(5);
		// 최소 유효 커넥션 수 (= 쉬고있는 커넥션 수, 비어있는 커넥션의 최소 수) 보통 max와 같은 개수로 설정
		hikariConfig.setMinimumIdle(5);
		// 첫 연결 시 test sql
		hikariConfig.setConnectionTestQuery("SELECT now()");
		// pool 이름 명명
		hikariConfig.setPoolName("springHikariCP");
		
		// ------ Hikari 추가 설정 ------
		// cachePrepStmts : cache 사용 여부 설정
		hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
		// mysql 드라이버가 연결당 cache 사이즈 설정 : 보통은 250~500 사이를 권장함
		hikariConfig.addDataSourceProperty("dataSource.prepStmtsCacheSize", "250");
		// connection당 캐싱할 PrepStmts 개수 지정 옵션 : default 250 / true : 기본값 설정 => 250
		hikariConfig.addDataSourceProperty("dataSource.prepStmtsCacheSqlLimit", "true");
		// mysql 서버에서 최신 이슈가 있을 경우 지원을 받을 것인지 설정
		hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
		
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		return hikariDataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean sqlFactoryBean = new SqlSessionFactoryBean();
		sqlFactoryBean.setDataSource(dataSource());
		// 내부 src/main/resources의 위치 값이 필요함!
		sqlFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/*.xml"));
		
		// DB : _(스네이크표기법) 사용 / java : 카멜표기법 reg_date(db) = regDate(java)
		sqlFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatisConfig.xml"));
		
		return sqlFactoryBean.getObject();
	}
	
	// 트랜잭션 매니저 설정
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
}
