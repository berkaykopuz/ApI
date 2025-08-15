package net.kopuz.ApI.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.kopuz.ApI.entity.Role;
import net.kopuz.ApI.entity.User;
import net.kopuz.ApI.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Component
public class JWTService {
    private final UserRepository userRepository;

    public JWTService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validateToken(final String token){
        try{
            Jwts.parserBuilder().setSigningKey(System.getenv("SECRET")).build().parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String userName) {
        Optional<User> user = userRepository.findByUsername(userName);
        Optional<List<Role>> roles = Optional.ofNullable(user.get().getRoles());
        return createToken(userName, roles.get());
    }

    public String createToken(String username, List<Role> roles){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("authorities", roles.stream().map(Role::getRolename).collect(Collectors.toList()));

        Date issuedAt = new Date();
        Date validUntil = new Date(issuedAt.getTime() + 360000);

        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(validUntil)
                .signWith(SignatureAlgorithm.HS256, System.getenv("SECRET"))
                .compact();
    }

    public String getUsernameFromJWT(final String token){
        Claims claims = Jwts.parser()
                .setSigningKey(System.getenv("SECRET"))
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
