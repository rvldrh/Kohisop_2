package models;

import java.awt.Color;
import java.awt.Font;

public class AppColors {
    // Warna utama
    public static final Color UTAMA        = new Color(0, 102, 102);    // hijau tua
    public static final Color UTAMA_GELAP  = new Color(0, 70, 70);      // lebih gelap untuk header
    public static final Color UTAMA_MUDA   = new Color(0, 140, 140);    // hover

    // Warna button
    public static final Color BTN_TAMBAH   = new Color(0, 204, 204);    // cyan neon
    public static final Color BTN_SELESAI  = new Color(102, 255, 102);  // hijau terang
    public static final Color BTN_BATAL    = new Color(255, 51, 51);    // merah
    public static final Color BTN_LOGOUT   = new Color(255, 51, 51);    // merah

    // Warna teks
    public static final Color TEKS_PUTIH   = Color.WHITE;
    public static final Color TEKS_GELAP   = new Color(30, 30, 30);
    public static final Color TEKS_HEADER  = Color.WHITE;

    // Warna background
    public static final Color BG_UTAMA     = new Color(240, 248, 248);  // putih kebiruan
    public static final Color BG_PANEL     = new Color(220, 240, 240);  // panel lebih terang
    public static final Color BG_TABLE_HDR = new Color(0, 102, 102);
    public static final Color BG_TABLE_ROW = Color.WHITE;
    public static final Color BG_TABLE_ALT = new Color(230, 245, 245);

    // Font
    public static final Font FONT_JUDUL    = new Font("Arial", Font.BOLD, 20);
    public static final Font FONT_SUBJUDUL = new Font("Arial", Font.BOLD, 14);
    public static final Font FONT_NORMAL   = new Font("Arial", Font.PLAIN, 13);
    public static final Font FONT_BOLD     = new Font("Arial", Font.BOLD, 13);
    public static final Font FONT_BUTTON   = new Font("Arial", Font.BOLD, 12);
    public static final Font FONT_TABLE    = new Font("Arial", Font.PLAIN, 12);
    public static final Font FONT_TABLE_H  = new Font("Arial", Font.BOLD, 12);
}
