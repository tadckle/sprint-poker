package org.zhxie.sprintpoker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.zhxie.sprintpoker.service.CustomUserDetailsService;
import org.zhxie.sprintpoker.service.LoginSuccessHandler;

import javax.sql.DataSource;

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

  @Autowired @Qualifier("dataSource")
  private DataSource dataSource;


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
            .and().rememberMe().tokenValiditySeconds(86400).tokenRepository(tokenRepository());;
  }

  private JdbcTokenRepositoryImpl tokenRepository() {
    JdbcTokenRepositoryImpl jtr = new JdbcTokenRepositoryImpl();
    jtr.setDataSource(dataSource);
    return jtr;
  }

  @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/user/regist");
    }

    @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService).passwordEncoder(bcryptPasswordEncoder());
    //remember me
    auth.eraseCredentials(false);
  }


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

  @Bean
  public CustomSecurityMetadataSource securityMetadataSource() {
    return new CustomSecurityMetadataSource(settings.getUrlroles());
  }

  @Bean
  public CustomFilterSecurityInterceptor customFilter() throws Exception{
    CustomFilterSecurityInterceptor customFilter = new CustomFilterSecurityInterceptor();
    customFilter.setSecurityMetadataSource(securityMetadataSource());
    customFilter.setAccessDecisionManager(accessDecisionManager());
    return customFilter;
  }

  @Bean
  public AccessDecisionManager accessDecisionManager() {
    return new CustomAccessDecisionManager();
  }
}