package online.hackpi.spring_boot.api.v1.auth.model.dto;

public record LoginDto(
        String userEmail,String password
) {
}
