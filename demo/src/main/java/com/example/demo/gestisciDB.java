package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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

}

/*
    TODO:
    static Utente searchContatto(String user, String psw)
    static Utente searchContatto(String token)
    public static List<Map<String, Object>> parseJson(File jsonFile) throws Exception
    static Utente addUser(String user, String psw)
    public static String generateToken()
    private static void saveListToJsonFile(List<Map<String, Object>> list)
    public static boolean updateUser(Utente u)
 */