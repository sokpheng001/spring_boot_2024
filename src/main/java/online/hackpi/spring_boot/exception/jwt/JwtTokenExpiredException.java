package online.hackpi.spring_boot.exception.jwt;

public class JwtTokenExpiredException extends RuntimeException{
    public JwtTokenExpiredException(String message) {
        super(message);
    }
}
