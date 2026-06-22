package models;

public class EMoney implements PaymentMethod {
        private final String nama;
    public EMoney(String nama) { this.nama = nama; }
    @Override public String getNama()               { return "E-Money (" + nama + ")"; }
    @Override public double adminFee(double t)      { return 20.0; }      // Rp 20 admin fee
    @Override public double diskonChannel(double t) { return t * 0.07; }  // diskon 7%

}
