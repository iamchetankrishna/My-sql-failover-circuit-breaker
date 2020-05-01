package com.thechetankrishna.mysqlfailover.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager",
        basePackages = {"com.thechetankrishna.mysqlfailover.secondaryRepository"}
)
public class SecondaryDatabaseConfiguration {

    @Bean(name = "secondary_mysql_server")
    @ConfigurationProperties(prefix = "datasource.secondary-mysql-server")
    public DataSource secondaryMySQlServerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactoryBean(EntityManagerFactoryBuilder
                  entityManagerFactoryBuilder, @Qualifier("secondary_mysql_server") DataSource secondaryDatasource) {
        return entityManagerFactoryBuilder
                .dataSource(secondaryDatasource)
                .packages("com.thechetankrishna.mysqlfailover.model")
                .persistenceUnit("secondaryDB")
                .build();
    }

    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryPlatformTransactionManager(@Qualifier("secondaryEntityManagerFactory")
                                                                                EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
