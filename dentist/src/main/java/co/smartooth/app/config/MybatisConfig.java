package co.smartooth.app.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 기능 : Database 및 mapper 설정
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01 
 */
@Configuration
@MapperScan("co.smartooth.app.mapper")
@EnableTransactionManagement
public class MybatisConfig {

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*/*.xml"));

		// myBatis properties setting
		Properties mybatisProperties = new Properties();
		// CamelCase 자동맵핑
		mybatisProperties.setProperty("mapUnderscoreToCamelCase", "true"); 
		sessionFactory.setConfigurationProperties(mybatisProperties);

		return sessionFactory.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
		sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
		SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		return sessionTemplate;
	}
}
