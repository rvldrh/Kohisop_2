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

        // Tampilkan data awal ke JTable
        updateTabelMenu();
        
        // Pasang gerbang logika tombol
        this.view.getBtnTambah().addActionListener(e -> prosesTambah());
        this.view.getBtnBatal().addActionListener(e -> {
            pesanModel.kosongkanKeranjang();
            updateTabelKeranjang();
            JOptionPane.showMessageDialog(view, "Pesanan dibatalkan (CC).");
        });
        
        this.view.getBtnSelesai().addActionListener(e -> prosesSelesai());
    }

    private void updateTabelMenu() {
        // --- 1. MENGISI TABEL MAKANAN ---
        DefaultTableModel dtmMakanan = (DefaultTableModel) view.getTblMenuMakanan().getModel();
        dtmMakanan.setRowCount(0); // Kosongkan dulu
        
        for (Menu m : menuModel.getMenuMakananTerurut()) {
            // Perhatikan: Kolom Kategori sudah dihilangkan karena tidak perlu
            dtmMakanan.addRow(new Object[]{m.getKode(), m.getNama(), m.getHarga()});
        }

        // --- 2. MENGISI TABEL MINUMAN ---
        DefaultTableModel dtmMinuman = (DefaultTableModel) view.getTblMenuMinuman().getModel();
        dtmMinuman.setRowCount(0); // Kosongkan dulu
        
        for (Menu m : menuModel.getMenuMinumanTerurut()) {
            // Perhatikan: Kolom Kategori sudah dihilangkan karena tidak perlu
            dtmMinuman.addRow(new Object[]{m.getKode(), m.getNama(), m.getHarga()});
        }
    }
    
    private void updateTabelKeranjang() {
        // --- 1. MENGISI TABEL KERANJANG MINUMAN ---
        DefaultTableModel dtmMinuman = (DefaultTableModel) view.getTblKeranjangMinuman().getModel();
        dtmMinuman.setRowCount(0); // Kosongkan dulu
        
        for (CartItem item : pesanModel.getKeranjangMinumanTerurutHarga()) {
            dtmMinuman.addRow(new Object[]{
                item.getMenu().getKode(), 
                item.getMenu().getNama(), 
                item.getMenu().getHarga(), 
                item.getKuantitas(), 
                item.getSubtotal()
            });
        }

        // --- 2. MENGISI TABEL KERANJANG MAKANAN ---
        DefaultTableModel dtmMakanan = (DefaultTableModel) view.getTblKeranjangMakanan().getModel();
        dtmMakanan.setRowCount(0); // Kosongkan dulu
        
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

        // Tampilkan ke Label dengan format Rupiah (tanpa koma desimal)
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

        // =========================================================================
        // BANSER PENYELIP: VALIDASI KODE DAN PEMBATASAN 5 JENIS PER KATEGORI
        // =========================================================================
        
        // 1. Cari tahu kategori menu berdasarkan kode menggunakan method yang sudah ada
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

        // 2. Jika tidak ketemu di makanan maupun minuman, berarti kodenya salah!
        if (kategoriMenu.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Kode menu tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            view.getTxtKodeMenu().setText("");
            view.getTxtKodeMenu().requestFocus();
            return; // Berhenti, jangan masukkan ke keranjang
        }

        // 3. Cek apakah menu ini SEBENARNYA SUDAH ADA di keranjang (hanya tambah porsi)
        boolean sudahAdaDiKeranjang = false;
        for (CartItem item : pesanModel.getKeranjang()) {
            if (item.getMenu().getKode().equalsIgnoreCase(kode)) {
                sudahAdaDiKeranjang = true;
                break;
            }
        }

        // 4. Jika menu yang diinput adalah JENIS BARU, cek batas maksimal 5 jenisnya
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
        // =========================================================================

        // KODE ASLI KAMU (Tetap dipertahankan karena ini yang memasukkan data ke model)
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
        // cek apakah keranjang kosong
        if (pesanModel.getKeranjang().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Keranjang masih kosong!\nSilakan pilih menu terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return; // Berhenti di sini, tidak bisa lanjut
        }

        // jika ada isinya, muncul pesan sukses
        JOptionPane.showMessageDialog(view, 
            "Pesanan Selesai!\nTotal item: " + pesanModel.getKeranjang().size() + " jenis.\nData siap dikirim ke Dapur dan Kasir.", 
            "Sukses", 
            JOptionPane.INFORMATION_MESSAGE);

        /* * CATATAN UNTUK NANTI SAAT MERGE (GABUNG) DENGAN TIM:
         * Kalau frame Anggota 4 (Keanggotaan) atau Anggota 3 (Pembayaran) sudah jadi,
         * kodingannya akan ditulis di sini. Contohnya:
         * * view.dispose(); // Tutup layar pemesanan ini
         * new FramePembayaran(pesanModel.getKeranjang()).setVisible(true); // Buka layar temanmu
         */
    }
}
