/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;
import models.User;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fiqih
 */
public class AuthController {
    
    private static Map<String, User> userDatabase = new HashMap<>();
    
    public AuthController() {
        if (userDatabase.isEmpty()) {
            userDatabase.put("admin@kohisop.com", new User("Admin KohiSop", "admin@kohisop.com", "admin123"));
        }
    }
    
    public boolean register(String fullName, String email, String password) {
        if (userDatabase.containsKey(email)) {
            System.out.println("Register Gagal: Email sudah digunakan!");
            return false;
        }
    
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Register Gagal: Data tidak boleh kosong!");
            return false;
        }
        
        User newUser = new User(fullName, email, password);
        userDatabase.put(email, newUser);
        System.out.println("Register Sukses untuk: " + fullName);
        return true;
    }
    
    public User login(String email, String password) {
        User user = userDatabase.get(email);

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login Sukses! Selamat datang " + user.getFullName());
            return user;
        }
        
        System.out.println("Login Gagal: Email atau Password salah!");
        return null;
    }
        
}
