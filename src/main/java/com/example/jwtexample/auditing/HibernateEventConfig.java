package com.example.jwtexample.auditing;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HibernateEventConfig {

  private final LocalContainerEntityManagerFactoryBean factoryBean;

  private final AuditLogListener logListener;

  @PostConstruct
  public void registerListeners() {
    log.info("Registering AuditLogListener - START");
    try {
      var emf = factoryBean.getNativeEntityManagerFactory();
      log.info("Got native EMF: {}", emf != null);

      var sessionFactory = emf.unwrap(org.hibernate.internal.SessionFactoryImpl.class);
      log.info("SessionFactory unwrapped successfully: {}", sessionFactory != null);

      var registry = sessionFactory.getServiceRegistry()
          .getService(org.hibernate.event.service.spi.EventListenerRegistry.class);

      registry.getEventListenerGroup(org.hibernate.event.spi.EventType.POST_INSERT)
          .appendListener(logListener);
      registry.getEventListenerGroup(org.hibernate.event.spi.EventType.POST_UPDATE)
          .appendListener(logListener);
      registry.getEventListenerGroup(org.hibernate.event.spi.EventType.PRE_UPDATE)
          .appendListener(logListener);

    } catch (Exception e) {
      log.error("Failed to register AuditLogListener: {}", e.getMessage());
      e.printStackTrace();
    }
  }
}
