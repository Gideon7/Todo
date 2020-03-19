package com.todoapplication.app.services;

import com.todoapplication.app.dtos.UserRegistrationDTO;
import com.todoapplication.app.entities.Users;

public interface UserService {
   Users createUser(UserRegistrationDTO userDTO);
    int performLogout(String token);
}
