package com.vivek.security.auth;

import com.google.common.collect.Lists;
import com.vivek.security.sSecurity.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository("fake")
public class FakeApplicationUserDAOService implements ApplicationUserDAO{
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public FakeApplicationUserDAOService(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationByUserName(String username) {
        return getApplicationUser()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUser(){
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        ApplicationUserRole.STUDENT.getGrantedAuthorities(),
                        "annasmith",
                        passwordEncoder.encode("password"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        ApplicationUserRole.ADMIN.getGrantedAuthorities(),
                        "linda",
                        passwordEncoder.encode("password"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(),
                        "tom",
                        passwordEncoder.encode("password"),
                        true,
                        true,
                        true,
                        true
                )
        );
        return applicationUsers;
    }
}
