package com.tasks.task_management.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {}//extends WebSecurityConfigurerAdapter {

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable() // Disable CSRF for simplicity, but consider enabling it
//				.authorizeRequests().antMatchers("/api/tasks/**").authenticated() // Ensure authentication for task
//																					// endpoints
//				.anyRequest().permitAll().and().httpBasic(); // Use Basic Auth for simplicity, consider using OAuth or
//																// JWT for production
//	}
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("user").password("{noop}password").roles("USER").and().withUser("admin")
//				.password("{noop}admin").roles("ADMIN");
//	}
//}
