package com.vivek.security.authPractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserServicePractice implements UserDetailsService {

    @Autowired
    private final ApplicationUserDAOPractice applicationUserDAOPractice;

    public ApplicationUserServicePractice(@Qualifier("fakePractice") ApplicationUserDAOPractice applicationUserDAOPractice) {
        this.applicationUserDAOPractice = applicationUserDAOPractice;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDAOPractice.getApplicationUserByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("Username %s not found",username)));
    }
}
