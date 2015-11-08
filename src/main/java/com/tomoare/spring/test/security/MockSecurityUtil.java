package com.tomoare.spring.test.security;

import com.google.common.collect.Sets;
import java.util.Set;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

/**
 *
 * @author tomoare
 */
public class MockSecurityUtil {

    private MockSecurityUtil() {
    }

    public static Set<? extends GrantedAuthority> toRoles(String role) {
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role);
        Set<SimpleGrantedAuthority> roles = Sets.newHashSet();
        roles.add(auth);
        return roles;
    }

    public static UsernamePasswordAuthenticationToken getPrincial(UserDetails user) {
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(
                        user,
                        user.getPassword(),
                        user.getAuthorities());
        return authentication;
    }

    public static MockHttpSession getMockHttpSessionAndSetSecurityContext(Authentication auth) {
        MockHttpSession session = new MockHttpSession();
        SecurityContext context = new SecurityContextImpl();
        context.setAuthentication(auth);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
        return session;
    }
}
