package com.bliffoscope.conf;

import java.io.File;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class WebAppInitializer implements WebApplicationInitializer{

	private static final String PACKAGE_WEB_CONFIGIRATION = "com.bliffoscope" ;
	
	private int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB
	 
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("Initializing Web Layer");
		
		WebApplicationContext applicationContext = getContext();
		
		servletContext.addListener(new ContextLoaderListener(applicationContext));
		
		ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("dispatcherServlet", new DispatcherServlet(applicationContext));
		dispatcherServlet.setLoadOnStartup(1);
		dispatcherServlet.addMapping("/");
		
		File uploadDirectory = new File(".");
		MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                        maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);
		
		dispatcherServlet.setMultipartConfig(multipartConfigElement);
		
	}

	private AnnotationConfigWebApplicationContext getContext(){
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation(PACKAGE_WEB_CONFIGIRATION);
		return context;
	}
}
