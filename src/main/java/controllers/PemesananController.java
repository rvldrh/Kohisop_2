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
import models.*;

/**
 *
 * @author Fiqih
 */
public class PemesananController {

    private MenuModel menuModel;
    private PemesananModel pesanModel;
    private PemesananFrame view;
    private User userAktif;
    private OrderRepository orderRepo;

    public PemesananController(
            MenuModel menuModel,
            PemesananModel pesanModel,
            PemesananFrame view,
            User user) {

        this.menuModel = menuModel;
        this.pesanModel = pesanModel;
        this.view = view;
        this.userAktif = user;
        this.orderRepo = new OrderRepository();

        updateTabelMenu();

        this.view.getBtnTambah().addActionListener(e -> prosesTambah());

        this.view.getBtnBatal().addActionListener(e -> {
            pesanModel.kosongkanKeranjang();
            updateTabelKeranjang();
            JOptionPane.showMessageDialog(view, "Pesanan dibatalkan.");
        });

        this.view.getBtnSelesai().addActionListener(e -> prosesSelesai());
    }

    private void updateTabelMenu() {
        DefaultTableModel dtmMakanan = (DefaultTableModel) view.getTblMenuMakanan().getModel();
        dtmMakanan.setRowCount(0);
        for (Menu m : menuModel.getMenuMakananTerurut()) {
            dtmMakanan.addRow(new Object[]{
                m.getKode(),
                m.getNama(),
                formatRupiah(m.getHarga())
            });
        }

        DefaultTableModel dtmMinuman = (DefaultTableModel) view.getTblMenuMinuman().getModel();
        dtmMinuman.setRowCount(0);
        for (Menu m : menuModel.getMenuMinumanTerurut()) {
            dtmMinuman.addRow(new Object[]{
                m.getKode(),
                m.getNama(),
                formatRupiah(m.getHarga())
            });
        }
    }

    private void updateTabelKeranjang() {
        DefaultTableModel dtmMinuman = (DefaultTableModel) view.getTblKeranjangMinuman().getModel();
        dtmMinuman.setRowCount(0);
        for (CartItem item : pesanModel.getKeranjangMinumanTerurutHarga()) {
            dtmMinuman.addRow(new Object[]{
                item.getMenu().getKode(),
                item.getMenu().getNama(),
                formatRupiah(item.getMenu().getHarga()),
                item.getKuantitas(),
                formatRupiah(item.getSubtotal())
            });
        }

        DefaultTableModel dtmMakanan = (DefaultTableModel) view.getTblKeranjangMakanan().getModel();
        dtmMakanan.setRowCount(0);
        for (CartItem item : pesanModel.getKeranjangMakananTerurutHarga()) {
            dtmMakanan.addRow(new Object[]{
                item.getMenu().getKode(),
                item.getMenu().getNama(),
                formatRupiah(item.getMenu().getHarga()),
                item.getKuantitas(),
                formatRupiah(item.getSubtotal())
            });
        }

        double total = 0;
        for (CartItem item : pesanModel.getKeranjang()) {
            total += item.getSubtotal();
        }
        view.getLblTotalHarga().setText("Total Harga: " + formatRupiah(total));
    }

    private void prosesTambah() {
        String kode = view.getTxtKodeMenu().getText().trim();
        String qtyStr = view.getTxtKuantitas().getText().trim();

        int qty = 1;
        if (!qtyStr.isEmpty()) {
            try {
                qty = Integer.parseInt(qtyStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Qty harus angka!");
                return;
            }
        }

        String hasil = pesanModel.tambahKeKeranjang(kode, qty);
        if (hasil.equals("Sukses")) {
            updateTabelKeranjang();
            view.getTxtKodeMenu().setText("");
            view.getTxtKuantitas().setText("");
        } else {
            JOptionPane.showMessageDialog(view, hasil);
        }
    }

    private void prosesSelesai() {
        if (pesanModel.getKeranjang().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Keranjang kosong!");
            return;
        }

        // Harga sudah IDR penuh — tidak perlu dikali apapun
        double total = 0;
        for (CartItem item : pesanModel.getKeranjang()) {
            total += item.getSubtotal();
        }

        MembershipFrame membershipFrame = new MembershipFrame(
                userAktif,
                total,
                pesanModel.getKeranjang()
        );
        membershipFrame.setVisible(true);
        view.dispose();
    }

    /**
     * Format double IDR → "Rp 46.000"
     */
    private String formatRupiah(double idr) {
        return "Rp " + String.format("%,.0f", idr).replace(",", ".");
    }
}
