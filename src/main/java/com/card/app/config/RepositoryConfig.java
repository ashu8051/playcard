package com.card.app.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableTransactionManagement
@Import(CachingConfig.class)
//@PropertySource(value = { "classpath:data/database.properties" })
public class RepositoryConfig {

	//@Autowired
	//private Environment environment;
	
	@Value("${hibernate.connection.driver_class}")     private String driverClassName;
    @Value("${hibernate.connection.url}")                 private String url;
    @Value("${hibernate.connection.username}")             private String username;
    @Value("${hibernate.connection.password}")             private String password;
    
    @Value("${hibernate.dialect}")         private String hibernateDialect;
    @Value("${hibernate.show_sql}")     private String hibernateShowSql;
    @Value("${hibernate.hbm2ddl.auto}") private String hibernateHbm2ddlAuto;
    @Value("hibernate.enable_lazy_load_no_trans")  private String lazyloadnotrans;
    @Value("hibernate.use_sql_comments")  private String usesqlcomments;
    //Eh Cache
    @Value("hibernate.current_session_context_class")  private String current_session_context_class;
    @Value("hibernate.cache.region.factory_class")  private String factory_class;
    @Value("hibernate.cache.use_second_level_cache")  private String use_second_level_cache;
    
    
	/* @Bean()    
	    public DataSource getDataSource()
	    {
	        DriverManagerDataSource ds = new DriverManagerDataSource();        
	        ds.setDriverClassName(environment.getRequiredProperty("hibernate.connection.driver_class"));
	        ds.setUrl( environment.getRequiredProperty("hibernate.connection.url"));
	        ds.setUsername(environment.getRequiredProperty("hibernate.connection.password"));
	        ds.setPassword(environment.getRequiredProperty("hibernate.connection.username") );        
	        return ds;
	    }*/
    
    @Bean()    
    public DataSource getDataSource()
    {
        DriverManagerDataSource ds = new DriverManagerDataSource();        
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        System.out.println("URLDATA  "+url+" username "+username+" password "+password);
        ds.setUsername(username.trim());
        ds.setPassword(password.trim());        
        return ds;
    }
	    
	    @Bean
	    @Autowired
	    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
	    {
	        HibernateTransactionManager htm = new HibernateTransactionManager();
	        htm.setSessionFactory(sessionFactory);
	        return htm;
	    }
	    
	    @Bean
	    @Autowired
	    public HibernateTemplate getHibernateTemplate(SessionFactory sessionFactory)
	    {
	        HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
	        return hibernateTemplate;
	    }
	        
	    @Autowired
	    @Bean(name = "sessionFactory")
	    public LocalSessionFactoryBean getSessionFactory()
	    {
	    	LocalSessionFactoryBean asfb = new LocalSessionFactoryBean();
	        asfb.setDataSource(getDataSource());
	        asfb.setHibernateProperties(getHibernateProperties());        
	        asfb.setPackagesToScan(new String[]{"com.card.app.model"});
	        return asfb;
	    }

	  /*  @Bean
	    public Properties getHibernateProperties()
	    {
	        Properties properties = new Properties();
	        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
	        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
	        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.format_sql"));
	        
	        return properties;
	    }*/
	    
	    @Bean
	    public Properties getHibernateProperties()
	    {
	        Properties properties = new Properties();
	        properties.put("hibernate.dialect", hibernateDialect);
	        properties.put("hibernate.show_sql", hibernateShowSql);
	        properties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);//
	        properties.put("hibernate.enable_lazy_load_no_trans", lazyloadnotrans);
	        properties.put("hibernate.use_sql_comments", usesqlcomments);
	        //properties.put("hibernate.current_session_context_class", current_session_context_class);
	        properties.put("hibernate.cache.region.factory_class", factory_class);
	        properties.put("hibernate.cache.use_second_level_cache", use_second_level_cache);
	        
	        return properties;
	    }
}
