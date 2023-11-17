package com.company.base.endpoint.rest.security;

import com.company.base.endpoint.rest.security.model.Principal;
import com.company.base.repository.WhistleblowerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthProvider extends AbstractUserDetailsAuthenticationProvider {
  private final WhistleblowerRepository whistleblowerRepository;

  public AuthProvider(WhistleblowerRepository whistleblowerRepository) {
    this.whistleblowerRepository = whistleblowerRepository;
  }

  @Override
  protected void additionalAuthenticationChecks(
      UserDetails userDetails, UsernamePasswordAuthenticationToken token) {
    // nothing
  }

  @Override
  protected UserDetails retrieveUser(
      String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
    log.info("retrieving user");
    String token = getTokenFromHeader(usernamePasswordAuthenticationToken);
    try {
      var user = whistleblowerRepository.findByToken(token);
      return new Principal(user, token);
    } catch (Exception e) {
      throw new UsernameNotFoundException("Bad credentials");
    }
  }

  private String getTokenFromHeader(
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
    Object tokenObject = usernamePasswordAuthenticationToken.getCredentials();
    if (!(tokenObject instanceof String)) {
      return null;
    }
    return ((String) tokenObject).trim();
  }

  public static Principal getPrincipal() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();
    return (Principal) authentication.getPrincipal();
  }
}
