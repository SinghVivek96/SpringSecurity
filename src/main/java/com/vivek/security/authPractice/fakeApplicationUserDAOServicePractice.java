package com.vivek.security.authPractice;

import com.google.common.collect.Lists;
import com.vivek.security.sSecurity.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("fakePractice")
public class fakeApplicationUserDAOServicePractice implements ApplicationUserDAOPractice{

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public fakeApplicationUserDAOServicePractice(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Optional<ApplicationUserPractice> getApplicationUserByUsername(String username) {
        return getApplicationUserPractice()
                .stream()
                .filter(applicationUserPractice -> username.equals(applicationUserPractice.username))
                .findFirst();
    }


    private List<ApplicationUserPractice> getApplicationUserPractice(){
        List<ApplicationUserPractice> applicationUserPractices = Lists.newArrayList(
                new ApplicationUserPractice(ApplicationUserRole.STUDENT.getGrantedAuthorities(),
                        passwordEncoder.encode("password"),
                        "vivek",
                        true,
                        true,
                        true,
                        true),
                new ApplicationUserPractice(ApplicationUserRole.ADMIN.getGrantedAuthorities(),
                        passwordEncoder.encode("password"),
                        "aman",
                        true,
                        true,
                        true,
                        true),
                new ApplicationUserPractice(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(),
                        passwordEncoder.encode("password"),
                        "golu",
                        true,
                        true,
                        true,
                        true)
        );
        return  applicationUserPractices;
    }
}
