/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.MenuModel;
import models.PemesananModel;
import models.Menu;
import models.CartItem;
import views.PemesananFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import models.KitchenModel;
import controllers.*;
import views.*;
/**
 *
 * @author Fiqih
 */
public class PemesananController {

    private MenuModel menuModel;
    private PemesananModel pesanModel;
    private PemesananFrame view;

    public PemesananController(MenuModel menuModel, PemesananModel pesanModel, PemesananFrame view) {
        this.menuModel = menuModel;
        this.pesanModel = pesanModel;
        this.view = view;
        
        updateTabelMenu();

        this.view.getBtnTambah().addActionListener(e -> prosesTambah());
        this.view.getBtnBatal().addActionListener(e -> {
            pesanModel.kosongkanKeranjang();
            updateTabelKeranjang();
            JOptionPane.showMessageDialog(view, "Pesanan dibatalkan (CC).");
        });

        this.view.getBtnSelesai().addActionListener(e -> prosesSelesai());
        view.getBtnKitchen().addActionListener(e -> {
            KitchenModel model = new KitchenModel();
            KitchenView kitchen = new KitchenView();

            KitchenController kitchenC= new KitchenController(model, kitchen);

            bukaKitchen();
        });
    }

    private void bukaKitchen() {
        views.KitchenView kitchenView = new views.KitchenView();
        kitchenView.setLocationRelativeTo(null);
        kitchenView.setVisible(true);
    }

    private void updateTabelMenu() {
        DefaultTableModel dtmMakanan = (DefaultTableModel) view.getTblMenuMakanan().getModel();
        dtmMakanan.setRowCount(0); // Kosongkan dulu

        for (Menu m : menuModel.getMenuMakananTerurut()) {
            dtmMakanan.addRow(new Object[]{m.getKode(), m.getNama(), m.getHarga()});
        }

        DefaultTableModel dtmMinuman = (DefaultTableModel) view.getTblMenuMinuman().getModel();
        dtmMinuman.setRowCount(0);

        for (Menu m : menuModel.getMenuMinumanTerurut()) {
            dtmMinuman.addRow(new Object[]{m.getKode(), m.getNama(), m.getHarga()});
        }
    }

    private void updateTabelKeranjang() {
        DefaultTableModel dtmMinuman = (DefaultTableModel) view.getTblKeranjangMinuman().getModel();
        dtmMinuman.setRowCount(0);

        for (CartItem item : pesanModel.getKeranjangMinumanTerurutHarga()) {
            dtmMinuman.addRow(new Object[]{
                item.getMenu().getKode(),
                item.getMenu().getNama(),
                item.getMenu().getHarga(),
                item.getKuantitas(),
                item.getSubtotal()
            });
        }

        DefaultTableModel dtmMakanan = (DefaultTableModel) view.getTblKeranjangMakanan().getModel();
        dtmMakanan.setRowCount(0);

        for (CartItem item : pesanModel.getKeranjangMakananTerurutHarga()) {
            dtmMakanan.addRow(new Object[]{
                item.getMenu().getKode(),
                item.getMenu().getNama(),
                item.getMenu().getHarga(),
                item.getKuantitas(),
                item.getSubtotal()
            });
        }

        double totalKeseluruhan = 0;
        for (CartItem item : pesanModel.getKeranjang()) {
            totalKeseluruhan += item.getSubtotal();
        }

        view.getLblTotalHarga().setText(String.format("Total Harga: Rp %,.0f", totalKeseluruhan));
    }

    private void prosesTambah() {
        String kode = view.getTxtKodeMenu().getText().trim();
        String qtyStr = view.getTxtKuantitas().getText().trim();

        int qty = 1;
        if (!qtyStr.isEmpty()) {
            if (qtyStr.equalsIgnoreCase("S") || qtyStr.equals("0")) {
                JOptionPane.showMessageDialog(view, "Item di-skip.");
                return;
            }
            try {
                qty = Integer.parseInt(qtyStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Error: Kuantitas harus angka!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String kategoriMenu = "";
        for (Menu m : menuModel.getMenuMakananTerurut()) {
            if (m.getKode().equalsIgnoreCase(kode)) {
                kategoriMenu = "Makanan";
                break;
            }
        }
        if (kategoriMenu.isEmpty()) {
            for (Menu m : menuModel.getMenuMinumanTerurut()) {
                if (m.getKode().equalsIgnoreCase(kode)) {
                    kategoriMenu = "Minuman";
                    break;
                }
            }
        }

        if (kategoriMenu.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Kode menu tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            view.getTxtKodeMenu().setText("");
            view.getTxtKodeMenu().requestFocus();
            return;
        }

        boolean sudahAdaDiKeranjang = false;
        for (CartItem item : pesanModel.getKeranjang()) {
            if (item.getMenu().getKode().equalsIgnoreCase(kode)) {
                sudahAdaDiKeranjang = true;
                break;
            }
        }

        if (!sudahAdaDiKeranjang) {
            if (kategoriMenu.equals("Makanan") && pesanModel.getKeranjangMakananTerurutHarga().size() >= 5) {
                JOptionPane.showMessageDialog(view, "Keranjang Makanan sudah penuh!\nMaksimal 5 jenis makanan berbeda.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (kategoriMenu.equals("Minuman") && pesanModel.getKeranjangMinumanTerurutHarga().size() >= 5) {
                JOptionPane.showMessageDialog(view, "Keranjang Minuman sudah penuh!\nMaksimal 5 jenis minuman berbeda.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        String hasil = pesanModel.tambahKeKeranjang(kode, qty);
        if (hasil.equals("Sukses")) {
            updateTabelKeranjang();
            view.getTxtKodeMenu().setText("");
            view.getTxtKuantitas().setText("");
        } else {
            JOptionPane.showMessageDialog(view, hasil, "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void prosesSelesai() {
        if (pesanModel.getKeranjang().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Keranjang masih kosong!\nSilakan pilih menu terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(view,
                "Pesanan Selesai!\nTotal item: " + pesanModel.getKeranjang().size() + " jenis.\nData siap dikirim ke Dapur dan Kasir.",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
