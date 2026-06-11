package models;

public interface Currency {
    String getKode();
    String getSimbol();
    double kursKeIDR();
    default double dariIDR(double idr) { return idr / kursKeIDR(); }
    default double keIDR(double val)   { return val * kursKeIDR(); }
}
