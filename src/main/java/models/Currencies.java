package models;

public class Currencies {

    public static class IDR implements Currency {
        public String getKode()    { return "IDR"; }
        public String getSimbol()  { return "Rp"; }
        public double kursKeIDR()  { return 1.0; }
    }

    public static class USD implements Currency {
        public String getKode()    { return "USD"; }
        public String getSimbol()  { return "$"; }
        public double kursKeIDR()  { return 16300.0; }
    }

    public static class EUR implements Currency {
        public String getKode()    { return "EUR"; }
        public String getSimbol()  { return "€"; }
        public double kursKeIDR()  { return 17800.0; }
    }

    public static class JPY implements Currency {
        public String getKode()    { return "JPY"; }
        public String getSimbol()  { return "¥"; }
        public double kursKeIDR()  { return 108.0; }
    }

    public static class MYR implements Currency {
        public String getKode()    { return "MYR"; }
        public String getSimbol()  { return "RM"; }
        public double kursKeIDR()  { return 3600.0; }
    }

    /** Kembalikan instance currency berdasarkan kode string */
    public static Currency get(String kode) {
        switch (kode.toUpperCase()) {
            case "USD": return new USD();
            case "EUR": return new EUR();
            case "JPY": return new JPY();
            case "MYR": return new MYR();
            default:    return new IDR();
        }
    }

    public static final String[] KODE_LIST = {"IDR", "USD", "EUR", "JPY", "MYR"};
}
