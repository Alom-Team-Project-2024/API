package com.example.user.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
//        entityManagerFactoryRef = "mysqlEntityManagerFactory",
//        transactionManagerRef = "mysqlTransactionManager",
        basePackages = {
                "com.example.user.userdomain.repository",
                "com.example.user.common.repository",
                "com.example.user.boarddomain.mentordomain.repository",
                "com.example.user.boarddomain.questiondomain.repository",
                "com.example.user.chatdomain.repository.mysql"
        })
public class MySQLConfig {

//    @Bean(name = "mysqlDataSource")
//    public DataSource mysqlDataSource() {
//        return DataSourceBuilder.create()
//                .url("jdbc:mysql://localhost:3306/alom_project")
//                .username("root")
//                .password("tjehwo0116")
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .build();
//    }
//
//    @Bean(name = "mysqlEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
//            EntityManagerFactoryBuilder builder, @Qualifier("mysqlDataSource") DataSource dataSource
//    ) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.example.user.common.entity",
//                        "com.example.user.userdomain.entity",
//                        "com.example.user.mentordomain.entity",
//                        "com.example.user.questiondomain.entity"
//                        )
//                .persistenceUnit("mysql")
//                .build();
//    }
//
//    @Bean(name = "mysqlTransactionManager")
//    public PlatformTransactionManager mysqlTransactionManager(
//            @Qualifier("mysqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory
//    ) {
//        return new JpaTransactionManager(Objects.requireNonNull(mysqlEntityManagerFactory.getObject()));
//    }
}
