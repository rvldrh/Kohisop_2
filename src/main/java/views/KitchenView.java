/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views; // Sesuaikan dengan nama package Anda di NetBeans

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.*;
import java.awt.geom.RoundRectangle2D;
/**
 *
 * @author rvldrh
 */

class SidebarButton extends JButton {

    public SidebarButton(String text) {
        super(text);
        setMaximumSize(new Dimension(200, 60));
        setPreferredSize(new Dimension(200, 60));
        setHorizontalAlignment(SwingConstants.LEFT);
        setFont(new Font("Segoe UI", Font.PLAIN, 20));
        setForeground(Color.BLACK);
        setBackground(new Color(20, 130, 96));
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
    }
}

class RoundedButton extends JButton {

    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setFont(new Font("Segoe UI", Font.BOLD, 18));
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(new Color(250, 250, 248));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

        g2.setColor(new Color(190, 190, 190));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);

        g2.dispose();
        super.paintComponent(g);
    }
}

class RoundedPanel extends JPanel {

    private int radius;

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        Shape shape = new RoundRectangle2D.Float(
                0, 0, getWidth(), getHeight(), radius, radius
        );

        g2.setColor(getBackground());
        g2.fill(shape);

        g2.setColor(new Color(220, 220, 220));
        g2.draw(shape);

        g2.dispose();
        super.paintComponent(g);
    }
}

class FoodTableRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        if (column == 3) {
            String text = value.toString();

            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
            wrapper.setBackground(Color.WHITE);

            PillLabel pill = new PillLabel(text);
            wrapper.add(pill);

            return wrapper;
        }

        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setText("<html><center>" + value.toString().replace("\n", "<br>") + "</center></html>");

        return label;
    }
}

class PillLabel extends JLabel {

    private Color bg;
    private Color fg;

    public PillLabel(String text) {
        super(text);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        setOpaque(false);

        switch (text) {
            case "Tinggi":
                bg = new Color(248, 225, 228);
                fg = new Color(199, 82, 96);
                break;
            case "Sedang":
                bg = new Color(248, 237, 218);
                fg = new Color(177, 118, 29);
                break;
            default:
                bg = new Color(228, 239, 215);
                fg = new Color(90, 145, 62);
                break;
        }

        setForeground(fg);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(bg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        g2.dispose();
        super.paintComponent(g);
    }
}

public class KitchenView extends javax.swing.JFrame { // Tambahkan deklarasi class dan extends JFrame

    private JPanel drinkListPanel;
    private JTable foodTable;
    private JLabel customerLabel;

    private JButton btnOrder;
    private JButton btnMember;
    private JButton btnKitchen;
    private JButton btnLogout;

    public KitchenView() {
        setTitle("Kitchen Queue");
        setSize(1280, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 242));

        add(createSidebar(), BorderLayout.WEST);
        add(createContent(), BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(230, 760));
        sidebar.setBackground(new Color(0, 110, 110));
        sidebar.setLayout(new BorderLayout());

        // TOP SECTION
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        JLabel title = new JLabel("KohiSop");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(title);
        topPanel.add(Box.createVerticalStrut(50));

        JSeparator line = new JSeparator();
        line.setForeground(Color.WHITE);
        line.setMaximumSize(new Dimension(180, 2));
        topPanel.add(line);

        topPanel.add(Box.createVerticalStrut(70));

        btnOrder = createOldStyleButton("Order");
        btnMember = createOldStyleButton("Member");
        btnKitchen = createOldStyleButton("Kitchen");

        topPanel.add(btnOrder);
        topPanel.add(Box.createVerticalStrut(45));
        topPanel.add(btnMember);
        topPanel.add(Box.createVerticalStrut(45));
        topPanel.add(btnKitchen);

        // BOTTOM SECTION
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 35, 30, 35));
        bottomPanel.setLayout(new BorderLayout());

        btnLogout = new JButton("Logout");
        btnLogout.setBackground(Color.RED);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        bottomPanel.add(btnLogout);

        sidebar.add(topPanel, BorderLayout.NORTH);
        sidebar.add(bottomPanel, BorderLayout.SOUTH);

        return sidebar;
    }

    private JButton createOldStyleButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(140, 50));
        btn.setPreferredSize(new Dimension(140, 50));
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setFocusPainted(false);
        return btn;
    }

    public void setCustomerAktif(int jumlah) {
        customerLabel.setText(
                "<html>Customer aktif: <b>" + jumlah
                + " pelanggan</b> — setelah 3 pelanggan, dapur diproses</html>"
        );
    }

    private JPanel createContent() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(248, 248, 246));

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 70));
        header.setBackground(new Color(248, 248, 246));
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel title = new JLabel("🍴 Kitchen Queue");
//        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setFont(new Font("Dialog", Font.BOLD, 30));

        JButton dots = new JButton("⋯");
        dots.setFocusPainted(false);
