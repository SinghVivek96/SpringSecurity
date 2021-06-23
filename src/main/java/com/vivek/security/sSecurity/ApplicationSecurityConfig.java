package com.vivek.security.sSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
//                .antMatchers("/management/api/**").hasAnyRole(ApplicationUserRole.ADMINTRAINEE.name(),ApplicationUserRole.STUDENT.name())
                .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
//                .antMatchers("/*").hasRole(ApplicationUserRole.ADMIN.name())
//                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(),ApplicationUserRole.ADMINTRAINEE.name())
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
//                                .roles(ApplicationUserRole.STUDENT.name())
                                .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
                                .build();

        UserDetails ADMIN = User.builder()
                                .username("admin")
                                .password(passwordEncoder.encode("admin"))
//                                .roles(ApplicationUserRole.ADMIN.name())
                                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
                                .build();

        UserDetails ADMIN_TRAINEE = User.builder()
                                        .username("admintrainee")
                                        .password(passwordEncoder.encode("admintrainee"))
//                                        .roles(ApplicationUserRole.ADMINTRAINEE.name())
                                        .authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
                                        .build();

        return new InMemoryUserDetailsManager(vivek,ADMIN,ADMIN_TRAINEE);
    }
}
