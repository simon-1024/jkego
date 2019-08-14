package cn.jkego.emall;

import cn.jkego.emall.utils.IdWorker;
import cn.jkego.emall.utils.JwtUtil;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import redis.clients.jedis.Jedis;

import javax.sql.DataSource;


@SpringBootApplication
public class EMall1Application {

    public static void main(String[] args) {

        SpringApplication.run(EMall1Application.class, args);

    }


    @Bean
    public IdWorker getIdWork() {
        return new IdWorker();
    }


    @Bean
    public Jedis getJedis() {
        return new Jedis();
    }


    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil getJwtUtil() {
        return new JwtUtil();
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid(){
        return new DruidDataSource();
    }

}
