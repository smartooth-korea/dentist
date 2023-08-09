package co.smartooth.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 작성일 : 2022-07-18
 * 작성자 : 정주현
 * 기능 : Resources Path Mapping
 * */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		
		// 실제 사용하는 경로
		registry.addResourceHandler("/**")
        .addResourceLocations("classpath:/static/")
        .setCachePeriod(60*60*24);
		
		
		registry.addResourceHandler("/dentist/utils/css/**")
		.addResourceLocations("classpath:/static/css/")
		.setCachePeriod(60*60*24);
		
	
	}
	
	
}
