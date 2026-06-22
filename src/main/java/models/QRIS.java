package models;

public class QRIS implements PaymentMethod {

    @Override
    public String getNama() {
        return "QRIS";
    }

    @Override
    public double adminFee(double t) {
        return 0;
    }

    @Override
    public double diskonChannel(double t) {
        return t * 0.05;
    } // diskon 5%

}
