package online.hackpi.spring_boot.exception;

import online.hackpi.spring_boot.exception.jwt.JwtTokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionConfig {
    @ResponseBody
    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<String> handleExpiredJwt(JwtTokenExpiredException e) {
        // Set appropriate HTTP status code (e.g., 401 Unauthorized)
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your token is expired");
    }
}
