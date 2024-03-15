package com.example.demo;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class gestisciJson {

    static String path = System.getProperty("user.dir") + "/demo/src/main/resources/static/utenti.json";

    static Utente searchContatto(String user, String psw) {
        File jsonFile = new File(path);
        try {
            List<Map<String, Object>> utentiMap = parseJson(jsonFile);
            for (Map<String,Object> map : utentiMap) {
                if(map.get("username").equals(user) && map.get("password").equals(psw)) {
                    Utente u = new Utente((String)map.get("username"),(String)map.get("password"), (String)map.get("token"), (List<Contatto>)map.get("rubrica"));
                    return u;
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static Utente searchContatto(String token) {
        File jsonFile = new File(path);
        try {
            List<Map<String, Object>> utentiMap = parseJson(jsonFile);
            for (Map<String,Object> map : utentiMap) {
                if(map.get("token").equals(token)) {
                    Utente u = new Utente((String)map.get("username"),(String)map.get("password"), (String)map.get("token"), (List<Contatto>)map.get("rubrica"));
                    return u;
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Map<String, Object>> parseJson(File jsonFile) throws Exception {
        List<Map<String, Object>> userList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonFile);

        ArrayNode usersArray = (ArrayNode) rootNode;
        for (JsonNode userNode : usersArray) {
            Map<String, Object> userMap = new HashMap<>();
            String username = userNode.get("username").asText();
            String password = userNode.get("password").asText();
            String token = userNode.get("token").asText();
            userMap.put("username", username);
            userMap.put("password", password);
            userMap.put("token", token);

            List<Contatto> rubrica = new ArrayList<>();
            JsonNode rubricaNode = userNode.get("rubrica");
            if (rubricaNode != null) {
                ArrayNode contattiArray = (ArrayNode) rubricaNode.get("rubrica");
                if( contattiArray != null){
                    for (JsonNode contattoNode : contattiArray) {
                        String nome = contattoNode.get("nome").asText();
                        String numero = contattoNode.get("numero").asText();
                        String cognome = contattoNode.get("cognome").asText();
                        String gruppo = contattoNode.get("gruppo").asText();
                        Contatto contatto = new Contatto(nome, cognome, numero, gruppo);
                        rubrica.add(contatto);
                    }
                }
                
            }
            userMap.put("rubrica", rubrica);
            userList.add(userMap);
        }

        return userList;
    }

    static Utente addUser(String user, String psw) {
        try {
            List<Contatto> rubrica = new ArrayList<>();
            String token = generateToken();
            if(token==null){
                return null;
            }
            Utente u = new Utente(user, psw, token, rubrica);
            if(updateUser(u)){
                return u;
            }
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String generateToken() {
        String token = UUID.randomUUID().toString();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(token.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void saveListToJsonFile(List<Map<String, Object>> list) {

        try {
            // Configurazione dell'ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            File outputFile = new File(path);
            objectMapper.writeValue(outputFile, list);

            System.out.println("Lista salvata nel file: " + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean updateUser(Utente u) {
        try {
            File jsonFile = new File(path);
            List<Map<String, Object>> list = parseJson(jsonFile);
            Map<String, Object> attributiMap = new HashMap<>();
            attributiMap.put("username", u.getUsername());
            attributiMap.put("password", u.getPassword());
            attributiMap.put("token", u.getToken());
            attributiMap.put("rubrica", u.getRubrica());
            list.add(attributiMap);
            saveListToJsonFile(list);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

class ContattiWrapper {
    private List<Contatto> contatti;

    public List<Contatto> getContatti() {
        return contatti;
    }

    public void setContatti(List<Contatto> contatti) {
        this.contatti = contatti;
    }

    // Costruttore senza argomenti
    public ContattiWrapper() {
    }
}

/*
 *
 * u = new Utente(username, password, token, null);
 *
 * // Parsing dei contatti
 * JsonNode rubricaNode = userNode.get("rubrica");
 * List<Contatto> contattoList = new ArrayList<>();
 * if (rubricaNode != null) {
 * ArrayNode contattiArray = (ArrayNode) rubricaNode.get("contatti");
 * for (JsonNode contattoNode : contattiArray) {
 * String nome = contattoNode.get("nome").asText();
 * String telefono = contattoNode.get("telefono").asText();
 * String cognome = contattoNode.get("cognome").asText();
 * String gruppo = contattoNode.get("gruppo").asText();
 * Contatto contatto = new Contatto(nome, cognome, telefono, gruppo);
 * contattoList.add(contatto);
 * }
 * }
 * u.setRubrica(contattoList);
 */