/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author Fiqih
 */
public class MenuModel {
    
    private ArrayList<Menu> daftarMenu;

    public MenuModel() {
        daftarMenu = new ArrayList<>();
        inisialisasiMenuBawaan();
    }

    private void inisialisasiMenuBawaan() {
        // Makanan
        daftarMenu.add(new Menu("M1", "Petemania Pizza", 112, "Makanan"));
        daftarMenu.add(new Menu("M2", "Mie Rebus Super Mario", 35, "Makanan"));
        daftarMenu.add(new Menu("M3", "Ayam Bakar Goreng Rebus Spesial", 72, "Makanan"));
        daftarMenu.add(new Menu("M4", "Soto Kambing Iga Guling", 124, "Makanan"));
        daftarMenu.add(new Menu("S1", "Singkong Bakar A La Carte", 37, "Makanan"));
        daftarMenu.add(new Menu("S2", "Ubi Cilembu Bakar Arang", 58, "Makanan"));
        daftarMenu.add(new Menu("S3", "Tempe Mendoan", 18, "Makanan"));
        daftarMenu.add(new Menu("S4", "Tahu Bakso Extra Telur", 28, "Makanan"));
        
        // Minuman
        daftarMenu.add(new Menu("A1", "Caffe Latte", 46, "Minuman"));
        daftarMenu.add(new Menu("A2", "Cappuccino", 46, "Minuman"));
        daftarMenu.add(new Menu("E1", "Caffe Americano", 37, "Minuman"));
        daftarMenu.add(new Menu("E2", "Caffe Mocha", 55, "Minuman"));
        daftarMenu.add(new Menu("E3", "Caramel Macchiato", 59, "Minuman"));
        daftarMenu.add(new Menu("E4", "Asian Dolce Latte", 55, "Minuman"));
        daftarMenu.add(new Menu("E5", "Double Shots Iced Shaken Espresso", 50, "Minuman"));
        daftarMenu.add(new Menu("B1", "Freshly Brewed Coffee", 23, "Minuman"));
        daftarMenu.add(new Menu("B2", "Vanilla Sweet Cream Cold Brew", 44, "Minuman"));
        daftarMenu.add(new Menu("B3", "Cold Brew", 50, "Minuman"));
    }

    // ATURAN DOSEN KELOMPOK 1: Jenisnya dulu, lalu berdasarkan KODE menu (A-Z)
    public ArrayList<Menu> getMenuTerurutKode() {
        ArrayList<Menu> sortedMenu = new ArrayList<>(daftarMenu);
        Collections.sort(sortedMenu, (m1, m2) -> {
            // 1. Cek Kategorinya dulu
            boolean isMakanan1 = m1.getKategori().equalsIgnoreCase("Makanan");
            boolean isMakanan2 = m2.getKategori().equalsIgnoreCase("Makanan");

            // Jika m1 Makanan dan m2 Minuman, m1 harus di atas (Return negatif)
            if (isMakanan1 && !isMakanan2) {
                return -1; 
            }
            // Jika m1 Minuman dan m2 Makanan, m2 harus di atas (Return positif)
            if (!isMakanan1 && isMakanan2) {
                return 1;
            }
            
            // 2. Jika Kategorinya SAMA, baru urutkan berdasarkan KODE (A-Z)
            return m1.getKode().compareToIgnoreCase(m2.getKode());
        });
        return sortedMenu;
    }

    public Menu cariMenu(String kode) {
        for (Menu m : daftarMenu) {
            if (m.getKode().equalsIgnoreCase(kode)) return m;
        }
        return null;
    }
    
    public ArrayList<Menu> getMenuMakananTerurut() {
        ArrayList<Menu> makanan = new ArrayList<>();
        for (Menu m : daftarMenu) {
            if (m.getKategori().equalsIgnoreCase("Makanan")) {
                makanan.add(m);
            }
        }
        // Urutkan berdasarkan KODE (A-Z)
        Collections.sort(makanan, (m1, m2) -> m1.getKode().compareToIgnoreCase(m2.getKode()));
        return makanan;
    }

    // Method untuk mengambil khusus Minuman dan diurutkan Kodenya
    public ArrayList<Menu> getMenuMinumanTerurut() {
        ArrayList<Menu> minuman = new ArrayList<>();
        for (Menu m : daftarMenu) {
            if (m.getKategori().equalsIgnoreCase("Minuman")) {
                minuman.add(m);
            }
        }
        // Urutkan berdasarkan KODE (A-Z)
        Collections.sort(minuman, (m1, m2) -> m1.getKode().compareToIgnoreCase(m2.getKode()));
        return minuman;
    }
    
}
