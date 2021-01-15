package com.github.zmilad97.onlineExam.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private MyUserDetailsService myUserDetailsService;

    public SecurityConfiguration(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);

        return daoAuthenticationProvider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) {

      /*  web.ignoring().antMatchers("/resources/**")
                .and().ignoring().antMatchers("/resources/static/**")
                .and().ignoring().antMatchers("/resources/static")
                .and().ignoring().antMatchers("/user/init");*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("login").permitAll()
                .antMatchers("/signUp").permitAll()
                .antMatchers("home").permitAll()
                .antMatchers("/index").authenticated()
                .antMatchers("/user/init").permitAll()
                .antMatchers("/user/add").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/remove/**").hasRole("ADMIN")
                .antMatchers("/question/add").hasAnyRole("ADMIN","MASTER")
                .antMatchers("/exam/add").hasAnyRole("ADMIN","MASTER")
                .antMatchers("/addQuestion.html").hasAnyRole("ADMIN","MASTER")
                .antMatchers("/exam/**/add2exam").hasAnyRole("ADMIN","MASTER")      //TODO :Check Here **
                .antMatchers("/exam/all").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/index")
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and()
                .rememberMe().tokenValiditySeconds((2592000))
                .and()
                .httpBasic();


    }
}
