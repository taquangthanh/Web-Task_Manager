package com.example.web_task_manager.config;

import com.example.web_task_manager.service.impl.UserDetailsImpl;
import com.example.web_task_manager.utils.CookieUtil;
import com.example.web_task_manager.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsImpl userDetails;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/login/**","/tasklist/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/login-succcess")
                .defaultSuccessUrl("/tasklist")
                .failureUrl("/login?status=login_false")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?status=logout");
    }
}
