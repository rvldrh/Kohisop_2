/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Fiqih
 */
public class Menu {
    private String kode;
    private String nama;
    private double harga;
    private String kategori;

    public Menu(String kode, String nama, double harga, String kategori) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public String getKategori() { return kategori; }
}
