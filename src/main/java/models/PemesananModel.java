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
public class PemesananModel {
    
    private ArrayList<CartItem> keranjang;
    private MenuModel menuModel;

    public PemesananModel(MenuModel menuModel) {
        this.keranjang = new ArrayList<>();
        this.menuModel = menuModel;
    }

    public ArrayList<CartItem> getKeranjangTerurutHarga() {
        Collections.sort(keranjang, (c1, c2) -> {
            int kat1 = c1.getMenu().getKategori().equalsIgnoreCase("Makanan") ? 1 : 2;
            int kat2 = c2.getMenu().getKategori().equalsIgnoreCase("Makanan") ? 1 : 2;
            if (kat1 != kat2) return Integer.compare(kat1, kat2);
            return Double.compare(c1.getMenu().getHarga(),  c2.getMenu().getHarga());
        });
        return keranjang;
    }

    public String tambahKeKeranjang(String kode, int qty) {
        Menu menu = menuModel.cariMenu(kode);
        if (menu == null) return "Error: Kode menu tidak valid!";

        if (menu.getKategori().equalsIgnoreCase("Minuman") && qty > 3) return "Error: Maksimal minuman 3 porsi!";
        if (menu.getKategori().equalsIgnoreCase("Makanan") && qty > 2) return "Error: Maksimal makanan 2 porsi!";

        for (CartItem item : keranjang) {
            if (item.getMenu().getKode().equalsIgnoreCase(kode)) {
                int totalQty = item.getKuantitas() + qty;
                if (menu.getKategori().equalsIgnoreCase("Minuman") && totalQty > 3) return "Error: Total porsi minuman melebihi 3!";
                if (menu.getKategori().equalsIgnoreCase("Makanan") && totalQty > 2) return "Error: Total porsi makanan melebihi 2!";
                item.setKuantitas(totalQty);
                return "Sukses";
            }
        }

        keranjang.add(new CartItem(menu, qty));
        return "Sukses";
    }

    private boolean itemSudahAdaDiKeranjang(String kode) {
        for (CartItem item : keranjang) {
            if (item.getMenu().getKode().equalsIgnoreCase(kode)) return true;
        }
        return false;
    }

    public void kosongkanKeranjang() { keranjang.clear(); }
    public ArrayList<CartItem> getKeranjang() { return keranjang; }
    
    public ArrayList<CartItem> getKeranjangMakananTerurutHarga() {
        ArrayList<CartItem> makanan = new ArrayList<>();
        for (CartItem item : keranjang) {
            if (item.getMenu().getKategori().equalsIgnoreCase("Makanan")) {
                makanan.add(item);
            }
        }
        Collections.sort(makanan, (c1, c2) -> Double.compare(c1.getMenu().getHarga(), c2.getMenu().getHarga()));
        return makanan;
    }

    public ArrayList<CartItem> getKeranjangMinumanTerurutHarga() {
        ArrayList<CartItem> minuman = new ArrayList<>();
        for (CartItem item : keranjang) {
            if (item.getMenu().getKategori().equalsIgnoreCase("Minuman")) {
                minuman.add(item);
            }
        }
        Collections.sort(minuman, (c1, c2) -> Double.compare(c1.getMenu().getHarga(), c2.getMenu().getHarga()));
        return minuman;
    }
    
}
