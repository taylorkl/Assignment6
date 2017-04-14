/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


/**
 *
 * @author c0537794
 */
@ServerEndpoint("/socket")
@ApplicationScoped
public class HelloWorldWebSocket {
    
    @Inject
    HelloWorldController HelloWorldController;
    
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
     
     @OnMessage
     
     public void onMessage(String HelloWorldList, Session session) {
         try {
             String output = "";
             JsonObject json = Json.createReader(new StringReader(HelloWorldList)).readObject();
             if (json.containsKey("getAll")) {
                 output = HelloWorldController.getAllJson().toString();
             } else if (json.containsKey("getById")) {
                 output = HelloWorldController.getByIdJson(json.getInt("getById")).toString();
             } else if (json.containsKey("getFromTo")) {
                 JsonArray dates = json.getJsonArray("getFromTo");
                try {
                     output = HelloWorldController.getByDateJson(sdf.parse(dates.getString(0)),sdf.parse(dates.getString(1))).toString();
                 }catch (ParseException ex) {
                     output = Json.createObjectBuilder()
                             .add("error", "Error parsing dates: " + dates.toString())
                             .build().toString();
                 }
                 
             } else if (json.containsKey("post")) {
                 output = HelloWorldController.addJson(json.getJsonObject("post")).toString();
             } else if (json.containsKey("put")) {
                 int id = json.getJsonObject("put").getInt("id");
                 output = HelloWorldController.editJson(id, json.getJsonObject("put")).toString();
             } else if (json.containsKey("delete")) {
                 output = Json.createObjectBuilder()
                         .add("error", "Invalid Request")
                         .add("original", json)
                         .build().toString();
             }
             session.getBasicRemote().sendText(output);
         }catch (IOException ex) {
             Logger.getLogger(HelloWorldWebSocket.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

    @OnMessage
    public void onMessage() {
    }
}
