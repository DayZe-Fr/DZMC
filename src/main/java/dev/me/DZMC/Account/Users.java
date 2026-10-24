package dev.me.DZMC.Account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import dev.me.DZMC.Data;

import java.util.*;

public class Users {

    private static User _currentUser;
    public static User getCurrentUser() throws Data.DataLoadingException, JsonProcessingException {
        if (!loadedUsers) {_LoadUsers();}
        return _currentUser;
    }

    private static boolean loadedUsers = false;



    private static final HashMap<String, User> _loggedUsers = new HashMap<String, User>();


    private static void _LoadUsers() throws Data.DataLoadingException, JsonProcessingException {
        JsonNode usersNode = Data.getUsersPrefNode();
        Set<Map.Entry<String, JsonNode>> fields = usersNode.properties();

        for (Map.Entry<String, JsonNode> usr : fields) {
            User newusr = Data.mapper.treeToValue(usr.getValue(), User.class);
            _loggedUsers.put(usr.getKey(), newusr);

            System.out.println("User Key: " + usr.getKey());
            System.out.println("UUID: " + newusr.UUID());
            System.out.println("Username: " + newusr.username());
            System.out.println("User Type: " + newusr.userType());
            System.out.println("Access Token: " + newusr.accessToken());
            System.out.println("Language: " + newusr.lang());
            System.out.println("--------------------");
        }

        String currentlogged = Data.getGlobPrefNode().get("currentUser").asText();
        _currentUser = _loggedUsers.get(currentlogged);

        loadedUsers = true;
    }







    public record User (
        @JsonProperty("UUID")           String UUID,
        @JsonProperty("username")       String username,
        @JsonProperty("userType")       String userType,
        @JsonProperty("accessToken")    String accessToken,
        @JsonProperty("lang")           String lang
    ) {}
}
