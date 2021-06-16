package com.vivek.security.sSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
//                .antMatchers("/management/api/**").hasAnyRole(ApplicationUserRole.ADMINTRAINEE.name(),ApplicationUserRole.STUDENT.name())
//                .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
//                .antMatchers("/*").hasRole(ApplicationUserRole.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails vivek = User.builder()
                                .username("viveks")
                                .password(passwordEncoder.encode("viveks"))
                                .roles(ApplicationUserRole.STUDENT.name())
                                .build();

        UserDetails ADMIN = User.builder()
                                .username("admin")
                                .password(passwordEncoder.encode("admin"))
                                .roles(ApplicationUserRole.ADMIN.name())
                                .build();

        UserDetails ADMIN_TRAINEE = User.builder()
                                        .username("admintrainee")
                                        .password(passwordEncoder.encode("admintrainee"))
                                        .roles(ApplicationUserRole.ADMINTRAINEE.name())
                                        .build();

        return new InMemoryUserDetailsManager(vivek,ADMIN,ADMIN_TRAINEE);
    }
}
