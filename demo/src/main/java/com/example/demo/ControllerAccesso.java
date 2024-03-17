package com.example.demo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ControllerAccesso {

    //Parametri GET:
    //- username ( string )
    //- password ( string )
    //http://localhost:8080/regist?username=manulp&password=aaa
    @GetMapping("/regist")
    public Map<String,String> getMethodName(@RequestParam(value = "username", defaultValue = "") String username,
                                @RequestParam(value = "password", defaultValue = "") String password) {
        
        //controlla se utente gia presente (status error), altrimenti lo registra
        gestisciDB db = null;
        try {
            db = new gestisciDB();
        } catch (SQLException e) {
            e.printStackTrace();
            return Map.of("status", "error", "message", "Errore nel sistema");
        }

        Utente u = null;
        try {
            u = db.searchUtente(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(u != null) {
            Map<String, String> map = Map.of("status", "error", "message", "Utente gia presente");
            return map;
        }
        else {
            boolean b = false;
            try {
                b = db.addUser(username, password);
            } catch (SQLException e) {

                e.printStackTrace();
            }
            if(b==false){
                Map<String, String> map = Map.of("status", "error", "message", "Errore nel sistema");
                return map;
            }
            Map<String, String> map = Map.of("status", "ok", "result", "Utente registrato correttamente");
            return map;
        }
        
        
    }

    //Parametri GET:
    //- username ( string )
    //- password ( string )
    //{"token":"e7c28cabf7e3b098489c4413ce0faba0"}
    //http://localhost:8080/getToken?username=manul&password=aaa
    @GetMapping("/getToken")
    public Map<String,Object> getToken(@RequestParam(value = "username", defaultValue = "") String username,
                            @RequestParam(value = "password", defaultValue = "") String password) {
        
        gestisciDB db = null;
        try {
            db = new gestisciDB();
        } catch (SQLException e) {
            e.printStackTrace();
            return Map.of("status", "error", "message", "Errore nel sistema1");
        }
        Utente u = null;
        try {
            u = db.searchUtente(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(u != null) {
            if(u.getToken()==null){
                String token = null;
                try {
                    token = db.createToken(u.getUsername());
                } catch (SQLException e) {
                    e.printStackTrace();
                    return Map.of("status", "error", "message", "Errore nel sistema2");
                }
                System.out.println(token);

                if(token!=null){
                    u.setToken(token);
                }
                else{
                    Map<String, Object> map = Map.of("status", "error", "message", "Errore nella generazione del token");
                    return map;
                }
            }
            Map<String,String> mapToken = new HashMap<>();
            mapToken.put("token", u.getToken());
            
            Map<String, Object> map = Map.of("status", "ok", "result",mapToken);
        
            
            
            return map;
        }
        else {
            Map<String, Object> map = Map.of("status", "error", "message", "Utente non presente");
            return map;
        }
        
                                
    }
    
    
}
