package com.days.moment.security.config;

import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import com.days.moment.security.handler.CustomAccessDeniedHandler;
import com.days.moment.security.handler.CustomAuthenticationEntryPoint;
import com.days.moment.security.handler.CustomLoginSuccessHandler;
import com.days.moment.security.service.CustomUserDetailsService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Log4j2
@MapperScan(basePackages = "com.days.moment.security.mapper")
@ComponentScan(basePackages = "com.days.moment.security.service" )
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private DataSource dataSource;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests()
//                .antMatchers("/sample/doAll").permitAll()
//                .antMatchers("/sample/doMember").access("hasRole('ROLE_MEMBER')")
//                .antMatchers("/sample/doAdmin").access("hasRole('ROLE_ADMIN')");

        http.formLogin().loginPage("/customLogin").loginProcessingUrl("/login");
        http.csrf().disable();

        http.rememberMe().tokenRepository(persistentTokenRepository())
                .key("zerock").tokenValiditySeconds(60*60*24*30);

        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());

        http.exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint());

    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() { return new CustomAuthenticationEntryPoint(); }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserDetailsService);

//        auth.userDetailsService(customUserDetailsService());

//        auth.inMemoryAuthentication().withUser("member1").password("$2a$10$xTFFWIDBh8PXAqR3vlagmODlN/rPxXDkZo67MPDtXAMRQUAPMVFPK")
//                .roles("MEMBER");
//        auth.inMemoryAuthentication().withUser("admin1").password("$2a$10$xTFFWIDBh8PXAqR3vlagmODlN/rPxXDkZo67MPDtXAMRQUAPMVFPK")
//                .roles("MEMBER","ADMIN");
    }

//    @Bean
//    public CustomUserDetailsService customUserDetailsService() {
//        return new CustomUserDetailsService();
//    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }
}