package org.acme.service;
import io.quarkus.elytron.security.common.BcryptUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncodersService {

    public static String encodePassword(String password) {
        String additionalString = "";
        try {
            additionalString = new String(Files.readAllBytes(Paths.get("src/main/resources/cle.txt")));
        } catch (IOException e) {
            System.err.println("Une erreur s'est produite lors de la lecture du fichier : " + e.getMessage());
        }

        return BcryptUtil.bcryptHash( password + additionalString);
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        String additionalString = "";
        try {
            additionalString = new String(Files.readAllBytes(Paths.get("src/main/resources/cle.txt")));
        } catch (IOException e) {
            System.err.println("Une erreur s'est produite lors de la lecture du fichier : " + e.getMessage());
        }
        plainPassword = plainPassword + additionalString;

        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}
