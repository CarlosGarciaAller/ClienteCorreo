package com.carlos.model;

public class Email {
    private String email;
    private String asunto;
    private String contenido;

    public Email (String email, String asunto, String contenido){
        this.email = email;
        this.asunto = asunto;
        this.contenido = contenido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
