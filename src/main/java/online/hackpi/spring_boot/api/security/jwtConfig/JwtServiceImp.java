package online.hackpi.spring_boot.api.security.jwtConfig;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import online.hackpi.spring_boot.api.v1.user.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtServiceImp {
    private final String secretKey = "2731a660061e5a77b1a4931282d6390fedd0a85cec135ad59e1e8f374fa3f726";
    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public boolean isValid(String token, UserDetails userDetails){
        String userEmail = extractUserEmail(token);
        return userEmail.equals(userDetails.getUsername()) && isTokenExpired(token);
    }
    public String extractUserEmail(String token){
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
        String token  = Jwts
                .builder()
                .subject(user.getUserEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                .signWith(getKeyForSign())
                .compact();
        return token;
//        return
    }
    private SecretKey getKeyForSign(){
        byte [] keyBytes  = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
