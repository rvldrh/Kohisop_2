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
        daftarMenu.add(new Menu("M1", "Petemania Pizza",                      112_000, "Makanan"));
        daftarMenu.add(new Menu("M2", "Mie Rebus Super Mario",                 35_000, "Makanan"));
        daftarMenu.add(new Menu("M3", "Ayam Bakar Goreng Rebus Spesial",       72_000, "Makanan"));
        daftarMenu.add(new Menu("M4", "Soto Kambing Iga Guling",              124_000, "Makanan"));
        daftarMenu.add(new Menu("S1", "Singkong Bakar A La Carte",             37_000, "Makanan"));
        daftarMenu.add(new Menu("S2", "Ubi Cilembu Bakar Arang",               58_000, "Makanan"));
        daftarMenu.add(new Menu("S3", "Tempe Mendoan",                         18_000, "Makanan"));
        daftarMenu.add(new Menu("S4", "Tahu Bakso Extra Telur",                28_000, "Makanan"));

        daftarMenu.add(new Menu("A1", "Caffe Latte",                           46_000, "Minuman"));
        daftarMenu.add(new Menu("A2", "Cappuccino",                            46_000, "Minuman"));
        daftarMenu.add(new Menu("E1", "Caffe Americano",                       37_000, "Minuman"));
        daftarMenu.add(new Menu("E2", "Caffe Mocha",                           55_000, "Minuman"));
        daftarMenu.add(new Menu("E3", "Caramel Macchiato",                     59_000, "Minuman"));
        daftarMenu.add(new Menu("E4", "Asian Dolce Latte",                     55_000, "Minuman"));
        daftarMenu.add(new Menu("E5", "Double Shots Iced Shaken Espresso",     50_000, "Minuman"));
        daftarMenu.add(new Menu("B1", "Freshly Brewed Coffee",                 23_000, "Minuman"));
        daftarMenu.add(new Menu("B2", "Vanilla Sweet Cream Cold Brew",         50_000, "Minuman"));
        daftarMenu.add(new Menu("B3", "Cold Brew",                             44_000, "Minuman"));
    }

    public ArrayList<Menu> getMenuTerurutHarga() {
        ArrayList<Menu> sortedMenu = new ArrayList<>(daftarMenu);
        Collections.sort(sortedMenu, (m1, m2) -> {
            boolean isMakanan1 = m1.getKategori().equalsIgnoreCase("Makanan");
            boolean isMakanan2 = m2.getKategori().equalsIgnoreCase("Makanan");
            
            if (isMakanan1 && !isMakanan2) return -1;
            if (!isMakanan1 && isMakanan2) return 1;
            
            return Double.compare(m1.getHarga(), m2.getHarga());
        });
        return sortedMenu;
    }

    public Menu cariMenu(String kode) {
        for (Menu m : daftarMenu)
            if (m.getKode().equalsIgnoreCase(kode)) return m;
        return null;
    }

    public ArrayList<Menu> getMenuMakananTerurut() {
        ArrayList<Menu> makanan = new ArrayList<>();
        for (Menu m : daftarMenu)
            if (m.getKategori().equalsIgnoreCase("Makanan")) makanan.add(m);
        
        Collections.sort(makanan, (m1, m2) -> Double.compare(m1.getHarga(), m2.getHarga()));
        return makanan;
    }

    public ArrayList<Menu> getMenuMinumanTerurut() {
        ArrayList<Menu> minuman = new ArrayList<>();
        for (Menu m : daftarMenu)
            if (m.getKategori().equalsIgnoreCase("Minuman")) minuman.add(m);
        
        Collections.sort(minuman, (m1, m2) -> Double.compare(m1.getHarga(), m2.getHarga()));
        return minuman;
    }
    
}
