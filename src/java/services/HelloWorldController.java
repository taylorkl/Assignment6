/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/**
 *
 * @author c0537794
 */
@ApplicationScoped
public class HelloWorldController {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    private List<HelloWorld> HelloWorldList;

    public HelloWorldController(){
        HelloWorldList = new ArrayList<>();
    }
    
    public JsonArray getAllJson() {
        JsonArrayBuilder json = Json.createArrayBuilder();
        for(HelloWorld h : HelloWorldList) {
            json.add(h.toJson());
        }
        return json.build();
    }
    
    public HelloWorld getById(int id) {
        for (HelloWorld h : HelloWorldList) {
            if (h.getId() == id) {
                return h;
            }
        }
        return null;
    }

    public JsonObject getByIdJson(int id) {
        HelloWorld h = getById(id);
        if (h != null) {
            return getById(id).toJson();
        }else {
            return null;
        }
    }
    
   public JsonArray getByDateJson(Date from, Date to) {
       JsonArrayBuilder json = Json.createArrayBuilder();
       for (HelloWorld h : HelloWorldList) {
           if((h.getSenttime().after(from) && h.getSenttime().before(to)) ||
                   h.getSenttime().equals(from) || h.getSenttime().equals(to)) {
               json.add(h.toJson());
           }
       }
       return json.build();
   } 
   
   public JsonObject addJson(JsonObject json) {
       HelloWorld h = new HelloWorld(json);
       HelloWorldList.add(h);
       return h.toJson();
   }
   
   public JsonObject editJson(int id, JsonObject json) {
       HelloWorld h = getById(id);
       h.setTitle(json.getString("title", ""));
       h.setContents(json.getString("contents", ""));
       h.setAuthor(json.getString("author", ""));
       String timeStr = json.getString("senttime", "");
       try {
           h.setSentTime(sdf.parse(timeStr));
       }catch (ParseException ex) {
           Logger.getLogger(HelloWorld.class.getName()).log(Level.SEVERE, null, "Failed Parsing Date: "+ timeStr);
       }
       return h.toJson();
   }

   public boolean deleteById(int id) {
       HelloWorld h = getById(id);
       if(h != null) {
           HelloWorldList.remove(h);
           return true;
       } else {
           return false;
       }
   }
}
