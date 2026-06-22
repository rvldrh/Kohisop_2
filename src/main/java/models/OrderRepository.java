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
public class OrderRepository {

    private final String FILE_PATH = "orders.txt";

    public OrderRepository() {
        File file = new File(FILE_PATH);

        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("orders.txt dibuat di: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveOrder(User user, List<CartItem> keranjang, double total) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true));

            bw.write("=== ORDER ===");
            bw.newLine();

            if (user != null) {
                bw.write("User: " + user.getFullName() + " | " + user.getEmail());
            } else {
                bw.write("User: Guest");
            }

            bw.newLine();

            for (CartItem item : keranjang) {
                bw.write(
                        item.getMenu().getKode() + ","
                        + item.getMenu().getNama() + ","
                        + item.getKuantitas() + ","
                        + item.getSubtotal()
                );
                bw.newLine();
            }

            bw.write("TOTAL=" + total);
            bw.newLine();
            bw.write("=================");
            bw.newLine();
            bw.newLine();

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
