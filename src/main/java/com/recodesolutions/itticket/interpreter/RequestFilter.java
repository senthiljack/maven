package com.recodesolutions.itticket.interpreter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.recodesolutions.itticket.dto.RequestHeaderData;
import com.recodesolutions.itticket.entity.User;
import com.recodesolutions.itticket.mapper.AuthMapper;
import com.recodesolutions.itticket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Order(1)
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RequestFilter implements Filter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String TOKEN_TYPE = "Bearer";
    private final UserService userService;
    private final AuthMapper authMapper;
    @Resource(name = "requestScopeHeaderData")
    private RequestHeaderData requestHeaderData;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        log.info("Logging Request  {} : {}", req.getMethod(), req.getRequestURI());
        String path = req.getRequestURI().substring(req.getContextPath().length());
        Boolean skip=Boolean.TRUE;
if(path.contains("/api/")){
    skip=Boolean.FALSE;
}
        if(!skip){
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Map<String, Object> claims = jwt.getClaims();
            String email = claims.get("upn").toString();
            Optional<User> user = userService.findUserByEmail(email);
            Boolean isPresent = user.isPresent();
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (!isPresent) {
                String firstName = claims.get("given_name").toString();
                String lastName = claims.get("family_name").toString();
                String fullName = claims.get("name").toString();
                List<String> roles = new JsonMapper().readValue(claims.get("roles").toString(), new TypeReference<List<String>>() {
                });
                User newUser = userService.createUser(User.builder().email(email).firstName(firstName)
                        .lastName(lastName).fullName(fullName).build(), roles);
                authMapper.update(requestHeaderData, newUser);
                requestHeaderData.setToken(jwt.getTokenValue());
                newUser.getRoles().stream().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
            } else {
                User existingUser=user.get();
                authMapper.update(requestHeaderData, existingUser);
                List<String> roles = new JsonMapper().readValue(claims.get("roles").toString(), new TypeReference<List<String>>() {
                });
                List<String> existingRoles=existingUser.getRoles().stream().map(role-> role.getName()).collect(Collectors.toList());

                roles.forEach(role->{
                    authorities.add(new SimpleGrantedAuthority(role));
                });
                if(!roles.equals(existingRoles)){
                    userService.updateUserRoles(roles,existingUser);
                }
                requestHeaderData.setToken(jwt.getTokenValue());

            }
            Principal principal = new Principal() {
                @Override
                public String getName() {
                    return requestHeaderData.getFullName();
                }
            };

            Authentication authentication = new PreAuthenticatedAuthenticationToken(principal, requestHeaderData,
                    authorities);

            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

}
