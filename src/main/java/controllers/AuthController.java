/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;
import models.User;
import java.util.HashMap;
import java.util.Map;
import models.UserRepository;
import java.util.*;

/**
 *
 * @author Fiqih
 */
public class AuthController {
    
      private UserRepository repo;

    public AuthController() {
        repo = new UserRepository();
    }

    public boolean register(String fullName, String email, String password) {

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Register gagal: data kosong");
            return false;
        }

        List<User> users = repo.getAllUsers();

        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Email sudah digunakan");
                return false;
            }
        }

        User user = new User(fullName, email, password);
        repo.saveUser(user);

        System.out.println("Register berhasil");
        return true;
    }

    public User login(String email, String password) {
        List<User> users = repo.getAllUsers();

        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)
                    && u.getPassword().equals(password)) {
                return u;
            }
        }

        return null;
    }
}
