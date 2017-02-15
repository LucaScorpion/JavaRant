package com.scorpiac.javarant;

import com.google.gson.JsonObject;
import com.scorpiac.javarant.exceptions.AuthenticationException;
import org.apache.http.message.BasicNameValuePair;

class Auth {
    private String id;
    private String key;
    private String userId;

    Auth(String username, char[] password) throws AuthenticationException {
        JsonObject json = DevRant.post(DevRant.API_AUTH_TOKEN,
                new BasicNameValuePair("username", username),
                new BasicNameValuePair("password", String.valueOf(password))
        );

        // Clear the password.
        for (int i = 0; i < password.length; i++)
            password[i] = 0;

        if (!Util.jsonSuccess(json))
            throw new AuthenticationException();
        
        JsonObject token = json.get("auth_token").getAsJsonObject();
        id = token.get("id").getAsString();
        key = token.get("key").getAsString();
        userId = token.get("user_id").getAsString();
    }

    String getId() {
        return id;
    }

    String getKey() {
        return key;
    }

    String getUserId() {
        return userId;
    }
}
