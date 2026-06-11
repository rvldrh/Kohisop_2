package models;

public class Tunai implements PaymentMethod {
    @Override public String getNama()              { return "Tunai"; }
    @Override public double adminFee(double t)     { return 0; }
    @Override public double diskonChannel(double t){ return 0; }
}
