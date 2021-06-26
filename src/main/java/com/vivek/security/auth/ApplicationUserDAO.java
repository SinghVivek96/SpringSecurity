package com.vivek.security.auth;

import java.util.Optional;

public interface ApplicationUserDAO {

    public Optional<ApplicationUser> selectApplicationByUserName(String username);

}
