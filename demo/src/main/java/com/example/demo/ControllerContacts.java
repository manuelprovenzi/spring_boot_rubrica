package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ControllerContacts {
    
    //http://localhost:8080/addContact?token=mBux2PEdS7ZeIijRI2BhMBeOhFMgZZ3V9dJNliolN8o=&nome=arianna&cognome=sebellin&numero=123456&gruppo=ciao
    @GetMapping("/addContact")
    public Map<String,String> addContact(@RequestParam(value = "token", defaultValue = "") String token,
                            @RequestParam(value = "nome", defaultValue = "") String nome,
                            @RequestParam(value = "cognome", defaultValue = "") String cognome,
                            @RequestParam(value = "numero", defaultValue = "") String numero,
                            @RequestParam(value = "gruppo", defaultValue = "null") String gruppo) {
        
        Utente u = staticAttributes.utenteAttivo;
        
        if(u.getToken().equals(token)){
            Contatto c = new Contatto(nome, cognome, numero, gruppo);
            u.getRubrica().add(c);

            if(gestisciJson.updateUser(u)){
                Map<String, String> map = Map.of("status", "ok", "message", "Contatto aggiunto correttamente");
                return map;
            }
            else {
                Map<String, String> map = Map.of("status", "error", "message", "Errore nel sistema");
                return map;
            }
        }
        else {
            Map<String, String> map = Map.of("status", "error", "message", "Token non valido");
            return map;
        }
        
        
    }

    //http://localhost:8080/deleteContact?token=mBux2PEdS7ZeIijRI2BhMBeOhFMgZZ3V9dJNliolN8o=&id=1
    @GetMapping("/deleteContact")
    public Map<String,String> deleteContact(@RequestParam(value = "token", defaultValue = "") String token,
                                            @RequestParam(value = "id", defaultValue = "") int id){
        
        Utente u = staticAttributes.utenteAttivo;
        if(u.getToken().equals(token)){
            for(Contatto c : u.getRubrica()){
                if(c.getId()==id){
                    u.getRubrica().remove(c);
                    if(gestisciJson.updateUser(u)){
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
        else {
            Map<String, String> map = Map.of("status", "error", "message", "Token non valido");
            return map;
        }
    }

    //[null,"abc"]
    //http://localhost:8080/getGroups?token=mBux2PEdS7ZeIijRI2BhMBeOhFMgZZ3V9dJNliolN8o=
    @GetMapping("/getGroups")
    public Map<String,Object> getGroups(@RequestParam(value = "token", defaultValue = "") String token){
        Utente u = staticAttributes.utenteAttivo;
        if(u.getToken().equals(token)){

            Map<String, Object> map = Map.of("status", "ok", "result", u.getGruppi());
            return map;
        }
        else {
            Map<String, Object> map = Map.of("status", "error", "message", "Token non valido");
            return map;
        }
    }

   // [{"id":157,"nome":"aaa","cognome":"bbb","numero":"123456","gruppo":null},{"id":158,"nome":"manuel","cognome":"provenzi","numero":"3333","gruppo":null}]}
    //http://localhost:8080/getContacts?token=mBux2PEdS7ZeIijRI2BhMBeOhFMgZZ3V9dJNliolN8o=
    @GetMapping("/getContacts")
    public Map<String,Object> getContacts(@RequestParam(value = "token", defaultValue = "") String token){
        Utente u = staticAttributes.utenteAttivo;
        if(u.getToken().equals(token)){
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
        else {
            Map<String,Object> map = Map.of("status", "error", "message", "Token non valido");
            return map;
        }
    }
    
    
}
