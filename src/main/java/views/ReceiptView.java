/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import controllers.KitchenController;
import models.Menu;
import models.CartItem;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import models.CustomerInfo;
import models.KitchenModel;
import models.PaymentModel;
import models.ReceiptItem;
import models.User;
/**
 *
 * @author rvldrh
 */

public class ReceiptView extends javax.swing.JFrame {

    private JPanel itemsPanel;
    private JLabel lblSubtotal, lblTax, lblBeforeDiscount, lblDiscount, lblAdminFee, lblGrandTotal;
    private JLabel lblNama, lblStatus, lblMetode, lblCurrency, lblPoin;

    // ── Constructor lama (untuk ReceiptController / standalone test) ──────────
    public ReceiptView() {
        this(null, null, null);
    }

    // ── Constructor baru: dipanggil setelah pembayaran berhasil ──────────────
    public ReceiptView(PaymentModel.RincianBayar rincian,
                       List<CartItem> cartItems,
                       User userAktif) {

        setTitle("Struk Pembayaran — KohiSop");
        setSize(600, 820);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(240, 240, 240));

        // ── Receipt card ─────────────────────────────────────────────────────
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(252, 250, 245));
        card.setBorder(new LineBorder(new Color(210, 210, 210), 1, true));

        card.add(createHeader());
        card.add(createSectionTitlePanel("🍽 ITEM PESANAN"));

        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(new Color(252, 250, 245));
        itemsPanel.setBorder(new EmptyBorder(0, 24, 0, 24));
        card.add(itemsPanel);

        card.add(createDashedLine());
        card.add(createSectionTitlePanel("🧾 RINGKASAN BIAYA"));
        card.add(createSummary());
        card.add(createDashedLine());
        card.add(createSectionTitlePanel("👤 INFO PELANGGAN & PEMBAYARAN"));
        card.add(createCustomerInfo());
        card.add(Box.createVerticalStrut(20));

        // Footer — dibungkus JPanel agar benar-benar center
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(252, 250, 245));
        JLabel footer = new JLabel(
                "<html><center><b>Terima kasih &amp; silakan datang kembali!</b><br>"
                + "Simpan struk ini sebagai bukti pembayaran</center></html>");
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        footer.setForeground(new Color(0, 120, 90));
        footer.setFont(new Font("Dialog", Font.PLAIN, 12));
        footerPanel.add(footer);
        card.add(footerPanel);
        card.add(Box.createVerticalStrut(20));

        // Langsung taruh card tanpa JScrollPane
        root.add(card, BorderLayout.CENTER);

        // ── Bottom button panel ───────────────────────────────────────────────
        JPanel bottomPanel = buildBottomPanel(rincian, cartItems, userAktif);
        root.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(root);

        // Isi data jika tersedia
        if (rincian != null && cartItems != null) {
            populateFromRincian(rincian, cartItems, userAktif);
        }
    }

    // ── Populate semua data dari RincianBayar + CartItems ───────────────────
    private void populateFromRincian(PaymentModel.RincianBayar r,
                                      List<CartItem> cartItems,
                                      User userAktif) {
        // Items — dikelompokkan: Makanan dulu, lalu Minuman
        List<ReceiptItem> receiptItems = buildReceiptItems(cartItems);
        setItems(receiptItems);

        // Summary
        setSummary(
            r.subtotalSebelumPajak,
            r.totalPajak,
            r.diskonChannel + r.diskonPoin,
            r.adminFee,
            r.totalIDR
        );

        // Customer info
        String namaUser = userAktif != null ? userAktif.getFullName() : "-";
        String status   = (r.poinSebelum > 0 || r.poinDiperoleh > 0) ? "Member" : "Non-Member";
        String metode   = r.metodeName;
        String currency = r.currencyKode.equals("IDR")
                ? "IDR"
                : r.currencyKode + " (" + r.currencySimbol + String.format(" %.4f", r.totalConverted) + ")";

        lblNama.setText(namaUser);
        lblStatus.setText(status);
        lblMetode.setText(metode);
        lblCurrency.setText(currency);
        lblPoin.setText("Poin: " + r.poinSebelum + " → +" + r.poinDiperoleh + " → " + r.poinSetelah);
    }

    // ── Bangun ReceiptItem dari CartItem (Makanan dulu, lalu Minuman) ────────
    private List<ReceiptItem> buildReceiptItems(List<CartItem> cartItems) {
        List<ReceiptItem> makanan  = new ArrayList<>();
        List<ReceiptItem> minuman  = new ArrayList<>();

        for (CartItem ci : cartItems) {
            Menu m = ci.getMenu();
            ReceiptItem ri = new ReceiptItem(
                m.getKode(), m.getNama(), m.getKategori(),
                ci.getKuantitas(), m.getHarga(), 0
            );
            if (m.getKategori().equalsIgnoreCase("Makanan")) {
                makanan.add(ri);
            } else {
                minuman.add(ri);
            }
        }

        List<ReceiptItem> all = new ArrayList<>();
        all.addAll(makanan);
        all.addAll(minuman);
        return all;
    }

    // ── Bottom panel: tombol Kirim ke Dapur ──────────────────────────────────
    private JPanel buildBottomPanel(PaymentModel.RincianBayar rincian,
                                     List<CartItem> cartItems,
                                     User userAktif) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 12));
        panel.setBackground(new Color(0, 102, 80));

        JButton btnKitchen = new JButton("🍳 Kirim ke Dapur");
        btnKitchen.setFont(new Font("Dialog", Font.BOLD, 14));
        btnKitchen.setBackground(new Color(255, 200, 50));
        btnKitchen.setForeground(new Color(30, 30, 30));
        btnKitchen.setFocusPainted(false);
        btnKitchen.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 160, 0), 1, true),
            new EmptyBorder(8, 20, 8, 20)
        ));
        btnKitchen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnKitchen.addActionListener(e -> {
            // Buka KitchenView, tutup ReceiptView
            KitchenModel kitchenModel = new KitchenModel();
            KitchenView kitchenView   = new KitchenView();
            new KitchenController(kitchenModel, kitchenView, userAktif);
            kitchenView.setVisible(true);
            this.dispose();
        });

        // Jika dipanggil standalone (tanpa data), disable tombol
        if (rincian == null) {
            btnKitchen.setEnabled(false);
            btnKitchen.setToolTipText("Hanya aktif setelah proses pembayaran");
        }

        panel.add(btnKitchen);
        return panel;
    }

    // ── UI builder helpers ───────────────────────────────────────────────────

    private JPanel createHeader() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 80));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("KohiSop");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Dialog", Font.BOLD, 32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel address = new JLabel("Jl. Kopi Nusantara No. 42, Malang");
        address.setForeground(Color.WHITE);
        address.setFont(new Font("Dialog", Font.PLAIN, 12));
        address.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(8));
        panel.add(address);
        return panel;
    }

    private JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(12, 24, 8, 24));
        label.setFont(new Font("Dialog", Font.BOLD, 13));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Bungkus dalam panel agar center benar-benar full-width
        return label;
    }

    private JPanel createSectionTitlePanel(String text) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBackground(new Color(252, 250, 245));
        JLabel label = new JLabel(text);
        label.setFont(new Font("Dialog", Font.BOLD, 13));
        p.add(label);
        return p;
    }

    private JPanel createSummary() {
        JPanel p = createKeyValuePanel();
        lblSubtotal       = createValueLabel();
        lblTax            = createValueLabel();
        lblBeforeDiscount = createValueLabel();
        lblDiscount       = createValueLabel(new Color(0, 140, 80));
        lblAdminFee       = createValueLabel();
        lblGrandTotal     = createValueLabel();

        p.add(row("Subtotal (sebelum pajak)", lblSubtotal));
        p.add(row("Pajak",                    lblTax));
        p.add(row("Sebelum diskon",           lblBeforeDiscount));
        p.add(row("Diskon",                   lblDiscount));
        p.add(row("Admin Fee",                lblAdminFee));
        p.add(Box.createVerticalStrut(12));
        p.add(new JSeparator());
        p.add(Box.createVerticalStrut(12));
        p.add(row("TOTAL",                    lblGrandTotal));
        return p;
    }

    private JPanel createCustomerInfo() {
        JPanel p = createKeyValuePanel();
        lblNama     = createValueLabel();
        lblStatus   = createValueLabel();
        lblMetode   = createValueLabel();
        lblCurrency = createValueLabel();
        lblPoin     = createValueLabel(new Color(0, 100, 180));

        p.add(row("Nama",         lblNama));
        p.add(row("Status",       lblStatus));
        p.add(row("Metode bayar", lblMetode));
        p.add(row("Mata uang",    lblCurrency));
        p.add(row("Poin",         lblPoin));
        return p;
    }

    private JPanel createKeyValuePanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(252, 250, 245));
        p.setBorder(new EmptyBorder(0, 24, 0, 24));
        return p;
    }

    private JLabel createValueLabel()            { return createValueLabel(Color.BLACK); }
    private JLabel createValueLabel(Color color) {
        JLabel lbl = new JLabel("-");
        lbl.setFont(new Font("Dialog", Font.BOLD, 13));
        lbl.setForeground(color);
        return lbl;
    }

    private JPanel row(String key, JLabel valueLabel) {
        JPanel row = new JPanel(new BorderLayout(16, 0));
        row.setBackground(new Color(252, 250, 245));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        JLabel left = new JLabel(key);
        left.setFont(new Font("Dialog", Font.PLAIN, 13));
        row.add(left, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.EAST);
        return row;
    }

    private JSeparator createDashedLine() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(200, 200, 200));
        return sep;
    }

    // ── Methods untuk ReceiptController (backward compat) ────────────────────

    public void setItems(List<ReceiptItem> items) {
        itemsPanel.removeAll();
        String lastKategori = "";
        for (ReceiptItem item : items) {
            // Label kategori separator
            if (!item.getKategori().equals(lastKategori)) {
                lastKategori = item.getKategori();
                JLabel katLabel = new JLabel("── " + lastKategori.toUpperCase() + " ──");
                katLabel.setFont(new Font("Dialog", Font.ITALIC, 11));
                katLabel.setForeground(new Color(100, 100, 100));
                katLabel.setBorder(new EmptyBorder(8, 0, 4, 0));
                itemsPanel.add(katLabel);
            }

            JPanel nameRow = new JPanel(new BorderLayout());
            nameRow.setBackground(new Color(252, 250, 245));
            JLabel nameLabel = new JLabel(item.getNama());
            nameLabel.setFont(new Font("Dialog", Font.BOLD, 13));
            JLabel totalLabel = new JLabel(formatRupiah(item.getTotal()));
            totalLabel.setFont(new Font("Dialog", Font.BOLD, 13));
            nameRow.add(nameLabel, BorderLayout.WEST);
            nameRow.add(totalLabel, BorderLayout.EAST);

            JPanel subRow = new JPanel(new BorderLayout());
            subRow.setBackground(new Color(252, 250, 245));
            JLabel subLabel = new JLabel(item.getKode() + " · " + item.getQty()
                    + " x " + formatRupiah(item.getHarga()));
            subLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
            subLabel.setForeground(Color.DARK_GRAY);
            subRow.add(subLabel, BorderLayout.WEST);

            itemsPanel.add(nameRow);
            itemsPanel.add(subRow);
            itemsPanel.add(Box.createVerticalStrut(8));
        }
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    public void setCustomer(CustomerInfo customer) {
        lblNama.setText(customer.getNama());
        lblStatus.setText(customer.getStatus());
        lblMetode.setText(customer.getMetodeBayar());
        lblCurrency.setText(customer.getCurrency());
        if (lblPoin != null) lblPoin.setText("-");
    }

    public void setSummary(double subtotal, double tax, double discount,
                            double adminFee, double grandTotal) {
        lblSubtotal.setText(formatRupiah(subtotal));
        lblTax.setText(formatRupiah(tax));
        lblBeforeDiscount.setText(formatRupiah(subtotal + tax));
        lblDiscount.setText("-" + formatRupiah(discount));
        lblAdminFee.setText(formatRupiah(adminFee));
        lblGrandTotal.setText(formatRupiah(grandTotal));
    }

    private String formatRupiah(double idr) {
        return "Rp " + String.format("%,.0f", idr).replace(",", ".");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReceiptView().setVisible(true));
    }
}