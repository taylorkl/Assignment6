/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        try (Connection conn = messages.getConnection()) {
            HelloWorldList = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM messages");
            while (rs.next()) {
                HelloWorld h = new HelloWorld (
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getString("author"),
                rs.timeStamp("senttime")
                );
                HelloWorld.add(h);
            }
            for (HelloWorld h : HelloWorldList) {
            if (h.getId() == id) {
                return h;
        }

            }
        }catch (SQLException ex) {
            Logger.getLogger(HelloWorld.class.getName()).log(Level.SEVERE, null,ex);
            HelloWorld = new ArrayList<>();
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
   
    public final static String SALT = "THISISArandomSTRINGofCHARACTERSusedTOsaltTHEpasswords";
    
    /**
     * 
     * @param password
     * @return
     */
    public static String hash(String password) {
        try {
            String salted = password + SALT;
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] hash = md.digest(salted.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b: hash) {
                String hex = Integer.toHexString(b & 0xff).toUpperCase();
                if (hex.length()== 1) {
                    sb.append("0");
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(HelloWorldController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * 
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(HelloWorldController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        String hostname = "IPRO";
        String port = "3306";
        String dbname = "c0537794-java";
        String username = "c0537794-java";
        String password = "c0537794";
        String jdbc = String.format("jdbc:mysql://%s:%s/%s", hostname, port, dbname);
        return DriverManager.getConnection(jdbc, username, password);
    }
}
