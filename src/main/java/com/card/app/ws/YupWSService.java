package com.card.app.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.card.app.bo.ws.YupWS;



@WebService(name="YupWSService")
@SOAPBinding(style = Style.RPC)
public class YupWSService extends SpringBeanAutowiringSupport {

	@Autowired
	YupWS helloWs;
	
	/*@WebMethod(operationName="sayHello")
	public String sayHello(String say)
	{
		return helloWs.SayHello(say);
	}*/
	
	@WebMethod(operationName="sayYup")
	public String sayYup(String say)
	{
		return helloWs.SayHello(say);
	}
}
