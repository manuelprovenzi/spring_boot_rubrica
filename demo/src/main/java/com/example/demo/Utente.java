package com.example.demo;

import java.util.ArrayList;
import java.util.List;


public class Utente {
    private String username;
    private String password;
    private String token; // New attribute
    
    private List<Contatto> rubrica;

    // Constructor
    public Utente(String username, String password, String token, List<Contatto> rubrica) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.rubrica = rubrica;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Contatto> getRubrica() {
        return rubrica;
    }

    public void setRubrica(List<Contatto> rubrica) {
        this.rubrica = rubrica;
    }

    public void deleteContact(Contatto c){
        this.rubrica.remove(c);
    }

    //[null,"abc"]
    public List<String> getGruppi(){
        List<String> gruppi = new ArrayList<>();
        for(Contatto c : rubrica){
            if(c.getGruppo()==null){
                c.setGruppo("null");
            }
            if(!gruppi.contains(c.getGruppo())){
                gruppi.add(c.getGruppo());
            }
        }
        

        return gruppi;
    }
}
