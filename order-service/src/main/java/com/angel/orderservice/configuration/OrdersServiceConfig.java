package com.angel.orderservice.configuration;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import com.angel.saga.configuration.ConsumerConfiguration;
import com.angel.saga.configuration.KafkaTopicConfig;
import com.angel.saga.configuration.ProducerConfiguration;
import com.angel.saga.impl.FactoryImpl;
import com.angel.saga.impl.SendMessageImpl;
import com.angel.swagger.config.SwaggerConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Objects;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.angel.orderservice.repos")
@EnableTransactionManagement
@PropertySource(value = {"classpath:./application.yaml" })
@EnableKafka
@Import({ConsumerConfiguration.class, KafkaTopicConfig.class, ProducerConfiguration.class,
         SwaggerConfig.class})
public class OrdersServiceConfig {

    @Autowired
    Environment env;

    @Bean
    public SendMessage sendMessage(){
        return new SendMessageImpl();
    }

    @Bean
    public ObjectMapper createObjectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper createMapper() {
        return new ModelMapper();
    }

    @Bean
    public Factory createFactory() {
        return new FactoryImpl();
    }

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(
            Objects.requireNonNull(env.getProperty("spring.datasource.driver-class-name")));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));

        return dataSource;

    }

    @Bean
    public EntityManager entityManager(){
        return entityManagerFactory().createEntityManager();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

        adapter.setDatabase(Database.MYSQL);
        adapter.setGenerateDdl(true);
        adapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        factory.setJpaVendorAdapter(adapter);

        factory.setPackagesToScan("com.angel.orderservice.models");

        factory.setDataSource(dataSource());

        Properties props = new Properties();

        props.setProperty("hibernate.ddl-auto", "validate");

        props.setProperty("hibernate.show_sql", "true");

        factory.setJpaProperties(props);

        factory.afterPropertiesSet();

        return factory.getObject();

    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory());

        return transactionManager;


    }
}
