package online.hackpi.spring_boot.api.v1.user;

import lombok.RequiredArgsConstructor;
import online.hackpi.spring_boot.api.v1.auth.model.dto.LoginDto;
import online.hackpi.spring_boot.api.v1.user.model.dto.UserDto;
import online.hackpi.spring_boot.api.v1.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserRestController {
    private final UserService userService;
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        System.out.println("Users found.!!!");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUserByUuid(@RequestParam(name = "id") String uuid){
        System.out.println("Users found by uuid: " + uuid);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserByUuid(uuid));
    }
    @PostMapping("/user")
    public ResponseEntity<UserDto> verifyUser(@RequestParam(name = "isVerified") String verify, @RequestBody LoginDto loginDto){
        System.out.println(loginDto.userEmail());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.verifyUser(Boolean.parseBoolean(verify), loginDto.userEmail()));
    }
}
