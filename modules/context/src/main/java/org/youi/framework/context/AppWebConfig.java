/**
 * 
 */
package org.youi.framework.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * @author zhyi_12
 *RequestMappingHandlerMapping
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"org.youi.framework.core.web"})
@ImportResource("WEB-INF/configs/mvc/*.xml")
public class AppWebConfig extends WebMvcConfigurerAdapter {

	/**
	 * 配置jsp的view resolver
	 * @return
	 */
	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/pages/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(2);
		viewResolver.setViewClass(JstlView.class);
		return viewResolver;
	}
	

}
