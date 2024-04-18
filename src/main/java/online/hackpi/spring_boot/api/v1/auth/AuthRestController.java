package online.hackpi.spring_boot.api.v1.auth;

import lombok.RequiredArgsConstructor;
import online.hackpi.spring_boot.api.v1.auth.model.AuthenticationServiceImp;
import online.hackpi.spring_boot.api.v1.auth.model.dto.LoginDto;
import online.hackpi.spring_boot.api.v1.user.UserService;
import online.hackpi.spring_boot.api.v1.user.model.User;
import online.hackpi.spring_boot.api.v1.user.model.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthenticationServiceImp authenticationServiceImp;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> addNewUser(@RequestBody User user){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.insertNewUser(user));
    }
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginDto userLoginDto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authenticationServiceImp.login(userLoginDto));
    }
}