//        dots.setFont(new Font("Segoe UI", Font.BOLD, 20));
        dots.setFont(new Font("Dialog", Font.BOLD, 20));

        header.add(title, BorderLayout.WEST);
        header.add(dots, BorderLayout.EAST);

        JPanel body = new JPanel();
        body.setBackground(new Color(248, 248, 246));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);

        customerLabel = new JLabel();
        customerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        setCustomerAktif(0);
        infoPanel.add(customerLabel, BorderLayout.WEST);

        RoundedButton processBtn = new RoundedButton("▷ Proses Dapur");
        processBtn.setPreferredSize(new Dimension(220, 70));
        
        infoPanel.add(processBtn, BorderLayout.EAST);

        body.add(Box.createVerticalStrut(20));
        body.add(infoPanel);
        body.add(Box.createVerticalStrut(30));

        JPanel sectionTitles = new JPanel(new GridLayout(1, 2, 20, 0));
        sectionTitles.setOpaque(false);

        JLabel foodLabel = new JLabel("FOOD QUEUE — PRIORITY (HARGA TERTINGGI)");
        foodLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        foodLabel.setForeground(new Color(80, 80, 80));

        JLabel drinkLabel = new JLabel("DRINK STACK — LIFO (LAST ORDERED FIRST SERVED)");
        drinkLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        drinkLabel.setForeground(new Color(80, 80, 80));

        sectionTitles.add(foodLabel);
        sectionTitles.add(drinkLabel);

        body.add(sectionTitles);
        body.add(Box.createVerticalStrut(15));

        JPanel queuePanels = new JPanel(new GridLayout(1, 2, 20, 0));
        queuePanels.setOpaque(false);

        queuePanels.add(createFoodPanel());
        queuePanels.add(createDrinkPanel());

        body.add(queuePanels);
        body.add(Box.createVerticalStrut(20));

        JLabel footer = new JLabel("ⓘ PriorityQueue untuk makanan | Stack untuk minuman");
//        footer.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        footer.setFont(new Font("Dialog", Font.PLAIN, 18));
        footer.setForeground(new Color(70, 70, 70));

        body.add(footer);

        root.add(header, BorderLayout.NORTH);
        root.add(body, BorderLayout.CENTER);

        return root;
    }

    private JPanel createFoodPanel() {
        RoundedPanel panel = new RoundedPanel(18);
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(new Color(16, 125, 105));
        top.setPreferredSize(new Dimension(0, 50));

        JLabel title = new JLabel("☕ Antrian Makanan");
        title.setForeground(Color.WHITE);
//        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setFont(new Font("Dialog", Font.BOLD, 20));

        top.add(title);

        String[] cols = {"#", "Nama", "Harga", "Prioritas"};

        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        foodTable = new JTable(model);
        foodTable.setRowHeight(85);
        foodTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        foodTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        foodTable.setShowGrid(false);
        foodTable.setIntercellSpacing(new Dimension(0, 0));
        foodTable.setDefaultRenderer(Object.class, new FoodTableRenderer());

        JScrollPane scroll = new JScrollPane(foodTable);
        scroll.setBorder(null);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDrinkPanel() {
        RoundedPanel panel = new RoundedPanel(18);
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(new Color(196, 126, 15));
        top.setPreferredSize(new Dimension(0, 50));

        JLabel title = new JLabel("☕ Antrian Minuman");
        title.setForeground(Color.WHITE);
//        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setFont(new Font("Dialog", Font.BOLD, 20));

        top.add(title);

        drinkListPanel = new JPanel();
        drinkListPanel.setLayout(new BoxLayout(drinkListPanel, BoxLayout.Y_AXIS));
        drinkListPanel.setBackground(Color.WHITE);

        panel.add(top, BorderLayout.NORTH);
        panel.add(drinkListPanel, BorderLayout.CENTER);

        return panel;
    }

    private void loadDummyDrinks() {
        drinkListPanel.add(createDrinkItem(1, "Asian Dolce\nLatte", "Terakhir\ndipesan"));
        drinkListPanel.add(createDrinkItem(2, "Caffe Mocha", null));
        drinkListPanel.add(createDrinkItem(3, "Caffe Latte", null));
        drinkListPanel.add(createDrinkItem(4, "Cold\nBrew", "Pertama\ndipesan"));
    }
    
    private JPanel createDrinkItem(int no, String name, String status) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.setOpaque(false);

        JLabel badge = new JLabel(String.valueOf(no), SwingConstants.CENTER);
        badge.setPreferredSize(new Dimension(34, 34));
        badge.setOpaque(true);
        badge.setBackground(new Color(213, 243, 233));
        badge.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel nameLabel = new JLabel("<html>" + name.replace("\n", "<br>") + "</html>");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        left.add(badge);
        left.add(Box.createHorizontalStrut(8));
        left.add(nameLabel);

        row.add(left, BorderLayout.WEST);

        if (status != null) {
            JLabel statusLabel = new JLabel(
                    "<html><center>" + status.replace("\n", "<br>") + "</center></html>"
            );
            statusLabel.setOpaque(true);
            statusLabel.setBackground(new Color(240, 238, 232));
            statusLabel.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            row.add(statusLabel, BorderLayout.EAST);
        }

        return row;
    }

    public JTable getFoodTable() {
        return foodTable;
    }

    public JButton getBtnOrder() {
        return btnOrder;
    }
    
    public JButton getBtnMember() {
        return btnMember;
    }
    
    public JButton getBtnLogout() {
        return btnLogout;
    }

    public void clearDrinkList() {
        drinkListPanel.removeAll();
        drinkListPanel.revalidate();
        drinkListPanel.repaint();
    }

    public void addDrinkItem(int no, String name, String status) {
        drinkListPanel.add(createDrinkItem(no, name, status));
        drinkListPanel.revalidate();
        drinkListPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KitchenView view = new KitchenView();
            view.setVisible(true);
        });
    }
}
