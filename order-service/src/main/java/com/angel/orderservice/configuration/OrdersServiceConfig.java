package com.angel.orderservice.configuration;
import com.angel.kafkautils.consumer.IKafkaConsumerConfig;
import com.angel.kafkautils.consumer.KafkaConsumerConfigImpl;
import com.angel.kafkautils.producer.IKafkaProducerConfig;
import com.angel.kafkautils.producer.KafkaProducerConfigImpl;
import com.angel.saga.api.SagaOrchestration;
import com.angel.saga.impl.SagaOrchestrationImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.angel.orderservice.repos")
@EnableTransactionManagement
@PropertySource(value = {"application.yaml" })
public class OrdersServiceConfig {

    @Autowired
    Environment env;

    @Bean
    public ModelMapper createMapper() {

        return new ModelMapper();
    }

    @Bean
    public SagaOrchestration createSaga() {

        return new SagaOrchestrationImpl();
    }

    @Bean
    public IKafkaProducerConfig createProducer() {

        return new KafkaProducerConfigImpl();
    }

    @Bean
    public IKafkaConsumerConfig createConsumer() {

        return new KafkaConsumerConfigImpl();
    }

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
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
