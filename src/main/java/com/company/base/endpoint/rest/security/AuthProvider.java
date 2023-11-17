package com.company.base.endpoint.rest.security;

import com.company.base.endpoint.rest.security.model.Principal;
import com.company.base.repository.WhistleblowerRepository;
import com.company.base.repository.model.Whistleblower;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
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
    // First authentication factor: token
    String providedToken = getToken(usernamePasswordAuthenticationToken);
    Whistleblower user;
    try {
      user = whistleblowerRepository.findByToken(providedToken);
    } catch (NoSuchElementException e) {
      throw new UsernameNotFoundException("Bad credentials");
    }

    // Second authentication factor: IP
    String providedIp = getIp(usernamePasswordAuthenticationToken);
    var userIp = user.getIp();
    if (!userIp.equals(providedIp)) {
      var warnMessage =
          String.format(
              "Bad ip! Token may be leaked! token=%s, ip=%s, providedIp:%s",
              providedToken, userIp, providedIp);
      logger.warn(warnMessage);
      throw new UsernameNotFoundException(warnMessage);
    }

    return new Principal(user, providedToken);
  }

  private String getToken(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
    var credentials = getCredentials(usernamePasswordAuthenticationToken);
    if (credentials == null) return null;
    return credentials.get("token");
  }

  private String getIp(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
    var credentials = getCredentials(usernamePasswordAuthenticationToken);
    if (credentials == null) return null;
    return credentials.get("ip");
  }

  private static Map<String, String> getCredentials(
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
    Object tokenObject = usernamePasswordAuthenticationToken.getCredentials();
    if (!(tokenObject instanceof Map)) {
      return null;
    }
    return (Map) tokenObject;
  }
}
