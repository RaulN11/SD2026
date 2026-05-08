package cars.microservice.UsersMicroservice.services;

import cars.microservice.UsersMicroservice.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${SECRET_KEY}")
    private String SECRET;
    @Value("${EXPIRATION_MS}")
    private long EXPIRATION;
    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }
    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }
    public String extractEmail(String token){
        return parseClaims(token).getSubject();
    }
    public boolean isTokenValid(String token){
        try{
            parseClaims(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }
    private Claims parseClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
