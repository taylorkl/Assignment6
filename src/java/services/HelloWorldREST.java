/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.math.BigDecimal;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author c0537794
 */
@Path("/hello")
@ApplicationScoped
public class HelloWorldREST {
    @Inject
    private HelloWorldController HelloWorldControllerList;
    
    @GET
    @Produces("application/json")
    public JsonObject hello() {
        JsonObjectBuilder json = Json.createObjectBuilder();
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (int i = 0; i < 100; i++) {
            JsonObjectBuilder subjson = Json.createObjectBuilder();
            for (int j = 0; j < 10; j++) {
                subjson.add("object" + 1 + "-" + j, i * j);
            }
            arr.add(subjson);
        }
        json.add("array", arr);
        return json.build();
    }  
    
}
