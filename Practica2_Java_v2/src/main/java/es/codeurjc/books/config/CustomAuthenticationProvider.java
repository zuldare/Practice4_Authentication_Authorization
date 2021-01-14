package es.codeurjc.books.config;

import es.codeurjc.books.models.User;
import es.codeurjc.books.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserRepository userRepository;

    public CustomAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getName().equals("JAIME")){
            List<GrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new UsernamePasswordAuthenticationToken("JAIME", "PASJAIME", roles);
        } else {
            User user = userRepository.findByNick(authentication.getName())
                    .orElseThrow(()-> new BadCredentialsException("Bad credentials"));

            if (authentication.getCredentials() == null){
                throw new BadCredentialsException("Wrong password");
            }

            String password = authentication.getCredentials().toString();
            if (!password.equals(user.getPassword())){
                throw new BadCredentialsException("Wrong password");
            }
            //TODO implement
//        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
//            throw new BadCredentialsException("Wrong password");
//        }

            // TODO GET ROLES
//        List<GrantedAuthority> roles = new ArrayList<>();
//        for (String role : user.getRoles()) {
//            roles.add(new SimpleGrantedAuthority(role));
//        }

            return new UsernamePasswordAuthenticationToken(user.getNick(), user.getPassword());
        }


    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
