/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.*;
import java.io.*;

/**
 *
 * @author rvldrh
 */
public class KitchenModel {

    private ArrayList<KitchenOrder> orders = new ArrayList<>();

    public void loadOrders() {
        orders.clear();

        File file = new File("orders.txt");

        if (!file.exists()) {
            System.out.println("orders.txt tidak ada");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length >= 9) {
                    orders.add(new KitchenOrder(
                            parts[1], // kode
                            parts[2], // nama
                            Integer.parseInt(parts[3]), // qty
                            Double.parseDouble(parts[4]), // harga
                            parts[5] // kategori
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<KitchenOrder> getOrders() {
        return orders;
    }
}
