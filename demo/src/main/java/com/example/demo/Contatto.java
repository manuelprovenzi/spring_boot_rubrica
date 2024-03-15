package com.example.demo;

public class Contatto {
    private int id;
    private String nome;
    private String cognome;
    private String numeroTelefono;
    private String gruppo;
    
    // Constructor
    public Contatto(String nome, String cognome, String numeroTelefono, String gruppo) {
        this.id = generateNextId(); // assegna il prossimo ID autoincrementale
        this.nome = nome;
        this.cognome = cognome;
        this.numeroTelefono = numeroTelefono;
        this.gruppo = gruppo;
    }
    
    // Metodo statico per generare il prossimo ID autoincrementale
    private static int generateNextId() {
        staticAttributes.lastId++; // incrementa l'ultimo ID assegnato
        return staticAttributes.lastId; // restituisce il nuovo ID
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCognome() {
        return cognome;
    }
    
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    
    public String getNumeroTelefono() {
        return numeroTelefono;
    }
    
    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }
    
    public String getGruppo() {
        return gruppo;
    }
    
    public void setGruppo(String gruppo) {
        this.gruppo = gruppo;
    }
}