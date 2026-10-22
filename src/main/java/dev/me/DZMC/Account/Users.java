package dev.me.DZMC.Account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Users {

    private User _currentUser;

    public User getCurrentUser() {
        return _currentUser;
    }

//    public loadUsers






    public record User (
        @JsonProperty("UUID")           String UUID,
        @JsonProperty("username")       String username,
        @JsonProperty("userType")       String userType,
        @JsonProperty("accessToken")    String accessToken
    ) {}
}
