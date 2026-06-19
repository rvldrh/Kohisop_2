/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author rvldrh
 */
public class CustomerInfo {

    private String nama;
    private String status;
    private String metodeBayar;
    private String mataUang;
    private int poinAwal;
    private int poinAkhir;

    public CustomerInfo(String nama, String status, String metodeBayar,
            String mataUang, int poinAwal, int poinAkhir) {
        this.nama = nama;
        this.status = status;
        this.metodeBayar = metodeBayar;
        this.mataUang = mataUang;
        this.poinAwal = poinAwal;
        this.poinAkhir = poinAkhir;
    }

    public String getNama() {
        return nama;
    }

    public String getStatus() {
        return status;
    }

    public String getMetodeBayar() {
        return metodeBayar;
    }

    public String getMataUang() {
        return mataUang;
    }

    public int getPoinAwal() {
        return poinAwal;
    }

    public int getPoinAkhir() {
        return poinAkhir;
    }
}
