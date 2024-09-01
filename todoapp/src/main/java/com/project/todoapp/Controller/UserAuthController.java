package com.project.todoapp.Controller;

import com.project.todoapp.Model.UserModel;
import com.project.todoapp.Service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
public class UserAuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserModel userModel, HttpServletRequest request){
        UserModel user = userService.findByUsername(userModel.getUserName());
        if (user != null && user.getPassword().equals(userModel.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("userId", user.getId());
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserModel userModel,HttpServletRequest request){
        UserModel user = new UserModel(userModel.getUserName(), userModel.getPassword());
        String result = userService.registerUser(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("isLoggedIn") != null && (boolean) session.getAttribute("isLoggedIn")) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/checkSession")
    public ResponseEntity<Boolean> checkSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("isLoggedIn") != null && (boolean) session.getAttribute("isLoggedIn")) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
