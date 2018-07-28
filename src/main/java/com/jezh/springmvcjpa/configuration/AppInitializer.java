package com.jezh.springmvcjpa.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

//	getRootConfigClasses() -- for "root" application context (non-web infrastructure) configuration.
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class };
	}

//	getServletConfigClasses() -- for DispatcherServlet application context (Spring MVC infrastructure) configuration.
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}
 
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
