package com.code.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import javax.sql.DataSource;

@MapperScan(basePackages = "com.code.dao")
@PropertySource(value = "classpath:jdbc/${spring.profiles.active}.properties")
@Configuration
public class MysqlConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource Datasource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory () throws Exception{
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(Datasource());
        // mapper 사용시
        /*Resource[] arrResource = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*Mapper.xml");
        sqlSessionFactoryBean.setMapperLocations(arrResource);*/
        return sqlSessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sessionTemplate () throws Exception{
        return new SqlSessionTemplate(sqlSessionFactory());
    }

}
