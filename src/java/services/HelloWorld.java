/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author c0537794
 */
public class HelloWorld {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    int id;
    String title;
    String contents;
    String author;
    Date senttime;

    
    public HelloWorld() {
       
    }

    public HelloWorld(JsonObject json) {
        id = json.getInt("id", 0);
        title = json.getString("title", "");
        contents = json.getString("contents", "");
        author = json.getString("author", "");
        String timeStr = json.getString("senttime", "");
        try {
            senttime = sdf.parse(json.getString("senttime"));
        } catch (ParseException ex) {
            Logger.getLogger(HelloWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JsonObject toJson() {
        String timeStr = sdf.format(senttime);
        return Json.createObjectBuilder()
                .add("id", id)
                .add("title", title)
                .add("contents", contents)
                .add("author", author)
                .add("senttime", timeStr)
                .build();
  
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getSenttime() {
        return senttime;
    }

    public void setSentTime(Date senttime) {
        this.senttime = senttime;
    }

}
