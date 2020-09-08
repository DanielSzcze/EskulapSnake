package pl.EskulapSnake.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.EskulapSnake.model.Role;
import pl.EskulapSnake.model.User;
import pl.EskulapSnake.repository.RoleRepository;
import pl.EskulapSnake.repository.UserRepository;

import java.util.*;

@Service("userDetailService")
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(name);
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    getGrantedAuthorities(Collections.emptyList()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),passwordEncoder.encode(user.getPassword()),user.isEnabled(),true,true,true,
                getGrantedAuthorities(user.getRoles())
        );
    }

    private List<? extends GrantedAuthority> getGrantedAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return authorities;
    }

}
