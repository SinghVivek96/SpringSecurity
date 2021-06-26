package com.vivek.security.authPractice;

import java.util.Optional;

public interface ApplicationUserDAOPractice {
    public Optional<ApplicationUserPractice> getApplicationUserByUsername(String username);
}
