package views;

import models.CustomerInfo;
import models.ReceiptItem;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class ReceiptView extends javax.swing.JFrame {

    private JPanel itemsPanel;
    private JLabel lblSubtotal, lblTax, lblBeforeDiscount, lblDiscount, lblAdminFee, lblGrandTotal;
    private JLabel lblNama, lblStatus, lblMetode, lblCurrency;

    public ReceiptView() {
        setTitle("Receipt — KohiSop");
        setSize(360, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(240, 240, 240));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(252, 250, 245));
        card.setBorder(new LineBorder(new Color(210, 210, 210), 1, true));

        card.add(createHeader());
        card.add(createSectionTitle("🍽 ITEM PESANAN"));

        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(new Color(252, 250, 245));
        itemsPanel.setBorder(new EmptyBorder(0, 16, 0, 16));
        card.add(itemsPanel);

        card.add(createDashedLine());
        card.add(createSectionTitle("🧾 RINGKASAN BIAYA"));
        card.add(createSummary());
        card.add(createDashedLine());
        card.add(createSectionTitle("👤 INFO PELANGGAN & PEMBAYARAN"));
        card.add(createCustomerInfo());
        card.add(Box.createVerticalStrut(20));

        JLabel footer = new JLabel(
                "<html><center><b>Terima kasih & silakan datang kembali!</b><br>Simpan struk ini sebagai bukti pembayaran</center></html>");
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);
        footer.setForeground(new Color(0, 120, 90));
        footer.setFont(new Font("Dialog", Font.PLAIN, 12));
        card.add(footer);
        card.add(Box.createVerticalStrut(20));

        JScrollPane scroll = new JScrollPane(card);
        root.add(scroll, BorderLayout.CENTER);
        setContentPane(root);
    }

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
        JLabel label = new JLabel(text);
        label.setBorder(new EmptyBorder(12, 16, 8, 16));
        label.setFont(new Font("Dialog", Font.BOLD, 13));
        return label;
    }

    private JPanel createSummary() {
        JPanel p = createKeyValuePanel();
        lblSubtotal      = createValueLabel();
        lblTax           = createValueLabel();
        lblBeforeDiscount= createValueLabel();
        lblDiscount      = createValueLabel(new Color(0, 140, 80));
        lblAdminFee      = createValueLabel();
        lblGrandTotal    = createValueLabel();

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
        lblNama    = createValueLabel();
        lblStatus  = createValueLabel();
        lblMetode  = createValueLabel();
        lblCurrency= createValueLabel();
        p.add(row("Nama",        lblNama));
        p.add(row("Status",      lblStatus));
        p.add(row("Metode bayar",lblMetode));
        p.add(row("Mata uang",   lblCurrency));
        return p;
    }

    private JPanel createKeyValuePanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(252, 250, 245));
        p.setBorder(new EmptyBorder(0, 16, 0, 16));
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
        JPanel row = new JPanel(new BorderLayout());
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

    // ===== METHODS UNTUK CONTROLLER =====

    public void setItems(List<ReceiptItem> items) {
        itemsPanel.removeAll();
        for (ReceiptItem item : items) {
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
