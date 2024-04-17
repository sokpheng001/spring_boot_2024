package online.hackpi.spring_boot.api.v1.auth.model;

import lombok.RequiredArgsConstructor;
import online.hackpi.spring_boot.api.security.jwtConfig.JwtServiceImp;
import online.hackpi.spring_boot.api.v1.auth.authBase.AuthToken;
import online.hackpi.spring_boot.api.v1.auth.model.dto.LoginDto;
import online.hackpi.spring_boot.api.v1.user.model.User;
import online.hackpi.spring_boot.api.v1.user.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp {
    private final JwtServiceImp jwtServiceImp;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    public AuthToken login(LoginDto userData){
//        System.out.println("From Authentication Service Implement: " + userData);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userData.userEmail(),
                        userData.password()
                )
        );
        User user_ = userRepository.findUserByUserEmail(userData.userEmail()).orElseThrow();
//        System.out.println("User Data: " + user_);
        String token = jwtServiceImp.generateToken(user_);
        return AuthToken.builder()
                .accessToken(token)
                .build();
    }
}
