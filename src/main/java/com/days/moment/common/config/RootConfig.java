package com.days.moment.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.*;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.days.moment.board.config.BoardRootConfig;

import javax.sql.DataSource;
import java.util.ArrayList;


@Configuration
@Import(BoardRootConfig.class)
@EnableTransactionManagement
public class RootConfig {


    @Bean
    public SqlSessionFactory sqlSessionFactory()throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        //config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");

        //config.setJdbcUrl("jdbc:mysql://localhost:3306/springdb");
        config.setJdbcUrl("jdbc:log4jdbc:mysql://hyun-9999.asuscomm.com:3377/momentdb");
        config.setUsername("master");
        config.setPassword("11009900");
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean
    public TransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean( name = "names")
    public ArrayList<String> names(){
        ArrayList<String> list = new ArrayList<>();
        list.add("AAA");
        list.add("BBB");
        list.add("CCC");
        return list;
    }
}
