package com.card.app.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.xml.ws.transport.http.servlet.WSSpringServlet;







public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { SpringRootConfig.class};
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { MvcConfiguration.class};
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
	
 
  
   /* @Override
    protected WebApplicationContext createRootApplicationContext() {
        final WebApplicationContext context = super.createRootApplicationContext();
        final ConfigurableEnvironment env = (ConfigurableEnvironment) context.getEnvironment();
        final String profile = (String) env.getSystemProperties().get("env");
        env.setActiveProfiles(profile);
        return context;
    }*/
    
  
   @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    	super.registerDispatcherServlet(servletContext);
    	Dynamic rsDynamic=servletContext.addServlet("rsServlet", new SpringServlet());
    	rsDynamic.addMapping("/rsdata/*");
    	rsDynamic.setInitParameter("com.sun.jersey.config.property.packages", "com.card.rest");
    	rsDynamic.setLoadOnStartup(4);
    	customizeRegistration(rsDynamic);
    	
    	Dynamic wsDynamic=servletContext.addServlet("wsServlet", new WSSpringServlet());
    	wsDynamic.addMapping("/wsdata");
    	wsDynamic.addMapping("/wsdata1");
    	wsDynamic.setLoadOnStartup(3);
    	customizeRegistration(wsDynamic);
    	
    	servletContext.setInitParameter("resteasy.scan", "true");
    	//servletContext.setInitParameter("resteasy.resources", "com.card.easyrest.EasyRest");
    	servletContext.setInitParameter("resteasy.servlet.mapping.prefix", "/easydata");
    	Dynamic easyDynamic=servletContext.addServlet("easyServlet", new HttpServletDispatcher());
    	easyDynamic.addMapping("/easydata/*");
    	easyDynamic.setLoadOnStartup(2);
    	customizeRegistration(easyDynamic);
    	
    	MessageDispatcherServlet  msgServlet=new MessageDispatcherServlet(createServletApplicationContext());
    	msgServlet.setTransformWsdlLocations(true);
    	servletContext.setInitParameter("transformWsdlLocations", "true");
    	//servletContext.setInitParameter("contextConfigLocation", "SpringRootConfig.class");
    	Dynamic msgDynamic=servletContext.addServlet("msgServlet",msgServlet);
    	//msgDynamic.addMapping("*.wsdl");
    	msgDynamic.addMapping("/endpoints/*");
    	msgDynamic.setLoadOnStartup(1);
    	customizeRegistration(msgDynamic);
    	
       servletContext.addListener(new ContextLoaderListener(createServletApplicationContext()));
       servletContext.addListener(new ResteasyBootstrap());
       FilterRegistration.Dynamic filter = servletContext.addFilter(
               "SessionInViewFilter", new OpenSessionInViewFilter());
       servletContext.setInitParameter("sessionFactoryBeanName", "sessionFactory");
     /*  servletContext.addListener(new CompleteAutoloadTilesListener());
       
          
          servletContext.setInitParameter("actionPackages", "com.card.app.action");
          FilterRegistration.Dynamic filter = servletContext.addFilter(
                  "StrutsDispatcher", new StrutsPrepareAndExecuteFilter());
          filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/app/*");*/
       
    }
    
   /*  protected void addSpringServlet(final ServletContext servletContext, final String profile) {

        final DispatcherServlet dispatcherServlet = new DispatcherServlet(new GenericWebApplicationContext());

        final Dynamic dispatcherDynamic = servletContext.addServlet("dispatcherServlet", dispatcherServlet);;
        dispatcherDynamic.setLoadOnStartup(1);

        dispatcherDynamic.addMapping("/");

        servletContext
                .addFilter(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, DelegatingFilterProxy.class)
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
    }

    protected void addSpringJersyServlet(final ServletContext servletContext, final ConfigurableEnvironment env) {
        final ServletRegistration.Dynamic appServlet = servletContext.addServlet("jersey-servlet", new SpringServlet());

        //appServlet.addMapping("/restdata");
        appServlet.setInitParameter("com.sun.jersey.config.property.packages", "com.card.rest");
        //appServlet.setInitParameter(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
       // appServlet.setInitParameter(ResourceConfig.FEATURE_TRACE, env.getProperty("jersey.enable.trace", "false"));
      //  appServlet.setInitParameter(ResourceConfig.FEATURE_DISABLE_WADL,
          //      env.getProperty("jersey.disable.wadl", "true"));

      //  appServlet.setInitParameter(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS,..);
       // appServlet.setInitParameter(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, ..);

        appServlet.setLoadOnStartup(2);
        appServlet.addMapping("/v1/*");
    }*/
  
   /* @Override
    protected void registerContextLoaderListener(ServletContext servletContext) {
    	ContextLoaderListener  context=new ContextLoaderListener();
    	servletContext.addListener(context);
    	registerContextLoaderListener(servletContext);
    }*/
  /*  @Override
    protected void registerDispatcherServlet(ServletContext servletContext) {
    	// TODO Auto-generated method stub
    	
    	//RestServlet  servlet=new RestServlet();
    	//servletContext.addServlet("restServlet", servlet);
    	Dynamic dynamic=servletContext.addServlet("restServlet", new SpringServlet());
    	dynamic.addMapping("/restdata");
    	dynamic.setInitParameter("com.sun.jersey.config.property.packages", "com.card.rest");
    	dynamic.setLoadOnStartup(1);
    	customizeRegistration(dynamic);
    	registerDispatcherServlet(servletContext);
    }*/
    
    
}
