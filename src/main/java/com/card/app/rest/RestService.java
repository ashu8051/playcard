package com.card.app.rest;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

@Component
@Path("/rest")
public class RestService {

	@GET
	@Path("/payment")
	public Response savePayment() {

		//String result = transactionBo.save();

		return Response.status(200).entity("This is Rest Service").build();

	}
	
	@GET
	@Path("/mat")
	public Response getMatixValue(@PathParam("year") String year,@MatrixParam("author") String author ,@HeaderParam("user-agent") String userAgent)
	{
		return Response.status(200).entity("getMatixValue"+" author "+author+" userAgent "+userAgent).build();
	}
}
