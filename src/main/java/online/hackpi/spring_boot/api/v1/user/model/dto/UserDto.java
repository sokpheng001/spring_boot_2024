package online.hackpi.spring_boot.api.v1.user.model.dto;

import lombok.Builder;
import online.hackpi.spring_boot.api.v1.role.model.dto.RoleDto;
import org.mapstruct.Mapper;

import java.util.Set;

@Builder
public record UserDto(
         String uuid,
         String userName,
         String email,
         Boolean isDeleted,
         Boolean isVerified,
         Set<RoleDto> roles
){}
