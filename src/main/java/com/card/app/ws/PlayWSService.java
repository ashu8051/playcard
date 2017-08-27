package com.card.app.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.springframework.beans.factory.annotation.Autowired;

import com.card.app.bo.ws.YupWS;

@WebService(name="PlayWSService")
@SOAPBinding(style = Style.RPC)
public class PlayWSService {

	@Autowired
	YupWS helloWs;
	
	@WebMethod(operationName="play")
	public String sayHello(String say)
	{
		return helloWs.SayHello(say);
	}
	
	/*@WebMethod(operationName="sayYup")
	public String sayYup(String say)
	{
		return helloWs.SayHello(say);
	}*/
}
