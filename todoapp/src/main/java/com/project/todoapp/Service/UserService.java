package com.project.todoapp.Service;

import com.project.todoapp.Model.UserModel;
import org.springframework.stereotype.Service;



public interface UserService {
    UserModel findByUsername(String userName);

    String registerUser(UserModel user);
}
