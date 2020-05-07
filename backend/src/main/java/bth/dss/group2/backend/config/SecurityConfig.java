package bth.dss.group2.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable() // required for post method testing, remove this in production
				.httpBasic()
				.and()
				.authorizeRequests()
				.antMatchers("/index", "/").permitAll()
				.anyRequest().permitAll();
	}
}
