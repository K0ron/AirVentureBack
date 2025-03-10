package com.keca.AirVentureBack.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.keca.AirVentureBack")
@EntityScan(basePackages = "com.keca.AirVentureBack")
public class JpaConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url(datasourceUrl)
            .username(datasourceUsername)
            .password(datasourcePassword)
            .driverClassName(driverClassName)
            .build();
    }

    @Bean(name = "emf")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(), null, null);
        return builder
                .dataSource(dataSource())
                .packages("com.keca.AirVentureBack.authentication.domain.entity",
                "com.keca.AirVentureBack.user.domain.entity",
                "com.keca.AirVentureBack.activity.domain.entity",
                "com.keca.AirVentureBack.reservation.domain.entity",
                "com.keca.AirVentureBack.upload.domain.entity",
                "com.keca.AirVentureBack.payment.domain.entity",
                "com.keca.AirVentureBack.token.domain.entity")  
                .persistenceUnit("default")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
