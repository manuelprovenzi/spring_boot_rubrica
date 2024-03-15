package com.example.demo;

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
    //http://localhost:8080/regist?username=manul&password=aaa
    @GetMapping("/regist")
    public Map<String,String> getMethodName(@RequestParam(value = "username", defaultValue = "") String username,
                                @RequestParam(value = "password", defaultValue = "") String password) {
        
        //controlla se utente gia presente (status error), altrimenti lo registra
        Utente u = gestisciJson.searchContatto(username, password);
        if(u != null) {
            Map<String, String> map = Map.of("status", "error", "message", "Utente gia presente");
            return map;
        }
        else {
            u = gestisciJson.addUser(username, password);
            if(u==null){
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
        
        
        Utente u = gestisciJson.searchContatto(username, password);
        if(u != null) {
            if(u.getToken()==null){
                u.setToken(gestisciJson.generateToken());
            }
            staticAttributes.token=u.getToken();
            staticAttributes.utenteAttivo= u;
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
