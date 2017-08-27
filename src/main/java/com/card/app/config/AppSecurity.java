package com.card.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.card.app.security.CustomAuthenticationEntryPoint;
import com.card.app.security.CustomAuthenticationFailureHandler;
import com.card.app.security.CustomAuthenticationManager;
import com.card.app.security.CustomAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class AppSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	/*
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
                   .withUser("ashu").password("ashu").roles("USER");
	}

	//.csrf() is optional, enabled by default, if using WebSecurityConfigurerAdapter constructor
	@Override
	protected void configure(HttpSecurity http) throws Exception {

	    http.csrf().disable().authorizeRequests().antMatchers("/**").authenticated()
		.and()
		    .formLogin().loginPage("/login").permitAll().failureUrl("/login?error").defaultSuccessUrl("/user/index")
		    .usernameParameter("username").passwordParameter("password")
		.and()
		    .logout().logoutSuccessUrl("/login?logout")
		;
	}*/
	
	@Autowired
    @Qualifier("authenticationTokenProcessingFilter")
    private  UsernamePasswordAuthenticationFilter authenticationTokenProcessingFilter;

	@Autowired
	@Qualifier("authenticationManager")
	private CustomAuthenticationManager  authenticationManager;
	@Autowired
	@Qualifier("successHandler")
	private CustomAuthenticationSuccessHandler  successHandler;
	@Autowired
	@Qualifier("failureHandler")
	private CustomAuthenticationFailureHandler  failureHandler;
    @Autowired
    private  AuthenticationEntryPoint entryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(entryPoint)
        .and()
        .formLogin()
        .loginPage("/login")
        .successHandler(getCustomAuthenticationSuccessHandler())
        .failureHandler(getSimpleUrlAuthenticationFailureHandler())
        .permitAll()
        .and()
         .authorizeRequests() // use-expressions="true"
         .antMatchers("/home/login").permitAll() //<security:intercept-url pattern="/authenticate" access="permitAll" />
         .antMatchers("/home/logout").permitAll()
         //.antMatchers("/endpoints/*.wsdl").permitAll()
         .antMatchers("/").authenticated() //<security:intercept-url pattern="/secure/**"            access="isAuthenticated()" />
         .and()
          .addFilterBefore(getCustomAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // <security:custom-filter ref="authenticationTokenProcessingFilter" position="FORM_LOGIN_FILTER" /> http://docs.spring.io/spring-security/site/docs/3.0.x/reference/ns-config.html
          .exceptionHandling().accessDeniedPage("/home/login?denied")
          .and()
          .sessionManagement()
          .maximumSessions(3)
          .expiredUrl("/home/login?expire")
          .and()
          .invalidSessionUrl("/home/login")
          .and()
          .logout()
          .logoutUrl("/home/logout")
          .and()
          .authorizeRequests()
          .anyRequest()
          .authenticated()
          .and()
  		.rememberMe().tokenRepository(persistentTokenRepository())
  		.tokenValiditySeconds(1209600);;
    }
    
    @Bean(name="entryPoint")
    public CustomAuthenticationEntryPoint   getCustomAuthenticationEntryPoint()
    {
    	CustomAuthenticationEntryPoint  custEntryPoint=new CustomAuthenticationEntryPoint();
    	
    	
    	return custEntryPoint;
    }
    
    @Bean(name="authenticationTokenProcessingFilter")
    public  UsernamePasswordAuthenticationFilter  getCustomAuthenticationFilter()
    {
    	UsernamePasswordAuthenticationFilter   flt=new UsernamePasswordAuthenticationFilter();
    	flt.setAuthenticationManager(getCustomAuthenticationManager());
    	flt.setAuthenticationSuccessHandler(getCustomAuthenticationSuccessHandler());
    	flt.setAuthenticationFailureHandler(getSimpleUrlAuthenticationFailureHandler());
    	return flt;
    	
    	
    }
   
    @Bean(name="authenticationManager")
    public CustomAuthenticationManager   getCustomAuthenticationManager()
    {
    	CustomAuthenticationManager  cam=new CustomAuthenticationManager();
    	
    	return cam;
    }
    
    @Bean(name="successHandler")
    public CustomAuthenticationSuccessHandler  getCustomAuthenticationSuccessHandler()
    {
    	CustomAuthenticationSuccessHandler  cas=new CustomAuthenticationSuccessHandler();
    	
    	return cas;
    }
    
    @Bean(name="failureHandler")
    public CustomAuthenticationFailureHandler  getSimpleUrlAuthenticationFailureHandler()
    {
    	CustomAuthenticationFailureHandler  suaf=new CustomAuthenticationFailureHandler();
    	//suaf.setDefaultFailureUrl("/login?logout");
    	return suaf;
    }
    
    @Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
}
