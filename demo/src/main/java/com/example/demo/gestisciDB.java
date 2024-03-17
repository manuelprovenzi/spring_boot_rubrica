package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class gestisciDB {
    Connection conn;

    public gestisciDB() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/db_rubrica","root","");//puo connettersi a qualsiasi db e file (cambierà la stringa) -> data source
    }
    /*
    gestisciDB() throws SQLException{
        //root è l'utente, l'altro è la password
        conn = DriverManager.getConnection("jdbc:mysql://localhost/test","root","");//puo connettersi a qualsiasi db e file (cambierà la stringa) -> data source

        Statement statement = conn.createStatement();//quando dobbiamo lanciare una query
        ResultSet rs = statement.executeQuery("SELECT id,nome as alias FROM utenti");
        //rs.next(); //true-> esiste un elemento successivo

        while(rs.next()){
            int id = rs.getInt(0); //0 è la colonna
            String user = rs.getString("alias"); //"alias" è la colonna 1 -> 2 modi
        }

        rs.close();
        statement.close();

        PreparedStatement stat2 = conn.prepareStatement("select * from utenti where username=? and password=md5(?)");
        stat2.setString(1, "mario");
        stat2.setString(2, "porta");
        ResultSet rs2 = stat2.executeQuery("SELECT id,nome as alias FROM utenti");

        while(rs2.next()){
            int id = rs2.getInt(0); //0 è la colonna
            String user = rs.getString("alias"); //"alias" è la colonna 1 -> 2 modi
        }
    }
    */
    public Utente searchUtente(String user, String psw) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("select * from utenti where username=? and password=md5(?)");
        stat.setString(1, user);
        stat.setString(2, psw);
        ResultSet rs = stat.executeQuery();
        if(rs.next()){
            Utente u = new Utente(rs.getString("username"),rs.getString("password"), rs.getString("token"), null);
            PreparedStatement statContatti = conn.prepareStatement("select * from contatti where usernameUtente=?");
            statContatti.setString(1, user);
            ResultSet rsContatti = statContatti.executeQuery();
            List<Contatto> contatti = new ArrayList<>();
            while(rsContatti.next()){
                Contatto c = new Contatto(rsContatti.getInt("id"),rsContatti.getString("nome"), rsContatti.getString("cognome"), rsContatti.getString("numero"), rsContatti.getString("gruppo"));
                contatti.add(c);
            }
            u.setRubrica(contatti);

            return u;
        }
        return null;
    }

    public Utente searchUtente(String token) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("select * from utenti where token=?");
        stat.setString(1, token);
        ResultSet rs = stat.executeQuery();
        if(rs.next()){
            Utente u = new Utente(rs.getString("username"),rs.getString("password"), rs.getString("token"), null);
            PreparedStatement statContatti = conn.prepareStatement("select * from contatti where usernameUtente=?");
            statContatti.setString(1, u.getUsername());
            ResultSet rsContatti = statContatti.executeQuery();
            List<Contatto> contatti = new ArrayList<>();
            while(rsContatti.next()){
                Contatto c = new Contatto(rsContatti.getInt("id"),rsContatti.getString("nome"), rsContatti.getString("cognome"), rsContatti.getString("numero"), rsContatti.getString("gruppo"));
                contatti.add(c);
            }
            u.setRubrica(contatti);

            return u;
        }
        return null;
    }

    public boolean creaContatto(String nome, String cognome, String numero, String gruppo, String username) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("insert into contatti (nome,cognome,numero,gruppo,usernameUtente) values (?,?,?,?,?)");
        stat.setString(1, nome);
        stat.setString(2, cognome);
        stat.setString(3, numero);
        stat.setString(4, gruppo);
        stat.setString(5, username);
        try {
            stat.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addUser(String user, String psw) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("insert into utenti (username,password) values (?,md5(?))");
        stat.setString(1, user);
        stat.setString(2, psw);
        try {
            stat.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String createToken(String user) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("update utenti set token=? where username=?");
        String token = generateToken();
        stat.setString(1, token);
        stat.setString(2, user);
        try {
            stat.execute();
            return token;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteContact(int id) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("delete from contatti where id=?");
        stat.setInt(1, id);
        try {
            stat.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private static String generateToken() {
        String token = UUID.randomUUID().toString();
        return token;
    }

}

/*
    TODO:
    -! static Utente searchContatto(String user, String psw)
    static Utente searchContatto(String token)
    -! static Utente addUser(String user, String psw)
    -! public static String generateToken()
 */