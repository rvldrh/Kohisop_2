/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.List;
/**
 *
 * @author Jiyyn
 */
public class PaymentModel {

    public static class RincianBayar {

        public double subtotalSebelumPajak;
        public double totalPajak;
        public double subtotalSetelahPajak; // subtotal + pajak
        public double adminFee;
        public double diskonChannel;
        public double diskonPoin;
        public double totalIDR;             // final dalam IDR setelah semua
        public String metodeName;
        public String currencyKode;
        public String currencySimbol;
        public double totalConverted;
        public int poinSebelum;
        public int poinSetelah;
        public int poinDiperoleh;

        // Untuk tampilan ringkasan
        public double subtotal; // alias subtotalSetelahPajak untuk backward compat
    }

    /**
     * @param cartItems daftar item yang dipesan
     * @param metode metode pembayaran
     * @param member member (boleh null)
     * @param poinDigunakan poin yang ingin digunakan (hanya berlaku jika
     * currency = IDR)
     * @param currency mata uang pembayaran
     */
    public RincianBayar hitung(List<CartItem> cartItems,
            PaymentMethod metode,
            Member member,
            int poinDigunakan,
            Currency currency) {

        boolean bebasPajak = member != null && member.getKodeMember().contains("A");

        // Hitung subtotal sebelum pajak dan pajak
        double subtotalSebelumPajak = 0;
        double totalPajak = 0;

        for (CartItem item : cartItems) {
            double harga = item.getMenu().getHarga(); // IDR penuh
            int qty = item.getKuantitas();
            double subtotal = harga * qty;
            subtotalSebelumPajak += subtotal;

            if (!bebasPajak) {
                double rate = hitungTarifPajak(item.getMenu());
                totalPajak += subtotal * rate;
            }
        }

        double subtotalSetelahPajak = subtotalSebelumPajak + totalPajak;

        RincianBayar r = new RincianBayar();
        r.subtotalSebelumPajak = subtotalSebelumPajak;
        r.totalPajak = totalPajak;
        r.subtotalSetelahPajak = subtotalSetelahPajak;
        r.subtotal = subtotalSetelahPajak;
        r.metodeName = metode.getNama();
        r.adminFee = metode.adminFee(subtotalSetelahPajak);
        r.diskonChannel = metode.diskonChannel(subtotalSetelahPajak);
        r.diskonPoin = 0;

        // Poin hanya berlaku jika bayar dengan IDR
        r.poinSebelum = member != null ? member.getPoin() : 0;

        if (member != null && poinDigunakan > 0 && "IDR".equals(currency.getKode())) {
            r.diskonPoin = member.gunakanPoin(poinDigunakan);
        }

        r.totalIDR = subtotalSetelahPajak + r.adminFee - r.diskonChannel - r.diskonPoin;
        if (r.totalIDR < 0) {
            r.totalIDR = 0;
        }

        r.currencyKode = currency.getKode();
        r.currencySimbol = currency.getSimbol();
        r.totalConverted = currency.dariIDR(r.totalIDR);

        // Tambah poin setelah bayar
        if (member != null) {
            int poinSebelumTambah = member.getPoin();
            member.tambahPoin(subtotalSebelumPajak);
            r.poinDiperoleh = member.getPoin() - poinSebelumTambah;
            r.poinSetelah = member.getPoin();
        } else {
            r.poinDiperoleh = 0;
            r.poinSetelah = 0;
        }

        return r;
    }

    /**
     * Hitung tarif pajak sesuai soal
     */
    public static double hitungTarifPajak(Menu menu) {
        double harga = menu.getHarga(); // IDR penuh
        if (menu.getKategori().equalsIgnoreCase("Minuman")) {
            if (harga < 50_000) {
                return 0.0;
            } else if (harga <= 55_000) {
                return 0.08;
            } else {
                return 0.11;
            }
        } else { // Makanan
            if (harga > 50_000) {
                return 0.08;
            } else {
                return 0.11;
            }
        }
    }

    /**
     * Hitung pajak untuk satu item (subtotal item * tarif)
     */
    public static double hitungPajakItem(CartItem item, boolean bebasPajak) {
        if (bebasPajak) {
            return 0;
        }
        return item.getSubtotal() * hitungTarifPajak(item.getMenu());
    }
}
