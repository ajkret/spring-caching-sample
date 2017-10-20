package com.dersommer.sample.caching.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/country/")
public class QueryCountry {
    private static Logger LOGGER = LoggerFactory.getLogger(QueryCountry.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{country}")
    public Response getCountry(@PathParam("country") String country) {
        LOGGER.info("Received request for {}", country);

        return Response.ok().build();
    }
}
