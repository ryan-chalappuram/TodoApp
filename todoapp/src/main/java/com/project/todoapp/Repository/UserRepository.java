package com.project.todoapp.Repository;

import com.project.todoapp.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {
    UserModel findByUserName(String userName);

}
