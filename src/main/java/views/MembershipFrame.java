package views;

import controllers.MembershipController;
import models.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class MembershipFrame extends JFrame {

    private MembershipController controller;

    // ── Tab: Membership ───────────────────────────────────────────────────────
    private JTextField txtNama, txtEmail, txtNoTelp, txtCariMember, txtKodeMemberDisplay;
    private JTable tblMember;
    private DefaultTableModel tblMemberModel;
    private String selectedKode = null;

    // ── Tab: Pembayaran ───────────────────────────────────────────────────────
    private JLabel lblSubtotalInfo;
    private JTextField txtKodeMemberBayar, txtPoinDigunakan;
    private JComboBox<String> cbMetode, cbEMoney, cbMataUang;
    private JTextArea txtHasil;
    private JLabel lblPoinInfo;

    // ── Tab: Konversi ─────────────────────────────────────────────────────────
    private JTextField txtNilaiKonversi;
    private JComboBox<String> cbDari, cbKe;
    private JLabel lblHasilKonversi;

    private User userAktif;
    private double subtotalIDR; // subtotal sebelum pajak, dalam IDR penuh

    public MembershipFrame(User user, double subtotal, java.util.List<CartItem> cartItems) {
        this.userAktif   = user;
        this.subtotalIDR = subtotal;
        this.controller  = new MembershipController(user, cartItems);

        setTitle("Membership & Pembayaran — KohiSop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(820, 640);
        setLocationRelativeTo(null);

        buildUI();

        // Tampilkan subtotal di tab pembayaran (read-only)
        lblSubtotalInfo.setText("Subtotal: " + formatRupiah(subtotalIDR) + " (belum termasuk pajak)");

        controller.setView(this);
    }

    private void buildUI() {
        JPanel header = buildHeader();
        add(header, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(AppColors.FONT_BOLD);
        tabs.setBackground(AppColors.BG_UTAMA);

        tabs.addTab("👥 Membership", buildTabMembership());
        tabs.addTab("💳 Pembayaran", buildTabPembayaran());
        tabs.addTab("💱 Konversi",   buildTabKonversi());

        add(tabs, BorderLayout.CENTER);
        getContentPane().setBackground(AppColors.BG_UTAMA);
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(AppColors.UTAMA_GELAP);
        p.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel title = new JLabel("☕ KohiSop — Membership & Pembayaran");
        title.setFont(AppColors.FONT_JUDUL);
        title.setForeground(Color.WHITE);

        JButton btnLogout = styledButton("🔓 Logout", AppColors.BTN_LOGOUT, AppColors.TEKS_GELAP);
        btnLogout.addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", "Logout",
                    JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) System.exit(0);
        });

        p.add(title, BorderLayout.WEST);
        p.add(btnLogout, BorderLayout.EAST);
        return p;
    }

    // ── TAB MEMBERSHIP ────────────────────────────────────────────────────────
    private JPanel buildTabMembership() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(AppColors.BG_UTAMA);
        p.setBorder(new EmptyBorder(12, 14, 12, 14));
        p.add(buildFormMember(), BorderLayout.NORTH);
        p.add(buildTableMember(), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildFormMember() {
        JPanel outer = new JPanel(new BorderLayout(0, 8));
        outer.setBackground(AppColors.BG_UTAMA);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppColors.BG_PANEL);
        form.setBorder(titledBorder("Data Member"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets  = new Insets(5, 8, 5, 8);
        g.anchor  = GridBagConstraints.WEST;
        g.fill    = GridBagConstraints.HORIZONTAL;

        txtNama              = styledField(20);
        txtEmail             = styledField(20);
        txtNoTelp            = styledField(20);
        txtCariMember        = styledField(15);
        txtKodeMemberDisplay = styledField(15);
        txtKodeMemberDisplay.setEditable(false);
        txtKodeMemberDisplay.setBackground(new Color(220, 240, 240));

        addFormRow(form, g, "Nama",         txtNama,              0);
        addFormRow(form, g, "Email",        txtEmail,             1);
        addFormRow(form, g, "No. Telp",     txtNoTelp,            2);
        addFormRow(form, g, "Kode Member",  txtKodeMemberDisplay, 3);

        JPanel cariPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        cariPanel.setBackground(AppColors.BG_PANEL);
        cariPanel.add(label("Cari (Kode/Nama):"));
        cariPanel.add(txtCariMember);
        JButton btnCari = styledButton("🔍 Cari", AppColors.UTAMA, Color.WHITE);
        btnCari.addActionListener(e -> controller.cariMember(txtCariMember.getText().trim()));
        cariPanel.add(btnCari);

        g.gridx = 0; g.gridy = 4; g.gridwidth = 2;
        form.add(cariPanel, g);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        btnPanel.setBackground(AppColors.BG_PANEL);

        JButton btnTambah = styledButton("➕ Tambah Member", AppColors.BTN_TAMBAH, AppColors.TEKS_GELAP);
        JButton btnHapus  = styledButton("🗑 Hapus",         AppColors.BTN_BATAL,  Color.WHITE);
        JButton btnBatal  = styledButton("✖ Batal",          AppColors.BTN_BATAL,  Color.WHITE);
        JButton btnReset  = styledButton("🔄 Reset",          AppColors.UTAMA,      Color.WHITE);

        btnTambah.addActionListener(e -> controller.tambahMember(
                txtNama.getText().trim(), txtEmail.getText().trim(), txtNoTelp.getText().trim()));
        btnHapus.addActionListener(e -> controller.hapusMember(selectedKode));
        btnBatal.addActionListener(e -> clearInputMember());
        btnReset.addActionListener(e -> { clearInputMember(); selectedKode = null; });

        btnPanel.add(btnTambah); btnPanel.add(btnHapus);
        btnPanel.add(btnBatal);  btnPanel.add(btnReset);

        g.gridx = 0; g.gridy = 5; g.gridwidth = 2;
        form.add(btnPanel, g);

        outer.add(form, BorderLayout.CENTER);
        return outer;
    }

    private JScrollPane buildTableMember() {
        tblMemberModel = new DefaultTableModel(MemberModel.TABLE_COLUMNS, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblMember = new JTable(tblMemberModel);
        styleTable(tblMember);

        tblMember.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblMember.getSelectedRow();
                if (row >= 0) {
                    selectedKode = tblMemberModel.getValueAt(row, 0).toString();
                    txtKodeMemberDisplay.setText(selectedKode);
                    txtNama.setText(tblMemberModel.getValueAt(row, 1).toString());
                    txtEmail.setText(tblMemberModel.getValueAt(row, 2).toString());
                    txtNoTelp.setText(tblMemberModel.getValueAt(row, 3).toString());
                }
            }
        });

        JScrollPane sp = new JScrollPane(tblMember);
        sp.setBorder(titledBorder("Daftar Member"));
        return sp;
    }

    // ── TAB PEMBAYARAN ────────────────────────────────────────────────────────
    private JPanel buildTabPembayaran() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(AppColors.BG_UTAMA);
        p.setBorder(new EmptyBorder(12, 14, 12, 14));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppColors.BG_PANEL);
        form.setBorder(titledBorder("Input Pembayaran"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8);
        g.anchor = GridBagConstraints.WEST;
        g.fill   = GridBagConstraints.HORIZONTAL;

        // Subtotal ditampilkan saja, tidak bisa diubah user
        lblSubtotalInfo      = new JLabel("-");
        lblSubtotalInfo.setFont(AppColors.FONT_BOLD);
        lblSubtotalInfo.setForeground(AppColors.UTAMA_GELAP);

        txtKodeMemberBayar   = styledField(18);
        txtPoinDigunakan     = styledField(18);
        txtPoinDigunakan.setText("0");

        cbMetode   = styledCombo(new String[]{"Tunai", "QRIS", "E-Money"});
        cbEMoney   = styledCombo(new String[]{"GoPay", "OVO", "Dana", "ShopeePay"});
        cbMataUang = styledCombo(Currencies.KODE_LIST);
        lblPoinInfo = label("Poin tersedia: -");

        cbMetode.addActionListener(e -> cbEMoney.setVisible("E-Money".equals(cbMetode.getSelectedItem())));
        cbEMoney.setVisible(false);

        txtKodeMemberBayar.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                Member m = controller.getMemberByKode(txtKodeMemberBayar.getText().trim());
                lblPoinInfo.setText(m != null
                        ? "Poin tersedia: " + m.getPoin() + " (= Rp " + (m.getPoin() * 2) + ")"
                        : "Member tidak ditemukan");
            }
        });

        addFormRow(form, g, "Subtotal Keranjang", lblSubtotalInfo,      0);
        addFormRow(form, g, "Metode",             cbMetode,             1);
        addFormRow(form, g, "E-Money",            cbEMoney,             2);
        addFormRow(form, g, "Kode Member",        txtKodeMemberBayar,   3);
        addFormRow(form, g, "",                   lblPoinInfo,          4);
        addFormRow(form, g, "Poin Digunakan",     txtPoinDigunakan,     5);
        addFormRow(form, g, "Mata Uang",          cbMataUang,           6);

        JButton btnBayar  = styledButton("✅ Proses Pembayaran", AppColors.BTN_SELESAI, AppColors.TEKS_GELAP);
        JButton btnBatalB = styledButton("✖ Batal",              AppColors.BTN_BATAL,   Color.WHITE);

        btnBayar.addActionListener(e  -> prosesKlikBayar());
        btnBatalB.addActionListener(e -> resetFormBayar());

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        btnRow.setBackground(AppColors.BG_PANEL);
        btnRow.add(btnBayar); btnRow.add(btnBatalB);

        g.gridx = 0; g.gridy = 7; g.gridwidth = 2;
        form.add(btnRow, g);

        txtHasil = new JTextArea(18, 30);
        txtHasil.setFont(new Font("Courier New", Font.PLAIN, 12));
        txtHasil.setEditable(false);
        txtHasil.setBackground(new Color(15, 35, 35));
        txtHasil.setForeground(new Color(100, 255, 180));
        txtHasil.setBorder(new EmptyBorder(10, 12, 10, 12));
        txtHasil.setText("Rincian pembayaran akan muncul di sini...");
        JScrollPane spHasil = new JScrollPane(txtHasil);
        spHasil.setBorder(titledBorder("Rincian Transaksi"));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, spHasil);
        split.setDividerLocation(340);
        split.setBackground(AppColors.BG_UTAMA);

        p.add(split, BorderLayout.CENTER);
        return p;
    }

    private void prosesKlikBayar() {
        try {
            String metode   = cbMetode.getSelectedItem().toString();
            String emoney   = cbEMoney.getSelectedItem().toString();
            String kodeMbr  = txtKodeMemberBayar.getText().trim();
            int poin        = Integer.parseInt(txtPoinDigunakan.getText().trim());
            String matauang = cbMataUang.getSelectedItem().toString();
            controller.prosesPembayaran(metode, emoney, kodeMbr, poin, matauang);
        } catch (NumberFormatException ex) {
            showPesan("Poin harus berupa angka!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFormBayar() {
        txtKodeMemberBayar.setText("");
        txtPoinDigunakan.setText("0");
        lblPoinInfo.setText("Poin tersedia: -");
        txtHasil.setText("Rincian pembayaran akan muncul di sini...");
        cbMetode.setSelectedIndex(0);
    }

    // ── TAB KONVERSI ──────────────────────────────────────────────────────────
    private JPanel buildTabKonversi() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(AppColors.BG_UTAMA);
        p.setBorder(new EmptyBorder(20, 40, 20, 40));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(AppColors.BG_PANEL);
        card.setBorder(titledBorder("Konversi Mata Uang"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 10, 8, 10);
        g.anchor = GridBagConstraints.WEST;
        g.fill   = GridBagConstraints.HORIZONTAL;

        txtNilaiKonversi  = styledField(15);
        cbDari            = styledCombo(Currencies.KODE_LIST);
        cbKe              = styledCombo(Currencies.KODE_LIST);
        cbKe.setSelectedIndex(1);
        lblHasilKonversi  = label("Hasil konversi muncul di sini");
        lblHasilKonversi.setFont(new Font("Arial", Font.BOLD, 15));
        lblHasilKonversi.setForeground(AppColors.UTAMA);

        addFormRow(card, g, "Nilai",   txtNilaiKonversi, 0);
        addFormRow(card, g, "Dari",    cbDari,           1);
        addFormRow(card, g, "Ke",      cbKe,             2);
        addFormRow(card, g, "Hasil",   lblHasilKonversi, 3);

        JButton btnKonversi = styledButton("🔄 Konversi", AppColors.BTN_TAMBAH, AppColors.TEKS_GELAP);
        btnKonversi.addActionListener(e -> prosesKonversi());
        g.gridx = 0; g.gridy = 4; g.gridwidth = 2;
        card.add(btnKonversi, g);

        p.add(card);
        return p;
    }

    private void prosesKonversi() {
        try {
            double nilai  = Double.parseDouble(txtNilaiKonversi.getText().trim());
            Currency dari = Currencies.get(cbDari.getSelectedItem().toString());
            Currency ke   = Currencies.get(cbKe.getSelectedItem().toString());
            double hasil  = ke.dariIDR(dari.keIDR(nilai));
            lblHasilKonversi.setText(String.format("%.2f %s = %s %.4f",
                    nilai, dari.getKode(), ke.getSimbol(), hasil));
        } catch (NumberFormatException ex) {
            lblHasilKonversi.setText("Input tidak valid!");
        }
    }

    // ── Public API (dipanggil controller) ─────────────────────────────────────
    public void refreshTableMember(Object[][] data, String[] cols) {
        tblMemberModel.setDataVector(data, cols);
        styleTableHeader(tblMember);
    }

    public void isiFormMember(Member m) {
        txtKodeMemberDisplay.setText(m.getKodeMember());
        txtNama.setText(m.getNama());
        txtEmail.setText(m.getEmail());
        txtNoTelp.setText(m.getNoTelp());
        selectedKode = m.getKodeMember();
    }

    public void clearInputMember() {
        txtNama.setText(""); txtEmail.setText(""); txtNoTelp.setText("");
        txtCariMember.setText(""); txtKodeMemberDisplay.setText("");
        selectedKode = null;
    }

    public void tampilkanRincianBayar(PaymentModel.RincianBayar r) {
        String sep = "─".repeat(38);
        boolean idrMode = "IDR".equals(r.currencyKode);

        StringBuilder sb = new StringBuilder();
        sb.append("  ╔══════════════════════════════════╗\n");
        sb.append("  ║       KUITANSI KOHISOP           ║\n");
        sb.append("  ╚══════════════════════════════════╝\n");
        sb.append(sep).append("\n");
        sb.append(String.format("  Metode       : %s%n", r.metodeName));
        sb.append(sep).append("\n");
        sb.append(String.format("  Subtotal     : %s%n",  formatRupiah(r.subtotalSebelumPajak)));
        sb.append(String.format("  Pajak        : %s%n",  formatRupiah(r.totalPajak)));
        sb.append(String.format("  Stlh Pajak   : %s%n",  formatRupiah(r.subtotalSetelahPajak)));
        sb.append(String.format("  Diskon Ch.   : -%s%n", formatRupiah(r.diskonChannel)));
        if (r.adminFee > 0)
            sb.append(String.format("  Admin Fee    : +%s%n", formatRupiah(r.adminFee)));
        if (r.diskonPoin > 0)
            sb.append(String.format("  Diskon Poin  : -%s%n", formatRupiah(r.diskonPoin)));
        sb.append(sep).append("\n");
        sb.append(String.format("  TOTAL (IDR)  : %s%n",  formatRupiah(r.totalIDR)));
        if (!idrMode)
            sb.append(String.format("  TOTAL (%s)  : %s %.4f%n",
                    r.currencyKode, r.currencySimbol, r.totalConverted));
        sb.append(sep).append("\n");
        if (r.poinSebelum >= 0 || r.poinDiperoleh > 0) {
            sb.append(String.format("  Poin Sebelum : %d%n", r.poinSebelum));
            sb.append(String.format("  Poin Diperoleh: +%d%n", r.poinDiperoleh));
            sb.append(String.format("  Poin Setelah : %d%n", r.poinSetelah));
            sb.append(sep).append("\n");
        }
        sb.append("  Terima kasih & silakan datang kembali!\n");

        txtHasil.setText(sb.toString());
    }

    public void showPesan(String msg, String judul, int type) {
        JOptionPane.showMessageDialog(this, msg, judul, type);
    }

    public int showKonfirmasi(String msg) {
        return JOptionPane.showConfirmDialog(this, msg, "Konfirmasi",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    // ── UI Helpers ────────────────────────────────────────────────────────────
    private String formatRupiah(double idr) {
        return "Rp " + String.format("%,.0f", idr).replace(",", ".");
    }

    private JButton styledButton(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setFont(AppColors.FONT_BUTTON);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorder(new CompoundBorder(new LineBorder(bg.darker(), 1, true), new EmptyBorder(6, 14, 6, 14)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(bg.brighter()); }
            public void mouseExited(MouseEvent e)  { b.setBackground(bg); }
        });
        return b;
    }

    private JTextField styledField(int cols) {
        JTextField f = new JTextField(cols);
        f.setFont(AppColors.FONT_NORMAL);
        f.setBorder(new CompoundBorder(new LineBorder(AppColors.UTAMA, 1, true), new EmptyBorder(4, 8, 4, 8)));
        return f;
    }

    private <T> JComboBox<T> styledCombo(T[] items) {
        JComboBox<T> cb = new JComboBox<>(items);
        cb.setFont(AppColors.FONT_NORMAL);
        cb.setBackground(Color.WHITE);
        return cb;
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(AppColors.FONT_NORMAL);
        return l;
    }

    private TitledBorder titledBorder(String title) {
        TitledBorder b = BorderFactory.createTitledBorder(new LineBorder(AppColors.UTAMA, 1, true), title);
        b.setTitleFont(AppColors.FONT_BOLD);
        b.setTitleColor(AppColors.UTAMA);
        return b;
    }

    private void addFormRow(JPanel p, GridBagConstraints g, String labelText, Component comp, int row) {
        g.gridx = 0; g.gridy = row; g.gridwidth = 1; g.weightx = 0;
        p.add(label(labelText), g);
        g.gridx = 1; g.weightx = 1;
        p.add(comp, g);
    }

    private void styleTable(JTable t) {
        t.setFont(AppColors.FONT_TABLE);
        t.setRowHeight(26);
        t.setGridColor(new Color(200, 230, 230));
        t.setSelectionBackground(AppColors.UTAMA_MUDA);
        t.setSelectionForeground(Color.WHITE);
        t.setShowHorizontalLines(true);
        t.setShowVerticalLines(false);
        styleTableHeader(t);
    }

    private void styleTableHeader(JTable t) {
        JTableHeader h = t.getTableHeader();
        h.setFont(AppColors.FONT_TABLE_H);
        h.setBackground(AppColors.UTAMA);
        h.setForeground(Color.WHITE);
        h.setReorderingAllowed(false);
    }
}