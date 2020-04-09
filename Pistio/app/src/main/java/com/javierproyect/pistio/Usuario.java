package com.javierproyect.pistio;

public class Usuario {
    String User;
    String key;
    String id;
    String type;

    public Usuario(String user, String key, String id, String type) {
        User = user;
        this.key = key;
        this.id = id;
        this.type = type;
    }

    public String getUser() {
        return User;
    }

    public String getKey() {
        return key;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
