package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ControllerContacts {
    
    //http://localhost:8080/addContact?token=8b3d920b-ce4f-4eb4-8c16-9e102f8ec302&nome=arianna&cognome=sebellin&numero=123456&gruppo=ciao
    @GetMapping("/addContact")
    public Map<String,String> addContact(@RequestParam(value = "token", defaultValue = "") String token,
                            @RequestParam(value = "nome", defaultValue = "") String nome,
                            @RequestParam(value = "cognome", defaultValue = "") String cognome,
                            @RequestParam(value = "numero", defaultValue = "") String numero,
                            @RequestParam(value = "gruppo", required=false) String gruppo) {
        
                                
                                
        gestisciDB db = null;
        try {
            db = new gestisciDB();
        } catch (SQLException e) {
            e.printStackTrace();
            return Map.of("status", "error", "message", "Errore nel sistema");
        }
        Utente u=null;
        try {
            u = db.searchUtente(token);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(u==null){
            Map<String, String> map = Map.of("status", "error", "message", "Token non valido");
            return map;
        }

        boolean result = false;
        try {
            result = db.creaContatto(nome, cognome, numero, gruppo, u.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(result){
            Map<String, String> map = Map.of("status", "ok", "message", "Contatto aggiunto correttamente");
            return map;
        }
        else {
            Map<String, String> map = Map.of("status", "error", "message", "Errore nel sistema");
            return map;
        }
        
        
        
    }

    //http://localhost:8080/deleteContact?token=8b3d920b-ce4f-4eb4-8c16-9e102f8ec302&id=2
    @GetMapping("/deleteContact")
    public Map<String,String> deleteContact(@RequestParam(value = "token", defaultValue = "") String token,
                                            @RequestParam(value = "id", defaultValue = "") int id){
        
        gestisciDB db = null;
        try {
            db = new gestisciDB();
        } catch (SQLException e) {
            e.printStackTrace();
            return Map.of("status", "error", "message", "Errore nel sistema");
        }
        Utente u=null;
        try {
            u = db.searchUtente(token);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(u==null){
            Map<String, String> map = Map.of("status", "error", "message", "Token non valido");
            return map;
        }

        
        for(Contatto c : u.getRubrica()){
            if(c.getId()==id){
                boolean b = false;
                try {
                    b = db.deleteContact(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if(b){
                    u.deleteContact(c);
                    Map<String, String> map = Map.of("status", "ok", "result", "Contatto eliminato correttamente");
                    return map;
                }
                else {
                    Map<String, String> map = Map.of("status", "error", "message", "Errore nel sistema");
                    return map;
                }
            }
        }
        Map<String, String> map = Map.of("status", "error", "message", "Contatto non presente");
        return map;
        
    }

    //[null,"abc"]
    //http://localhost:8080/getGroups?token=8b3d920b-ce4f-4eb4-8c16-9e102f8ec302
    @GetMapping("/getGroups")
    public Map<String,Object> getGroups(@RequestParam(value = "token", defaultValue = "") String token){
        
        gestisciDB db = null;
        try {
            db = new gestisciDB();
        } catch (SQLException e) {
            e.printStackTrace();
            return Map.of("status", "error", "message", "Errore nel sistema");
        }
        Utente u=null;
        try {
            u = db.searchUtente(token);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(u==null){
            Map<String, Object> map = Map.of("status", "error", "message", "Token non valido");
            return map;
        }
        else {
            Map<String, Object> map = Map.of("status", "ok", "result", u.getGruppi());
            return map;
            
        }
        
    }

   // [{"id":157,"nome":"aaa","cognome":"bbb","numero":"123456","gruppo":null},{"id":158,"nome":"manuel","cognome":"provenzi","numero":"3333","gruppo":null}]}
    //http://localhost:8080/getContacts?token=8b3d920b-ce4f-4eb4-8c16-9e102f8ec302
    @GetMapping("/getContacts")
    public Map<String,Object> getContacts(@RequestParam(value = "token", defaultValue = "") String token){
        
        gestisciDB db = null;
        try {
            db = new gestisciDB();
        } catch (SQLException e) {
            e.printStackTrace();
            return Map.of("status", "error", "message", "Errore nel sistema");
        }
        Utente u=null;
        try {
            u = db.searchUtente(token);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(u==null){
            Map<String, Object> map = Map.of("status", "error", "message", "Token non valido");
            return map;
        }

        List<Map<String,String>> listMaps = new ArrayList<>();
        for(Contatto c : u.getRubrica()){
            
            Map<String,String> mapContatto = new HashMap<>();
            mapContatto.put("id", Integer.toString(c.getId()));
            mapContatto.put("nome", (c.getNome()));
            mapContatto.put("cognome", (c.getCognome()));
            mapContatto.put("numero", (c.getNumeroTelefono()));
            mapContatto.put("gruppo", (c.getGruppo()));
            listMaps.add(mapContatto);
        }
        
        Map<String,Object> map = Map.of("status", "ok", "result", listMaps);
        return map;
        
    }
    
    
}
