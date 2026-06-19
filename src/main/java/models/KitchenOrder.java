/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author rvldrh
 */
public class KitchenOrder {

   private String kode;
    private String nama;
    private int qty;
    private double harga;
    private String kategori;

    public KitchenOrder(String kode, String nama, int qty, double harga, String kategori) {
        this.kode = kode;
        this.nama = nama;
        this.qty = qty;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public int getQty() {
        return qty;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }
}
