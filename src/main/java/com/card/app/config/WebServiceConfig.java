package com.card.app.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;


@EnableWs
@Configuration
@ComponentScan(basePackages="com.card.app")
public class WebServiceConfig extends WsConfigurerAdapter {

	private static final Logger log = Logger.getLogger(WebServiceConfig.class);
	/*@Bean
    public ServletRegistrationBean dispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        return new ServletRegistrationBean(servlet, "/services/*");
    }*/

    @Bean(name = "AccountDetailsService")
    public DefaultWsdl11Definition defaultWsdl11Definition() {

		if(log.isDebugEnabled())
		{
			log.info("WebServiceConfig  defaultWsdl11Definition Invoked");
		}
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AccountDetailsService");
        wsdl11Definition.setServiceName("AccountDetailsService");
        wsdl11Definition.setLocationUri("/endpoints");
       // CommonsXsdSchemaCollection  schemaCollection=new CommonsXsdSchemaCollection();
       // schemaCollection.setInline(true);
        //ClassPathResource  rcfg= new ClassPathResource("schemas/AccountDetailsServiceOperations.xsd");
       // Resource[]  rec={rcfg};
       // schemaCollection.setXsds(rec);
        //wsdl11Definition.setSchemaCollection(schemaCollection);
        wsdl11Definition.setTargetNamespace("http://com/card/app/webservices/accountservice");
        wsdl11Definition.setSchema(soap1());
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema soap1() {
        return new SimpleXsdSchema(new ClassPathResource("schemas/AccountDetailsServiceOperations.xsd"));
    }

}
