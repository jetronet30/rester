package com.jaba.rester.securing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception{
        http
              .csrf((csrf)->csrf.disable())
              .authorizeHttpRequests((request)->request
                .requestMatchers("/iptv/**",
                                             "/playlist",
                                             "./mainrepo/content/stream/**",
                                             "/css/login.css",
                                             "/css/**",
                                             "/script/**",
                                             "actuator/**",
                                             "/logo/**",
                                             "/images/bacground.jpg",
                                             "/tvmonitor/**",
                                             "/live/**",
                                             "/script/ajax.js").permitAll()
                .anyRequest().authenticated()
              )
              .formLogin((form)->form
                .loginPage("/login")
                .permitAll()
              )
              .logout((logout)->logout.permitAll());
              http
		          .headers(headers->headers.disable());


        return http.build();
    }
      @Bean
      public AuthenticationManager authenticationManager (AuthenticationConfiguration auth)throws Exception{
          return auth.getAuthenticationManager();
      }

      @Bean
      public PasswordEncoder encoder(){
          return new BCryptPasswordEncoder();
      }

}
