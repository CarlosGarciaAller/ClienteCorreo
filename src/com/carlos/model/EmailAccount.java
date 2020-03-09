package com.carlos.model;

import java.io.Serializable;

public class EmailAccount implements Serializable {
    String email;
    String pass;

    public EmailAccount(String email, String pass){
        this.email=email;
        this.pass=pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
