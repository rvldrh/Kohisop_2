package models;

public class Member {
    private String kodeMember;
    private String nama;
    private String email;
    private String noTelp;
    private int poin;

    public Member(String nama, String email, String noTelp) {
        this.nama       = nama;
        this.email      = email;
        this.noTelp     = noTelp;
        this.poin       = 0;
        this.kodeMember = generateKode();
    }

    private String generateKode() {
        // 6 karakter A-F dan 0-9
        String chars = "ABCDEF0123456789";
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++)
            sb.append(chars.charAt((int)(Math.random() * chars.length())));
        return sb.toString();
    }

    /**
     * Soal: 1 poin untuk setiap kelipatan 10 (dalam ribuan IDR).
     * Karena harga sekarang IDR penuh: 1 poin per kelipatan Rp 10.000.
     * Jika kode member mengandung 'A', poin digandakan.
     */
    public void tambahPoin(double totalBelanjaIDR) {
        int poinBaru = (int)(totalBelanjaIDR / 10_000);
        if (kodeMember.contains("A")) poinBaru *= 2;
        this.poin += poinBaru;
    }

    /**
     * Soal: 1 poin = 2 IDR.
     * @return nilai diskon dalam IDR, atau 0 jika poin tidak cukup.
     *         Sisa poin tidak hangus.
     */
    public double gunakanPoin(int jumlah) {
        if (jumlah > poin) {
            // Gunakan semua poin yang ada
            jumlah = poin;
        }
        poin -= jumlah;
        return jumlah * 2.0; // 1 poin = 2 IDR
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String getKodeMember() { return kodeMember; }
    public String getNama()       { return nama; }
    public String getEmail()      { return email; }
    public String getNoTelp()     { return noTelp; }
    public int    getPoin()       { return poin; }

    // ── Setters ──────────────────────────────────────────────────────────────
    public void setNama(String nama)     { this.nama   = nama; }
    public void setEmail(String email)   { this.email  = email; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }

    @Override
    public String toString() {
        return kodeMember + " - " + nama + " (" + poin + " poin)";
    }
}
