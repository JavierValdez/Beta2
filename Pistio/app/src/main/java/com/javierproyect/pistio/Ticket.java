package com.javierproyect.pistio;


public class Ticket {

    String tipo;
    String id;
    String numero;
    String fechain;
    String fechafin;
    public Ticket(){

    }
    public Ticket(String Tipo, String id, String numero, String fechain, String fechafin) {
        this.tipo = Tipo;
        this.id = id;
        this.numero = numero;
        this.fechain = fechain;
        this.fechafin = fechafin;
    }


    public String getTipo() {
        return tipo;
    }

    public String getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public String getFechain() {
        return fechain;
    }

    public String getFechafin() {
        return fechafin;
    }
}
