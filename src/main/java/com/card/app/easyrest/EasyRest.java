package com.card.app.easyrest;

import javax.ws.rs.GET;

import javax.ws.rs.Path;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;




@Path("/pay")
public class EasyRest {

	
	private static final Logger log = Logger.getLogger(EasyRest.class);
	@GET
	@Path("/bill")
	public Response  getBill() {
		
		if(log.isDebugEnabled())
		{
			log.info("EasyRest  Invoked ");
		}
		return Response.status(200).entity("easy   bhai").build();
	}
}
