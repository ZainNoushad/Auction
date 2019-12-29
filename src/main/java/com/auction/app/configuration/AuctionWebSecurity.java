package com.auction.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import com.auction.app.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class AuctionWebSecurity extends WebSecurityConfigurerAdapter {


	@Autowired
	UserDetailsService userDetailsService;
	
//	@Bean
//	protected AuthenticationProvider authProvider() {
//		System.out.println("=======================================here");
//		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//		provider.setUserDetailsService(userDetailsService);
//		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//		return provider;
//		
//	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http
		.authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .successForwardUrl("/secret/index")
        .permitAll()
        .and()
        .rememberMe()
        .alwaysRemember(true)
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/")
        .and()
        .exceptionHandling().accessDeniedPage("/");
       
          
//                .antMatchers("**/secured/**").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin().permitAll();
		
	}
//	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web
        .ignoring()
           .antMatchers("/required/**")
           .antMatchers("/js/**")
           .antMatchers("/css/**")
           .antMatchers("/images/**")
           .antMatchers("/signUp")
           .antMatchers("/checkEmail")
           .antMatchers("/resetPassword")
           .antMatchers("/topic/**","/user/**");
	}
}