package com.home.homebirthdaytip.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description: springSecurity配置
 * @author: hemb
 * @date: 2020年10月24日 14:56
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //这里配置PasswordEncoder,BCryptPasswordEncoder是security提供的PasswordEncorder的一个实现类
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        web.ignoring().antMatchers("/swagger-ui.html")
//                .antMatchers("/webjars/**")
//                .antMatchers("/v2/**")
//                .antMatchers("/swagger-resources/**");
        http
                .formLogin()   //使用表单登录页面
                .loginPage("/login")    //登录url
                .loginProcessingUrl("/doLogin")    //登录提交url
                .and()
                .authorizeRequests()    //认证请求
                .antMatchers("/login", "/doLogin","/wxLogin"
                        ,"/checkUserName","/regist","/doRegister"
                        ,"/api/subscribe/**"
                        ,"/api/weChat/**"   //来自微信小程序的免登录验证
                        ,"/outAllFileMessage","/getImageByPath"
                        ,"/favicon.ico","/css/**","/js/**","/images/**"
                        ,"/v2/**","/swagger-ui.html","/swagger-resources/configuration/ui","/swagger-resources/**", "/swagger-resources/configuration/security","/webjars/**"
                        ,"/druid/**").permitAll()     //除了***能够无认证访问
                .anyRequest().authenticated()    //任何请求都需要认证
                .and()
                .csrf().disable()    //CSRF跨站请求伪造直接关闭
                .headers().frameOptions().sameOrigin();


    }

}
