package models;

public interface PaymentMethod {
    String getNama();
    double adminFee(double total);
    double diskonChannel(double total);
}
