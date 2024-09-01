package com.project.todoapp.Service;

import com.project.todoapp.Model.UserModel;
import com.project.todoapp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel findByUsername(String userName){
        return userRepository.findByUserName(userName);
    }

    @Override
    public String registerUser(UserModel user){
        UserModel userFound = findByUsername(user.getUserName());
        if(userFound!=null){
            return "Username already exist";
        }
        else {
            userRepository.save(user);
            return "Registration successful please login";
        }
    }
}
