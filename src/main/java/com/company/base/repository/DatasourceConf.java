package com.company.base.repository;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DatasourceConf {

  private final String databaseUrl;
  private final String databaseUsername;
  private final String databasePassword;

  public DatasourceConf(
      @Value("${DATABASE_URL:${spring.datasource.url}}") String databaseUrl,
      @Value("${DATABASE_USERNAME:${spring.datasource.username}}") String databaseUsername,
      @Value("${DATABASE_PASSWORD:${spring.datasource.password}}") String databasePassword) {
    this.databaseUrl = databaseUrl;
    this.databaseUsername = databaseUsername;
    this.databasePassword = databasePassword;
  }

  @ConfigurationProperties(prefix = "datasource.postgres")
  @Bean
  @Primary
  public DataSource dataSource() {
    return DataSourceBuilder.create()
        .url(databaseUrl)
        .username(databaseUsername)
        .password(databasePassword)
        .build();
  }
}
