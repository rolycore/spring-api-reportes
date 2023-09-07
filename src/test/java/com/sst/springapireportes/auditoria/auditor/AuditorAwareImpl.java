package com.sst.springapireportes.auditoria.auditor;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || !authentication.isAuthenticated()) {
	    	return null;
	    }

	    return Optional.of(authentication.getName().toUpperCase());

    //return Optional.of("COD_USUARIO");
  }
}
