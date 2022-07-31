package com.example.web_task_manager.config;

import com.example.web_task_manager.service.impl.UserDetailsImpl;
import com.example.web_task_manager.utils.CookieUtil;
import com.example.web_task_manager.utils.JwtTokenFilter;
import com.example.web_task_manager.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsImpl userDetails;
    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(new BCryptPasswordEncoder());
    }
    @Override @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(
                ((request, response, ex) ->{
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                            ex.getMessage());
                } )
        );
        http.authorizeRequests().antMatchers("/login/**","/tasklist/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login-check")
                .defaultSuccessUrl("/tasklist")
                .failureUrl("/login?status=login_false")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?status=logout");

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
