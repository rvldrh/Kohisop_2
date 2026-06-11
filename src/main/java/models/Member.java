package models;

public class Member {
    private String kodeMember;
    private String nama;
    private String email;
    private String noTelp;
    private int poin;

    public Member(String nama, String email, String noTelp) {
        this.nama    = nama;
        this.email   = email;
        this.noTelp  = noTelp;
        this.poin    = 0;
        this.kodeMember = generateKode();
    }

    private String generateKode() {
        return "MBR" + (int)(Math.random() * 900000 + 100000);
    }

    public void tambahPoin(double totalBelanja) {
        this.poin += (int)(totalBelanja / 10000);
    }

    /** @return nilai diskon dalam rupiah, atau 0 jika poin kurang */
    public double gunakanPoin(int jumlah) {
        if (jumlah > poin) return 0;
        poin -= jumlah;
        return (jumlah / 100.0) * 5000;
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
