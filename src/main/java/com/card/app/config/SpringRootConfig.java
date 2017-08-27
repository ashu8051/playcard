package com.card.app.config;

import javax.wsdl.extensions.schema.Schema;

import org.apache.ws.commons.schema.SchemaBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;


@Configuration
//@EnableWs
//@ComponentScan(basePackages="com.card")
//@ImportResource("classpath:AccountDetailsServiceOperations.xsd")
public class SpringRootConfig {

	/*@Bean
	public DefaultWsdl11Definition getDefaultWsdl11Definition()
	{
		DefaultWsdl11Definition    wsdlDef=new DefaultWsdl11Definition();
    	CommonsXsdSchemaCollection   schemaCollection=new CommonsXsdSchemaCollection();
    	schemaCollection.setInline(true);
    	Resource[]  rec={new ClassPathResource("classpath:AccountDetailsServiceOperations.xsd")};
    	schemaCollection.setXsds(rec);
    	wsdlDef.setSchemaCollection(schemaCollection);
		//wsdlDef.setSchema(new XsdSchema(AccountDetailsServiceOperations.xsd));
    	wsdlDef.setTargetNamespace("http://com/blog/samples/webservices/accountservice");
    	wsdlDef.setPortTypeName("AccountDetailsService");
    	wsdlDef.setServiceName("AccountDetailsService");
    	wsdlDef.setLocationUri("/endpoints");
    	return wsdlDef;
	}*/
}
