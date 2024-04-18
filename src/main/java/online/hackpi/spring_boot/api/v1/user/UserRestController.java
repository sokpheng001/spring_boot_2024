package online.hackpi.spring_boot.api.v1.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Retention;

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
    public ResponseEntity<?> getUserByUuid(@RequestParam(name = "id") String uuid){
        System.out.println("Users found.!!!");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserByUuid(uuid));
    }
}
