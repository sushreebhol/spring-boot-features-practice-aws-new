package com.test.practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
//@ComponentScan(basePackages = "com.test.practice.bean")
public class SecurityConfiguration{
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.ignoringRequestMatchers("/saveMsg")
						.ignoringRequestMatchers("/data-api/**")
						.ignoringRequestMatchers("/public/**")
						.ignoringRequestMatchers("/api/**")
						.ignoringRequestMatchers("/eazyschool/actuator/**"))
						.authorizeRequests((requests) -> requests.requestMatchers("/dashboard").authenticated()
						.requestMatchers("/displayMessages/**").hasRole("ADMIN")
						.requestMatchers("/closeMsg/**").hasRole("ADMIN")
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/eazyschool/actuator/**").hasRole("ADMIN")
						.requestMatchers("/api/**").authenticated()
						.requestMatchers("/data-api/**").authenticated()
						.requestMatchers("/displayProfile").authenticated()
						.requestMatchers("/updateProfile").authenticated()
						.requestMatchers("/student/**").hasRole("STUDENT")
						//.requestMatchers("/profile/**").permitAll()
						.requestMatchers("", "/", "/home").permitAll()
						.requestMatchers("/holidays/**").permitAll()
						.requestMatchers("/contact").permitAll()
						.requestMatchers("/saveMsg").permitAll()
						.requestMatchers("/courses").permitAll()
						.requestMatchers("/about").permitAll()
						.requestMatchers("/assets/**").permitAll()
						.requestMatchers("/login").permitAll()
						.requestMatchers("/logout").permitAll()
						.requestMatchers("/public/**").permitAll())
						//.requestMatchers(PathRequest.toH2Console()).permitAll()
				.formLogin(loginConfigurer -> loginConfigurer.loginPage("/login")
						.defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll())
				.logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl("/login?logout=true")
						.invalidateHttpSession(true).permitAll())
				.httpBasic(Customizer.withDefaults());

		 /* antMatchers() is Depricated now
		 http.authorizeRequests(authorize -> authorize
						.antMatchers("/public").permitAll()
						.anyRequest().authenticated())
				.formLogin(withDefaults())
				.logout(withDefaults());*/

		/*http.headers(headers -> headers
						.frameOptions(frameOptions -> frameOptions.disable()
						)
		);*/
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
