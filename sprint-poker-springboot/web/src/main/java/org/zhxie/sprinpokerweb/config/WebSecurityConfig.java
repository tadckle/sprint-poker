package org.zhxie.sprinpokerweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.zhxie.sprinpokerweb.service.CustomUserDetailsService;
import org.zhxie.sprinpokerweb.service.LoginSuccessHandler;

/**
 * Created by jianyang on 1/7/2019.
 */

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecuritySettings.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  private SecuritySettings settings;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    /**
     * Only / ,/home, /login are allowed to directly visit
     * others routes visit need be authenticated
     *
     *  Disable the CORS
     */
    http.formLogin().loginPage("/login").permitAll().successHandler(loginSuccessHandler())
            .and().authorizeRequests()
            .antMatchers("/images/**", "/checkcode", "/scripts/**", "/styles/**").permitAll()
            .anyRequest().authenticated()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and().logout().logoutSuccessUrl(settings.getLogoutsuccssurl())
            .and().exceptionHandling().accessDeniedPage(settings.getDeniedpage())
            .and().rememberMe().tokenValiditySeconds(86400);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService).passwordEncoder(bcryptPasswordEncoder());
    //remember me
    auth.eraseCredentials(false);
  }

//  @Bean
//  public JdbcTokenRepositoryImpl tokenRepository(){
//    JdbcTokenRepositoryImpl jtr = new JdbcTokenRepositoryImpl();
//    return jtr;
//  }

  @Bean
  public BCryptPasswordEncoder bcryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected UserDetailsService userDetailsService() {
    return super.userDetailsService();
  }

  @Bean
  public LoginSuccessHandler loginSuccessHandler(){
    return new LoginSuccessHandler();
  }

}