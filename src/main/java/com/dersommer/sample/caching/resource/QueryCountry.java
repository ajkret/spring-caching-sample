package com.dersommer.sample.caching.resource;

import com.dersommer.sample.caching.model.Country;
import com.dersommer.sample.caching.service.CountriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/country/")
public class QueryCountry {
    private static Logger LOGGER = LoggerFactory.getLogger(QueryCountry.class);

    @Autowired
    private CountriesService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{country}")
    public Response getCountry(@PathParam("country") String country) {
        LOGGER.info("Received request for {}", country);

        List<Country> countries = service.getCountry(country);

        return Response.ok().entity(countries).build();
    }
}
