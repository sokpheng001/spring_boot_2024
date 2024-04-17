package online.hackpi.spring_boot.api.v1.user.mapstruct;

import online.hackpi.spring_boot.api.v1.user.model.User;
import online.hackpi.spring_boot.api.v1.user.model.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;
;

@Mapper(componentModel = "spring")
public interface UserMapstruct{
    List<UserDto> mapFromUserToUserDto(List<User> userList);
    UserDto mapFromUserToUserDto(User user);
}
