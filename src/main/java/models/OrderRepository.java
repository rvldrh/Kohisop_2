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

    public void saveOrder(User user, List<CartItem> items, double total) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("orders.txt", true))) {

            pw.println("===== ORDER =====");
            pw.println("User: " + user.getFullName());

            for (CartItem item : items) {
                pw.println(
                        item.getMenu().getNama()
                        + " x" + item.getKuantitas()
                        + " = Rp " + String.format("%,.0f", item.getSubtotal() * 1000)
                );
            }

            pw.println("TOTAL: Rp " + String.format("%,.0f", total * 1000));
            pw.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
