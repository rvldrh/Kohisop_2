/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.*;
import views.KitchenView;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import views.*;

/**
 *
 * @author rvldrh
 */
public class KitchenController {

    private KitchenModel model;
    private KitchenView view;
    private User userAktif;
    private User user;

    /** Overload dengan User — dipakai saat masuk dari ReceiptView */
    public KitchenController(KitchenModel model, KitchenView view, User user) {
        this.model     = model;
        this.view      = view;
        this.user      = user;
        this.userAktif = user;

        loadData();
        view.getBtnOrder().addActionListener(e -> {
            MenuModel menuModel = new MenuModel();
            PemesananModel pesanModel = new PemesananModel(menuModel);
            PemesananFrame orderView = new PemesananFrame();
            new PemesananController(menuModel, pesanModel, orderView, this.user);
            orderView.setVisible(true);
            
            view.dispose();
        });
        
        view.getBtnMember().addActionListener(e -> {
            views.MembershipFrame memberView = new views.MembershipFrame(
                this.userAktif, 
                0.0, 
                new java.util.ArrayList<models.CartItem>()
            );

            memberView.setLocationRelativeTo(null);
            memberView.setVisible(true);

            view.dispose();
        });
        
        view.getBtnLogout().addActionListener(e -> {
            int pilihan = javax.swing.JOptionPane.showConfirmDialog(
                    view, 
                    "Apakah kamu yakin ingin keluar dari akun?",
                    "Konfirmasi Logout",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE
            );

            if (pilihan == javax.swing.JOptionPane.YES_OPTION) {
                views.LoginFrame loginView = new views.LoginFrame();
                loginView.setLocationRelativeTo(null);
                loginView.setVisible(true);

                view.dispose();
            }
        });
    }

    public KitchenController(KitchenModel model, KitchenView view) {
        this.model = model;
        this.view = view;

        loadData();
        view.getBtnOrder().addActionListener(e -> {
            MenuModel menuModel = new MenuModel();
            PemesananModel pesanModel = new PemesananModel(menuModel);
            PemesananFrame orderView = new PemesananFrame();

            new PemesananController(menuModel, pesanModel, orderView, user);

            orderView.setVisible(true);
            
            view.dispose();
        });
        
                view.getBtnMember().addActionListener(e -> {
            views.MembershipFrame memberView = new views.MembershipFrame(
                this.user, 
                0.0, 
                new java.util.ArrayList<models.CartItem>()
            );

            memberView.setLocationRelativeTo(null);
            memberView.setVisible(true);

            view.dispose();
        });
                    
                view.getBtnLogout().addActionListener(e -> {
            int pilihan = javax.swing.JOptionPane.showConfirmDialog(
                    view, 
                    "Apakah kamu yakin ingin keluar dari akun?",
                    "Konfirmasi Logout",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE
            );

            if (pilihan == javax.swing.JOptionPane.YES_OPTION) {
                views.LoginFrame loginView = new views.LoginFrame();
                loginView.setLocationRelativeTo(null);
                loginView.setVisible(true);

                view.dispose();
            }
        });
    }

    private void loadData() {
        model.loadOrders();

        PriorityQueue<KitchenOrder> foodQueue
                = new PriorityQueue<>(
                        (a, b) -> Double.compare(b.getHarga(), a.getHarga())
                );

        Stack<KitchenOrder> drinkStack = new Stack<>();

        for (KitchenOrder order : model.getOrders()) {
            if (order.getKategori().equalsIgnoreCase("Makanan")) {
                foodQueue.add(order);
            } else {
                drinkStack.push(order);
            }
        }

        // Hitung jumlah customer unik dari orders.txt
        int jumlahCustomer = hitungCustomerAktif();
        view.setCustomerAktif(jumlahCustomer);

        renderFood(foodQueue);
        renderDrinks(drinkStack);
    }

    /**
     * Baca orders.txt dan hitung jumlah baris (transaksi) unik berdasarkan
     * username. Setiap 1 user yang bayar = 1 pelanggan aktif.
     */
    private int hitungCustomerAktif() {
        java.util.Set<String> customers = new java.util.LinkedHashSet<>();
        java.io.File f = new java.io.File("orders.txt");
        if (!f.exists()) return 0;
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 1 && !parts[0].isBlank()) {
                    customers.add(parts[0]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers.size();
    }

    private void renderFood(PriorityQueue<KitchenOrder> queue) {
        DefaultTableModel dtm
                = (DefaultTableModel) view.getFoodTable().getModel();

        dtm.setRowCount(0);

        int no = 1;

        while (!queue.isEmpty()) {
            KitchenOrder item = queue.poll();

            String priority;

            if (item.getHarga() >= 100) {
                priority = "Tinggi";
            } else if (item.getHarga() >= 50) {
                priority = "Sedang";
            } else {
                priority = "Rendah";
            }

            dtm.addRow(new Object[]{
                no++,
                item.getNama(),
                item.getHarga(),
                priority
            });
        }
    }

    private void renderDrinks(Stack<KitchenOrder> stack) {
        view.clearDrinkList();

        int no = 1;
        int total = stack.size();

        while (!stack.isEmpty()) {
            KitchenOrder item = stack.pop();

            String status = null;

            if (no == 1) {
                status = "Terakhir\ndipesan";
            } else if (no == total) {
                status = "Pertama\ndipesan";
            }

            view.addDrinkItem(no, item.getNama(), status);
            no++;
        }
    }
}
