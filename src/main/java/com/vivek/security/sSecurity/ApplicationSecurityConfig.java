package com.vivek.security.sSecurity;

import com.vivek.security.auth.ApplicationUserService;
import com.vivek.security.jwt.JWTConfig;
import com.vivek.security.jwt.JWTTokenVerifier;
import com.vivek.security.jwt.JWTUsernamePasswordAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final JWTConfig jwtConfig;
    private final SecretKey secretKey;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService, JWTConfig jwtConfig, SecretKey secretKey) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrf().disable()
//                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JWTUsernamePasswordAuthFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JWTTokenVerifier(jwtConfig, secretKey),JWTUsernamePasswordAuthFilter.class)
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
                .authenticated();
//                .and()
//                .formLogin()
//                    .loginPage("/login")
//                    .permitAll()
//                    .defaultSuccessUrl("/courses",true)
//                    .usernameParameter("username")
//                    .passwordParameter("password")
//                .and()
//                .rememberMe()
//                    .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
//                    .key("some_good_key_something_secure")
//                .and()
//                .logout()
//                    .logoutUrl("/logout")
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
//                    .clearAuthentication(true)
//                    .invalidateHttpSession(true)
//                    .deleteCookies("remember-me","JSESSIONID")
//                    .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(daoAuthenticationProvider());
    }


//    @Override
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
//    protected UserDetailsService userDetailsService() {
//        UserDetails vivek = User.builder()
//                                .username("viveks")
//                                .password(passwordEncoder.encode("viveks"))
////                                .roles(ApplicationUserRole.STUDENT.name())
//                                .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
//                                .build();
//
//        UserDetails ADMIN = User.builder()
//                                .username("admin")
//                                .password(passwordEncoder.encode("admin"))
////                                .roles(ApplicationUserRole.ADMIN.name())
//                                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
//                                .build();
//
//        UserDetails ADMIN_TRAINEE = User.builder()
//                                        .username("admintrainee")
//                                        .password(passwordEncoder.encode("admintrainee"))
////                                        .roles(ApplicationUserRole.ADMINTRAINEE.name())
//                                        .authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
//                                        .build();
//
//        return new InMemoryUserDetailsManager(vivek,ADMIN,ADMIN_TRAINEE);
//    }
}
