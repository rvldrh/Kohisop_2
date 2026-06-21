/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.*;
import java.util.*;

/**
 *
 * @author rvldrh
 */
public class UserRepository {

    private final String FILE_PATH = "users.txt";

    public UserRepository() {
        File file = new File(FILE_PATH);

        try {
            if (!file.exists()) {
                file.createNewFile();

                BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                bw.write("Admin KohiSop,admin@kohisop.com,admin123");
                bw.newLine();
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println(new File(FILE_PATH).getAbsolutePath());
    }

    public void saveUser(User user) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true));
            bw.write(user.getFullName() + ","
                    + user.getEmail() + ","
                    + user.getPassword());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 3) {
                    users.add(new User(data[0], data[1], data[2]));
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }
}
