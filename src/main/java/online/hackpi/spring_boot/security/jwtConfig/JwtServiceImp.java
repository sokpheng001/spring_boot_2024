package online.hackpi.spring_boot.security.jwtConfig;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import online.hackpi.spring_boot.api.v1.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImp {
    @Value(value = "${Application.jwt.secret-key}")
    private String secretKey;
    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }
    public boolean isTokenExpired(String token){
        return extractExpiration(token).after(new Date());
    }
    public boolean isValid(String token, UserDetails userDetails){
        String userEmail = extractUserEmail(token);
        return userEmail.equals(userDetails.getUsername()) && isTokenExpired(token);
    }
    public String extractUserEmail(String token) throws ExpiredJwtException {
     return extractClaims(token, Claims::getSubject);
    }
    public <T> T extractClaims(String token, Function<Claims , T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getKeyForSign())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String generateToken(User user){
        return Jwts
                .builder()
                .subject(user.getUserEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 60*1000)) // expired in 1 minute
                .signWith(getKeyForSign())
                .compact();
//        return
    }
    private SecretKey getKeyForSign(){
        byte [] keyBytes  = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
