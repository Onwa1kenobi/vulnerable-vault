package io.ugochukwu.vulnerablevault.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class VulnerableVaultSpringMVCInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {VulnerableVaultSpringMVCConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {VulnerableVaultSpringSecurityInitialiser.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
}
	
}