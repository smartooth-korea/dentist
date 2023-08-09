package co.smartooth.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 */
@SpringBootApplication
@ComponentScan(basePackages = { "co.smartooth" })
public class AppApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return super.configure(builder);
	}

}
