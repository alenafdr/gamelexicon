package ru.study.gamelexicon.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;
import ru.study.gamelexicon.dao.UserDao;
import ru.study.gamelexicon.model.Role;
import ru.study.gamelexicon.model.User;
import ru.study.gamelexicon.security.exceptions.TokenAuthenticationHeaderNotFound;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TokenAuthenticationFilter extends GenericFilterBean {



    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final String header;

    private final UserDao userDao;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint, String header, UserDao userDao) {
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.header = header;
        this.userDao = userDao;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String headerValue = "";
        try {
            headerValue = httpServletRequest.getHeader(header);
            if (headerValue == null || headerValue.isEmpty()) {
                logger.debug("Header " + header + " is not found.");
                throw new TokenAuthenticationHeaderNotFound("Header " + header + " is not found.", null);
            }
            
            User user = userDao.findByToken(headerValue);

            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (Role role : user.getRoles()){
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }

            Authentication authentication = authenticationManager.authenticate(new TokenAuthentication(headerValue, grantedAuthorities));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(servletRequest, servletResponse);

        } catch (AuthenticationException authenticationException) {
            /*if (!ignoreFault) {
                authenticationEntryPoint.commence(httpServletRequest, httpServletResponse, authenticationException);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }*/
            authenticationEntryPoint.commence(httpServletRequest, httpServletResponse, authenticationException);
        }


    }
}
