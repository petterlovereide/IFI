


class Bil {
    static int tankLiter = 100;
    static int kmPrLiter = 1;
    static int antallKilomter;

    // Nok bensin? Kjor "km" kilometer.
    public void kjorTur(int km) {
        double maks = hentMaksDistanse();
        if (km > maks) {
            System.out.println("Du kan ikke kjore saa langt. Fyll tanken forst!");
        }
        if (km <= maks) {
            tankLiter = tankLiter - (km / kmPrLiter);
            antallKilomter += km;
        }
    }

    // Fyll tanken, dersom det er plass til spesifisert mengde bensin.
    public void fyllTank(double liter) {
        System.out.println("Fyller tanken med " + liter + " liter");
        tankLiter += liter;
        System.out.println("Antall liter igjen paa tanken: " + tankLiter);
        System.out.println();
    }

    // Hent maksimal kjoredistanse, gitt bensinmengde på tanken.
    public double hentMaksDistanse() {
        int maks = tankLiter / kmPrLiter;
        return maks;
    }

    // Hent bilens totale kilometerstand.
    public int hentKilometerstand() {
        return antallKilomter;
    }
}