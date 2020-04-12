package com.javierproyect.pistio;

public class Usuario {
    String user;
    String id;
    String type;

    public Usuario() {

    }
    public Usuario(String user, String id, String type) {
        this.user = user;
        this.id = id;
        this.type = type;
    }

    public String getUser() {
        return user;
    }


    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
