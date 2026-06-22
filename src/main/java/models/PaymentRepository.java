/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.*;

/**
 *
 * @author rvldrh
 */
public class PaymentRepository {

    private final String path = "payments.txt";

    public void savePayment(
            String username,
            double subtotal,
            double totalBayar,
            String metode
    ) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(path, true))) {

            bw.write(username + "|"
                    + subtotal + "|"
                    + totalBayar + "|"
                    + metode);

            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFullOrder(
            String username,
            java.util.List<CartItem> items,
            double subtotal,
            double totalBayar,
            String metode
    ) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("orders.txt", true))) {

            for (CartItem item : items) {
                Menu menu = item.getMenu();

                bw.write(
                        username + "|"
                        + menu.getKode() + "|"
                        + menu.getNama() + "|"
                        + item.getKuantitas() + "|"
                        + menu.getHarga() + "|"
                        + menu.getKategori() + "|"
                        + subtotal + "|"
                        + totalBayar + "|"
                        + metode
                );

                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
