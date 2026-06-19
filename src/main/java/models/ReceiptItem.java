/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author rvldrh
 */
public class ReceiptItem {

    private String kode;
    private String nama;
    private String kategori; // Makanan / Minuman
    private int qty;
    private double harga;
    private double diskon;

    public ReceiptItem(String kode, String nama, String kategori,
            int qty, double harga, double diskon) {
        this.kode = kode;
        this.nama = nama;
        this.kategori = kategori;
        this.qty = qty;
        this.harga = harga;
        this.diskon = diskon;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public String getKategori() {
        return kategori;
    }

    public int getQty() {
        return qty;
    }

    public double getHarga() {
        return harga;
    }

    public double getDiskon() {
        return diskon;
    }

    public double getTotal() {
        return harga * qty;
    }
}
