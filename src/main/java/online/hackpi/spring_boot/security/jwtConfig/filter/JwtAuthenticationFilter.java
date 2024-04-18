package online.hackpi.spring_boot.security.jwtConfig.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import online.hackpi.spring_boot.exception.jwt.JwtTokenExpiredException;
import online.hackpi.spring_boot.security.jwtConfig.JwtServiceImp;
import online.hackpi.spring_boot.security.UserDetailsImp;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.EOFException;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtServiceImp jwtServiceImp;
    private final UserDetailsImp userDetailsImp;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("URL: " + request.getRequestURL());
        String authHeader = request.getHeader("Authorization");
        if(authHeader==null || !authHeader.startsWith("Bearer ")){// don't for get the space
            System.out.println("Don't need to authenticate");
            filterChain.doFilter(request,response);
            return;
        }
        String token = authHeader.substring(7);
        try{
            String userEmail = jwtServiceImp.extractUserEmail(token);
            if(userEmail !=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails  = userDetailsImp.loadUserByUsername(userEmail);
                System.out.println(jwtServiceImp.isValid(token,userDetails));
                if(jwtServiceImp.isValid(token,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request,response);
                }
            }
        }catch (ExpiredJwtException exception){
            throw new JwtTokenExpiredException("Your token is expired.");
        }
    }
}
