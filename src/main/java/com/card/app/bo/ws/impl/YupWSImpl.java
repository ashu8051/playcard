package com.card.app.bo.ws.impl;

import javax.jws.WebService;

import com.card.app.bo.ws.YupWS;

@WebService(endpointInterface = "com.card.bo.ws.HelloWS")
public class YupWSImpl implements YupWS{

	
	@Override
	public String SayHello(String say) {
		// TODO Auto-generated method stub
		return say;
	}
}
