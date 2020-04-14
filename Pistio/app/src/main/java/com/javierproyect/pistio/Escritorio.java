package com.javierproyect.pistio;

public class Escritorio {
    String id;
    String usuario;
    String numero;
    String cantidad;
    String nticket;

    public Escritorio(String id, String usuario, String numero, String cantidad, String nticket) {
        this.id = id;
        this.usuario = usuario;
        this.numero = numero;
        this.cantidad = cantidad;
        this.nticket = nticket;
    }

    public String getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNumero() {
        return numero;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getNticket() {
        return nticket;
    }
}
