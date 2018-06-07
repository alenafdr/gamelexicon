package ru.study.gamelexicon.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.study.gamelexicon.model.Role;
import ru.study.gamelexicon.model.User;
import ru.study.gamelexicon.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenUserDetailService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenUserDetailService.class);

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userService.findByToken(s);

        if (user == null){
            LOGGER.error("Token not found");
            throw new UsernameNotFoundException("Token not found");
        } else {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (Role role : user.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
        }

    }
}
