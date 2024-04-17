package online.hackpi.spring_boot.api.v1.auth.authBase;

import lombok.Builder;

@Builder
public record AuthToken(
        String token
) {
}
