package online.hackpi.spring_boot.api.security;

import lombok.RequiredArgsConstructor;
import online.hackpi.spring_boot.api.v1.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsImp implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
//        System.out.println("From User Details: " + userEmail);
        return userRepository.findUserByUserEmail(userEmail)
                .orElseThrow(()->new UsernameNotFoundException("User is not found."));
    }
}
