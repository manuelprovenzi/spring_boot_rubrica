package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebPagesController { //gestire le chiamate fatte per ritornare html e non json
    
    //@GetMapping("/hello") stessa cosa di questo sotto
    @GetMapping("/hello.html") 
    @ResponseBody //ritorniamo solo il corpo della risposta
    public String hello(@RequestParam(value="nome") String nome){
        //http://localhost:8080/hello.html?nome=pippo  
        return "<b>ciao"+nome+"</b>"; // ->pagina dinamica
    }

    //@GetMapping("/hello") stessa cosa di questo sotto
    @GetMapping("/hello2.html") 
    public String hellov2(@RequestParam(value="nome") String nome){
        //http://localhost:8080/hello2.html?nome=pippo  
        return "<b>ciao"+nome+"</b>"; // ->pagina dinamica
    }
}
