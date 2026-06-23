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
        pasangListener();
    }

    public KitchenController(KitchenModel model, KitchenView view) {
        this.model = model;
        this.view  = view;

        loadData();
        pasangListener();
    }

    // ── Listener dipasang sekali, dipakai kedua constructor ──────────────────
    private void pasangListener() {
        view.getBtnOrder().addActionListener(e -> bukaOrder());
        view.getBtnProsesDapur().addActionListener(e -> prosesDapur()); // ← TAMBAHAN

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

    // ── Order ─────────────────────────────────────────────────────────────────
    private void bukaOrder() {
        MenuModel menuModel       = new MenuModel();
        PemesananModel pesanModel = new PemesananModel(menuModel);
        PemesananFrame orderView  = new PemesananFrame();
        new PemesananController(menuModel, pesanModel, orderView, user);
        orderView.setVisible(true);
        view.dispose();
    }

    // ── Proses Dapur ─────────────────────────────────────────────────────────
    /**
     * Sesuai soal CB-02:
     * - Minimal 3 transaksi sebelum bisa diproses
     * - Makanan: Priority Queue (harga tertinggi duluan)
     * - Minuman: Stack LIFO (terakhir dipesan = pertama diproses)
     * - Setelah diproses, antrian dikosongkan dan orders.txt di-reset
     */
    private void prosesDapur() {
        int jumlahCustomer = hitungCustomerAktif();

        if (jumlahCustomer < 3) {
            javax.swing.JOptionPane.showMessageDialog(
                view,
                "Dapur belum bisa diproses.\nDibutuhkan minimal 3 pelanggan, saat ini: "
                    + jumlahCustomer + " pelanggan.",
                "Belum Cukup Pelanggan",
                javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Bangun kembali queue & stack dari orders yang ada
        PriorityQueue<KitchenOrder> foodQueue =
                new PriorityQueue<>((a, b) -> Double.compare(b.getHarga(), a.getHarga()));
        Stack<KitchenOrder> drinkStack = new Stack<>();

        for (KitchenOrder order : model.getOrders()) {
            if (order.getKategori().equalsIgnoreCase("Makanan")) {
                foodQueue.add(order);
            } else {
                drinkStack.push(order);
            }
        }

        // Susun hasil proses dapur
        StringBuilder sb = new StringBuilder();
        sb.append("✅ DAPUR MEMPROSES ").append(jumlahCustomer).append(" PELANGGAN\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");

        sb.append("🍽 TIM MAKANAN (Priority — Harga Tertinggi Duluan):\n");
        int no = 1;
        PriorityQueue<KitchenOrder> foodCopy = new PriorityQueue<>(foodQueue);
        if (foodCopy.isEmpty()) sb.append("   (tidak ada pesanan makanan)\n");
        while (!foodCopy.isEmpty()) {
            KitchenOrder o = foodCopy.poll();
            sb.append(String.format("   %d. %s  —  Rp %.0f%n", no++, o.getNama(), o.getHarga()));
        }

        sb.append("\n☕ TIM MINUMAN (LIFO — Terakhir Dipesan, Pertama Diproses):\n");
        no = 1;
        Stack<KitchenOrder> drinkCopy = new Stack<>();
        drinkCopy.addAll(drinkStack);
        if (drinkCopy.isEmpty()) sb.append("   (tidak ada pesanan minuman)\n");
        while (!drinkCopy.isEmpty()) {
            KitchenOrder o = drinkCopy.pop();
            sb.append(String.format("   %d. %s%n", no++, o.getNama()));
        }

        sb.append("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("Antrian dikosongkan. Siap untuk pelanggan berikutnya.");

        javax.swing.JTextArea ta = new javax.swing.JTextArea(sb.toString());
        ta.setEditable(false);
        ta.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 13));
        javax.swing.JScrollPane sp = new javax.swing.JScrollPane(ta);
        sp.setPreferredSize(new java.awt.Dimension(480, 320));

        javax.swing.JOptionPane.showMessageDialog(
            view, sp, "Proses Dapur Selesai",
            javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        // Kosongkan orders.txt dan reset tampilan
        bersihkanOrdersFile();
        model.getOrders().clear();
        view.resetAntrian();
    }

    /** Hapus semua isi orders.txt setelah dapur selesai diproses */
    private void bersihkanOrdersFile() {
        try (java.io.FileWriter fw = new java.io.FileWriter("orders.txt", false)) {
            // overwrite dengan kosong
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    // ── Load & Render ─────────────────────────────────────────────────────────
    private void loadData() {
        model.loadOrders();

        PriorityQueue<KitchenOrder> foodQueue =
                new PriorityQueue<>((a, b) -> Double.compare(b.getHarga(), a.getHarga()));
        Stack<KitchenOrder> drinkStack = new Stack<>();

        for (KitchenOrder order : model.getOrders()) {
            if (order.getKategori().equalsIgnoreCase("Makanan")) {
                foodQueue.add(order);
            } else {
                drinkStack.push(order);
            }
        }

        // ← FIX: pakai hitungCustomerAktif() yang sudah diperbaiki
        int jumlahCustomer = hitungCustomerAktif();
        view.setCustomerAktif(jumlahCustomer);

        renderFood(foodQueue);
        renderDrinks(drinkStack);
    }

    /**
     * Hitung jumlah transaksi unik dari orders.txt.
     * Format baris: username|kode|nama|qty|harga|kategori|subtotal|total|metode
     * Key unik: username + subtotal + total = 1 transaksi.
     * Baris format lama (tidak punya 9 kolom pipe) diabaikan.
     */
    private int hitungCustomerAktif() {
        java.util.Set<String> transaksi = new java.util.LinkedHashSet<>();
        java.io.File f = new java.io.File("orders.txt");
        if (!f.exists()) return 0;
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 9) {
                    String key = parts[0] + "|" + parts[6] + "|" + parts[7];
                    transaksi.add(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transaksi.size();
    }

    private void renderFood(PriorityQueue<KitchenOrder> queue) {
        DefaultTableModel dtm = (DefaultTableModel) view.getFoodTable().getModel();
        dtm.setRowCount(0);

        int no = 1;
        while (!queue.isEmpty()) {
            KitchenOrder item = queue.poll();
            String priority;
            if (item.getHarga() >= 100000) {
                priority = "Tinggi";
            } else if (item.getHarga() >= 50000) {
                priority = "Sedang";
            } else {
                priority = "Rendah";
            }
            dtm.addRow(new Object[]{ no++, item.getNama(), item.getHarga(), priority });
        }
    }

    private void renderDrinks(Stack<KitchenOrder> stack) {
        view.clearDrinkList();

        int no    = 1;
        int total = stack.size();

        while (!stack.isEmpty()) {
            KitchenOrder item = stack.pop();
            String status = null;
            if (no == 1)     status = "Terakhir\ndipesan";
            else if (no == total) status = "Pertama\ndipesan";
            view.addDrinkItem(no, item.getNama(), status);
            no++;
        }
    }
}