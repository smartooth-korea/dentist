package co.smartooth.app.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 */
@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:application.yml" })
public class DBConfig implements TransactionManagementConfigurer {
	
	Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${driver-class-name}")
    private String driverClassName;

    @Value("${db-url}")
    private String url;

    @Value("${db-username}")
    private String username;

    @Value("${db-password}")
    private String password;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSource dataSource() {
    	
    	logger.debug("============================== DB CONFIG ==============================");
    	logger.debug("============================== DRIVER CLASS NAME :: "+driverClassName+" ==============================");
    	logger.debug("============================== URL :: "+url+" ==============================");
    	logger.debug("============================== USER NAME :: "+username+" ==============================");
    	
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}