/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author c0537794
 */
@ApplicationScoped
public class HelloWorldController {
    List<HelloWorld> list = new ArrayList<>();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    public HelloWorldController() {
    
    }
    
    public List<HelloWorld> getList() {
        return list;
    }

   

    

   
    
 
    
    
}
