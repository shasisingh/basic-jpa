package com.shashi.takeaway.takeaway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class JpaConfiguration {

  public PlatformTransactionManager jpaTransactionManager() {
    return new JpaTransactionManager();
  }
}
