package cn.jkego.emall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * spring security配置类
 * 添加了spring security依赖后，所有的地址都被spring security控制了，
 * 目前只是需要用到BCrypt密码加密的部分，所以添加一个配置类，配置为所有地址
 * 都可以匿名访问。
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //authorizeRequests:所有security全注解配置实现的开端，表示开始说明需要的权限。
    //需要的权限分两部分，第一部分是拦截的路径，第二部分访问该路径需要的权限。
    //antMatchers表示拦截什么路径，permitAll任何权限都可以访问，直接放行所有。
    //anyRequest() 任何请求，authenticated认证后才能访问
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/admin/home.jsp").hasRole("admin")
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()

                // 项目中用到frame嵌入网页，然后用到springsecurity就被拦截了 浏览器报错  x-frame-options deny
                //  原因是因为springSecurty使用X-Frame-Options防止网页被Frame
                .and().headers().frameOptions().disable()  //解决 'X-Frame-Options' to 'deny'.
                .and().csrf().disable()
        ;


        http
                .formLogin()
                .loginProcessingUrl("/admin/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/admin/index.jsp")
                .defaultSuccessUrl("/admin/home.jsp")
        ;


        http
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/index.jsp")
        ;
    }


    /**
     * 管理员身份认证与授权
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("kiki")
                .password(new BCryptPasswordEncoder()
                        .encode("123456")).roles("admin");

    }

}