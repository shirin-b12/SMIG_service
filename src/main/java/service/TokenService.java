package service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import model.Utilisateurs;
import java.util.HashSet;
import java.util.List;


@ApplicationScoped
public class TokenService {


    public String generateToken(Utilisateurs user) {
        String role = user.id_role == 1 ? "Admin" : "User";
        return Jwt.issuer("https://localhost/issuer")
                .upn(String.valueOf(user.id_utilisateur))
                .groups(new HashSet<>(List.of(role)))
                .signWithSecret("NouvelleCleSecreteDe32CaracteresOuPlus");
    }
}