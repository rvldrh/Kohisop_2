package models;

public class PaymentModel {

    public static class RincianBayar {
        public double subtotal;
        public double adminFee;
        public double diskonChannel;
        public double diskonPoin;
        public double totalIDR;
        public String metodeName;
        public String currencyKode;
        public String currencySimbol;
        public double totalConverted;
    }

    public RincianBayar hitung(double subtotal, PaymentMethod metode,
                                Member member, int poinDigunakan,
                                Currency currency) {
        RincianBayar r = new RincianBayar();
        r.subtotal       = subtotal;
        r.metodeName     = metode.getNama();
        r.adminFee       = metode.adminFee(subtotal);
        r.diskonChannel  = metode.diskonChannel(subtotal);
        r.diskonPoin     = 0;

        if (member != null && poinDigunakan > 0) {
            r.diskonPoin = member.gunakanPoin(poinDigunakan);
        }

        r.totalIDR       = subtotal + r.adminFee - r.diskonChannel - r.diskonPoin;
        r.currencyKode   = currency.getKode();
        r.currencySimbol = currency.getSimbol();
        r.totalConverted = currency.dariIDR(r.totalIDR);

        // Tambah poin setelah bayar
        if (member != null) {
            member.tambahPoin(subtotal);
        }

        return r;
    }
}
