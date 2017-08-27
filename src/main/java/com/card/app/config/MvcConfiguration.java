package com.card.app.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jvnet.jax_ws_commons.spring.SpringService;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.card.app.aop.CardAspect;
import com.card.app.bo.ws.YupWS;
import com.card.app.bo.ws.impl.YupWSImpl;
import com.card.app.easyrest.EasyRest;
import com.card.app.ws.PlayWSService;
import com.card.app.ws.YupWSService;
import com.sun.xml.ws.transport.http.servlet.SpringBinding;




@Configuration
@ComponentScan(basePackages="com.card.app")
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableTransactionManagement
//@PropertySource(value = { "classpath:data/database.properties" })
@Import({
	AppSecurity.class ,
	RepositoryConfig.class,
	WebServiceConfig.class})
//@ImportResource("classpath:struts.xml")

public class MvcConfiguration extends WebMvcConfigurerAdapter{

	BasicDataSource appDataSource;
	/*@Autowired
	private Environment environment;
	@Autowired
	AnnotationSessionFactoryBean asfb;*/
	
	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}
	
	@Bean
	public YupWS getHelloWS()
	{
		return new YupWSImpl();
	}
	
	@Bean
	public EasyRest getEasyRest()
	{
		return new EasyRest();
	}
	
	@Bean(name="YupWSService")
	public YupWSService getHelloWSService()
	{
		return new YupWSService();
	}
	
	@Bean(name="PlayWSService")
	public PlayWSService getPlayWSService()
	{
		return new PlayWSService();
	}

	
/*	@Bean
	public TilesConfigurer tilesConfigurer(){
	    TilesConfigurer tilesConfigurer = new TilesConfigurer();//TilesDefinitionsConfig
	    tilesConfigurer.setDefinitions(new String[] {"/WEB-INF/tiles.xml"});
	    tilesConfigurer.setCheckRefresh(true);
	    return tilesConfigurer;
	}

	*/
	
	
	/*
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		TilesViewResolver viewResolver = new TilesViewResolver();
		registry.viewResolver(viewResolver);
	}*/
	@Bean
	public TilesViewResolver getTilesViewResolver() {
	    TilesViewResolver tilesViewResolver = new TilesViewResolver();
	    tilesViewResolver.setViewClass(TilesView.class);
	    tilesViewResolver.setOrder(1);
	    return tilesViewResolver;
	}
	
	 @Bean
	   public TilesConfigurer getTilesConfigurer() {
	 
	     TilesConfigurer tilesConfigurer = new TilesConfigurer();
	     tilesConfigurer.setDefinitionsFactoryClass(TilesDefinitionsConfig.class);
	     tilesConfigurer.setCheckRefresh(true);
	     TilesDefinitionsConfig.addDefinitions();
	 
	     return tilesConfigurer;
	   }
	 

	@Bean
	public CardAspect getCardAspect()

	{
		return new CardAspect();
	}
	 @Bean
	    public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer()
	    {
	        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
	        ppc.setLocation(new ClassPathResource("data/database.properties"));
	        ppc.setIgnoreUnresolvablePlaceholders(true);
	        return ppc;
	    }
	 
	 
	 @Bean
	 public SpringBinding bind() throws Exception {
	        SpringService springService = new SpringService();
	        springService.setBean(getHelloWSService());
	        SpringBinding binding = new SpringBinding();
	        binding.setService(springService.getObject());
	        binding.setUrl("/wsdata");
	        return binding;
	    }
	 
	 @Bean
	 public SpringBinding bind1() throws Exception {
	        SpringService springService = new SpringService();
	        springService.setBean(getPlayWSService());
	        SpringBinding binding = new SpringBinding();
	        binding.setService(springService.getObject());
	        binding.setUrl("/wsdata1");
	        return binding;
	    }
}
