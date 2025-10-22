package com.store.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/test")
public class TestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() {
        return Response.ok("{\"message\": \"API is working!\"}").build();
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Store API!";
    }
}