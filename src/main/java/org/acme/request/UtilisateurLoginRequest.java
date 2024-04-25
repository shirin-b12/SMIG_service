package org.acme.request;

import io.quarkus.elytron.security.common.BcryptUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UtilisateurLoginRequest {
    String email;
    String mot_de_passe;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setPassword(String password) {

        this.mot_de_passe = password;
    }
}
