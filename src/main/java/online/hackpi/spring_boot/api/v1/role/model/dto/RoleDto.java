package online.hackpi.spring_boot.api.v1.role.model.dto;

import lombok.Builder;
@Builder
public record RoleDto(
    String roleName
) { }
