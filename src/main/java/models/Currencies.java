package models;

public class Currencies {

    public static class IDR implements Currency {

        public String getKode() {
            return "IDR";
        }

        public String getSimbol() {
            return "Rp";
        }

        public double kursKeIDR() {
            return 1.0;
        }
    }

    /**
     * Soal: 1 USD = 15 (dalam satuan ribuan IDR) → 1 USD = 15.000 IDR
     */
    public static class USD implements Currency {

        public String getKode() {
            return "USD";
        }

        public String getSimbol() {
            return "$";
        }

        public double kursKeIDR() {
            return 15_000.0;
        }
    }

    /**
     * Soal: 10 JPY = 1 (dalam satuan ribuan IDR) → 1 JPY = 100 IDR
     */
    public static class JPY implements Currency {

        public String getKode() {
            return "JPY";
        }

        public String getSimbol() {
            return "¥";
        }

        public double kursKeIDR() {
            return 100.0;
        }
    }

    /**
     * Soal: 1 MYR = 4 (dalam satuan ribuan IDR) → 1 MYR = 4.000 IDR
     */
    public static class MYR implements Currency {

        public String getKode() {
            return "MYR";
        }

        public String getSimbol() {
            return "RM";
        }

        public double kursKeIDR() {
            return 4_000.0;
        }
    }

    /**
     * Soal: 1 EUR = 14 (dalam satuan ribuan IDR) → 1 EUR = 14.000 IDR
     */
    public static class EUR implements Currency {

        public String getKode() {
            return "EUR";
        }

        public String getSimbol() {
            return "€";
        }

        public double kursKeIDR() {
            return 14_000.0;
        }
    }

    public static Currency get(String kode) {
        switch (kode.toUpperCase()) {
            case "USD":
                return new USD();
            case "EUR":
                return new EUR();
            case "JPY":
                return new JPY();
            case "MYR":
                return new MYR();
            default:
                return new IDR();
        }
    }

    public static final String[] KODE_LIST = {"IDR", "USD", "EUR", "JPY", "MYR"};
}
