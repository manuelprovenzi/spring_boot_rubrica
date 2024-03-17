package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class gestisciDB {
    Connection conn;

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
                Contatto c = new Contatto(rsContatti.getString("nome"), rsContatti.getString("cognome"), rsContatti.getString("telefono"), rsContatti.getString("gruppo"));
                contatti.add(c);
                //TODO:aggiungi Id nel contatto
            }
            u.setRubrica(contatti);

            return u;
        }
        return null;
    }

}

/*
    TODO:
    -! static Utente searchContatto(String user, String psw)
    static Utente searchContatto(String token)
    public static List<Map<String, Object>> parseJson(File jsonFile) throws Exception
    static Utente addUser(String user, String psw)
    public static String generateToken()
    private static void saveListToJsonFile(List<Map<String, Object>> list)
    public static boolean updateUser(Utente u)
 */