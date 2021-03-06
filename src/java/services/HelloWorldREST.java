/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


/**
 *
 * @author c0537794
 */
@Path("/services")
@ApplicationScoped
public class HelloWorldREST {
    @Inject
    private HelloWorldController helloWorldController;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    
    @GET
    @Produces("application/json")
    public Response getAll() {
        return Response.ok(helloWorldController.getAllJson()).build();
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById (@PathParam("id") int id) {
        JsonObject json = helloWorldController.getByIdJson(id);
        if(json != null) {
            return Response.ok(json).build();   
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("{from}/{to}")
    @Produces("application/json")
    public Response getByDate(@PathParam("from") String fromStr, @PathParam("to") String toStr) {
        try {
            return Response.ok(helloWorldController.getByDateJson(sdf.parse(fromStr), sdf.parse(toStr))).build();
        }catch (ParseException ex){
            Logger.getLogger(HelloWorldREST.class.getName()).log(Level.SEVERE, null, ex); 
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
     
     @POST
     @Consumes("application/json")
     @Produces("application/json")
     public Response add(JsonObject json) {
        return Response.ok(helloWorldController.addJson(json)).build();
    
    }
        
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response edit(@PathParam("id") int id, JsonObject json) {
        JsonObject jsonWithId = helloWorldController.editJson(id, json);
        if (jsonWithId != null) {
            return Response.ok(jsonWithId).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
   
    @DELETE
    @Path("{id}")
    public Response del(@PathParam("id") int id) {
        if (helloWorldController.deleteById(id)) {
            return Response.ok().build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    }
