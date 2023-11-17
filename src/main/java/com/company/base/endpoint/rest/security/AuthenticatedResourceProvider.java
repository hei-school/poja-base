package com.company.base.endpoint.rest.security;

import com.company.base.repository.model.Whistleblower;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedResourceProvider {
  public Whistleblower getAuthenticatedUser() {
    return AuthProvider.getPrincipal().user();
  }
}
