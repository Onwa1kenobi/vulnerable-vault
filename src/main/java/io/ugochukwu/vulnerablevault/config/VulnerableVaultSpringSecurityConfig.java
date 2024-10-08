package io.ugochukwu.vulnerablevault.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class VulnerableVaultSpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private CustomCSRFFilter customCSRFFilter;
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		return new UserService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(customCSRFFilter, UsernamePasswordAuthenticationFilter.class) // Register the custom filter
        	.authorizeRequests()
                // Define which pages are accessible to which roles
                .antMatchers("/admin-dashboard/**").hasAuthority("ADMIN")
                .antMatchers("/teller-dashboard/**").hasAuthority("TELLER")
                .antMatchers("/customer-dashboard/**").hasAuthority("CUSTOMER")
        		.antMatchers("/", "/showLogin", "/showRegister", "/registerTheUser").permitAll() // Public pages
                .anyRequest().authenticated() // Any other request must be authenticated
            .and()
            .formLogin()
				.loginPage("/showLogin")
				.loginProcessingUrl("/authenticateTheUser")
				.defaultSuccessUrl("/")
                .failureUrl("/login-error")
                .permitAll()
            .and()
            	.logout().permitAll()
            .and()
            	.exceptionHandling()
                .accessDeniedPage("/access-denied")
            .and()
            .csrf().disable(); // Disable CSRF for simplicity, enable it in production
    }

	@Override
	public void configure(WebSecurity web) throws Exception {
		// Exclude static resources from security checks
		web.ignoring().antMatchers("/resources/**", "/static/**", "/webjars/**");
	}
}