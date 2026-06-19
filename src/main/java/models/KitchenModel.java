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

                if (parts.length >= 5) {
                    orders.add(new KitchenOrder(
                            parts[0],
                            parts[1],
                            Integer.parseInt(parts[2]),
                            Double.parseDouble(parts[3]),
                            parts[4]
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
