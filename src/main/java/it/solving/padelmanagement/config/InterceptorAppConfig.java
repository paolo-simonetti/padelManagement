package it.solving.padelmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import it.solving.padelmanagement.interceptor.RoleInterceptor;

@Component
public class InterceptorAppConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private RoleInterceptor roleInterceptor;
		
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(roleInterceptor);
	}
	
}
