package bth.dss.group2.backend.config;

import bth.dss.group2.backend.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.ForwardLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomUserDetailsService customUserDetailsService;

	@Autowired
	public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf()
				.disable() // required for post method testing, remove this in production
				.httpBasic()
				.and()
				.authorizeRequests()
				.antMatchers("*.bundle.*", "/assets/**", "/index*", "/*", "/home*", "/api/login/authenticate", "/api/users/registerUser")
				.permitAll()
				.anyRequest()
				.authenticated();

	/*
				.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/api/login/authenticate")
				.successHandler(forwardAuthenticationSuccessHandler())
				.failureHandler(simpleUrlAuthenticationFailureHandler())
				.authenticationDetailsSource(webAuthenticationDetailsSource())
				.permitAll();

				.and()
				.sessionManagement()
				.invalidSessionUrl("/invalidSession.html")
				.maximumSessions(1).sessionRegistry(sessionRegistry()).and()
				.sessionFixation().none();*/
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(customUserDetailsService)
				.passwordEncoder(encoder());
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebAuthenticationDetailsSource webAuthenticationDetailsSource() {
		return new WebAuthenticationDetailsSource();
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public ForwardAuthenticationSuccessHandler forwardAuthenticationSuccessHandler() {
		return new ForwardAuthenticationSuccessHandler("/api/login/success");
	}

	@Bean
	public ForwardLogoutSuccessHandler forwardLogoutSuccessHandler() {
		return new ForwardLogoutSuccessHandler("/api/login/logout");
	}

	@Bean
	public SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
		return new SimpleUrlAuthenticationFailureHandler("/api/login/failure");
	}
}
