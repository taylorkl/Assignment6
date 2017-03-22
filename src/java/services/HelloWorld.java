/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author c0537794
 */
public class HelloWorld {
    int id;
    String title;
    String contents;
    String author;
    Date sentTime;
    
    public JsonObject getJson() {
        JsonObjectBuilder json = Json.createObjectBuilder();
            id = json.getInt("id"); 
            title = json.getString("title")
            contents = json.getString("contents")
            author = json.getString("author")
            sentTime = json.getDate("sentTime")
            .build();      
    }

    public HelloWorld(int id, String title, String contents, String author, Date sentTime) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.sentTime = sentTime;
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

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }
    
    
    

}
