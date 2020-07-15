package org.step.configuration.db;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

/*
Environment ->
BeanDefinition (Бежит по пакетам и собирает имена классов) ->
BeanFactory (Создает бины) ->
Inject beans (Внедрение зависимостей) ->
BeanPostProcessor.postProcessBeforeInitialization() (первичная настройка бина) ->
init method () ->
BeanPostProcessor.postProcessAfterInitialization() (создание proxy объектов, дополнение кода если нужно) ->
Context

Destroying context -> destroy method -> close context
 */
@Configuration
@ComponentScan(basePackages = {"org.step"})
@PropertySources({
        @PropertySource("classpath:db.properties")
})
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManager",
        basePackages = "org.step",
        transactionManagerRef = "transaction"
)
@EnableTransactionManagement
@EnableJpaAuditing
@EnableAsync
public class DatabaseConfiguration {

//    @Autowired
    private final Environment environment;

//    @Value("${db.url}")
//    private String url;

    @Autowired
    public DatabaseConfiguration(Environment environment) {
        this.environment = environment;
    }

    /*
    Scope - session, request, globalSession
     */
    @Bean("dataSource")
    @Scope("singleton")
    @Profile("production")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl(environment.getProperty("db.url"));
        dataSource.setUsername(environment.getProperty("db.username"));
        dataSource.setPassword(environment.getProperty("db.password"));
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("db.driver")));

        return dataSource;
    }

    @Bean("embeddedDataSource")
    @Profile("develop")
    public DataSource embeddedDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();

        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
//    @DependsOn(value = {"dataSource"})
    public LocalContainerEntityManagerFactoryBean entityManager(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();

        emFactory.setDataSource(dataSource);
        emFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emFactory.setJpaProperties(jpaProperties());
        emFactory.setPackagesToScan("org.step");

        return emFactory;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PlatformTransactionManager transaction(@Qualifier("entityManager") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    private Properties jpaProperties() {
        Properties jpaProperties = new Properties();

        jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        jpaProperties.setProperty("hibernate.show_sql", "true");
        jpaProperties.setProperty("hibernate.format_sql", "true");

        return jpaProperties;
    }

//    @Bean("customDataSource")
//    @Scope("prototype")
//    public DataSource customDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setUrl("bla");
//        dataSource.setUsername("bla");
//        dataSource.setPassword("bla");
//        dataSource.setDriverClassName("bla");
//
//        return dataSource;
//    }

//    @Autowired
//    public void setEnvironment(Environment environment) {
//        this.environment = environment;
//    }
}
