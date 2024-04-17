package online.hackpi.spring_boot.api.v1.user;

import lombok.RequiredArgsConstructor;
import online.hackpi.spring_boot.api.v1.user.model.User;
import online.hackpi.spring_boot.api.v1.user.model.dto.UserDto;
import online.hackpi.spring_boot.api.v1.user.model.dto.UserLoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserRestController {
    private final UserService userService;
    @PostMapping("/users")
    public ResponseEntity<User> addNewUser(@RequestBody User user){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.insertNewUser(user));
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }
    @PostMapping("/user/login")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginDto userLoginDto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userLoginDto);
    }
}
