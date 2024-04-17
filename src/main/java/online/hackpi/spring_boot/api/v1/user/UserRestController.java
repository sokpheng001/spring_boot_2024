package online.hackpi.spring_boot.api.v1.user;

import lombok.RequiredArgsConstructor;
import online.hackpi.spring_boot.api.v1.user.model.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserRestController {
    private final UserService userService;
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }
}
