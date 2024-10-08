package io.ugochukwu.vulnerablevault.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class VulnerableVaultSpringSecurityInitialiser extends AbstractSecurityWebApplicationInitializer {

	private static final String TMP_FOLDER = "/tmp";
	private static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		insertFilters(servletContext, new MultipartFilter());
		
		ServletRegistration.Dynamic appServlet = servletContext.addServlet("mvc",
				new DispatcherServlet(new GenericWebApplicationContext()));

		appServlet.setLoadOnStartup(1);

		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_FOLDER, MAX_UPLOAD_SIZE,
				MAX_UPLOAD_SIZE * 2L, MAX_UPLOAD_SIZE / 2);

		appServlet.setMultipartConfig(multipartConfigElement);
	}
}