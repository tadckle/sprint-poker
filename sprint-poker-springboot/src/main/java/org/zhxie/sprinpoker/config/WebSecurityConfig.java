package org.zhxie.sprinpoker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.zhxie.sprinpoker.util.JwtUtil;

/**
 * Created by jianyang on 1/7/2019.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    /**
     * Only / ,/home, /login are allowed to directly visit
     * others routes visit need be authenticated
     *
     *  Disable the CORS
     */
    httpSecurity
            .authorizeRequests()
            .antMatchers("/", "/home").permitAll()
            .anyRequest().authenticated().and()
            .formLogin()
              .loginPage("/login").
              permitAll()
            .and()
            .logout()
              .permitAll()
            .and().csrf().disable();
  }

  @Bean
  public BCryptPasswordEncoder bcryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected UserDetailsService userDetailsService() {
    return super.userDetailsService();
  }
}