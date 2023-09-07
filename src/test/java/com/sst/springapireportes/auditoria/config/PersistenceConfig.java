package com.sst.springapireportes.auditoria.config;

import com.sst.springapireportes.auditoria.auditor.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class PersistenceConfig {
  @Bean
  public AuditorAware<String> auditorProvider() {
    return new AuditorAwareImpl();
  }
}
