package com.thechetankrishna.mysqlfailover.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager",
        basePackages = {"com.thechetankrishna.mysqlfailover.primaryRepository"}
)
public class PrimaryDatabaseConfiguration {

    @Primary
    @Bean(name = "primary_mysql_server")
    @ConfigurationProperties(prefix = "datasource.primary-mysql-server")
    public DataSource primaryMySQlServerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactoryBean(EntityManagerFactoryBuilder
                    entityManagerFactoryBuilder, @Qualifier("primary_mysql_server") DataSource primaryDatasource) {
        return entityManagerFactoryBuilder
                .dataSource(primaryDatasource)
                .packages("com.thechetankrishna.mysqlfailover.model")
                .persistenceUnit("primaryDB")
                .build();
    }

    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager primaryPlatformTransactionManager(@Qualifier("primaryEntityManagerFactory")
                                                                        EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
