class Rute {
    private Brett brett;
    private int index;
    private int verdi;
    private Boks boks;
    private Rad rad;
    private Kolonne kolonne;

    public Rute(int verdi, Boks boks, Rad rad, Kolonne kolonne, Brett brett, int index) {
        this.verdi = verdi;
        this.boks = boks;
        this.rad = rad;
        this.kolonne = kolonne;
        this.brett = brett;
        this.index = index;
    }

    public void fyllUtDenneRuteOgResten() {
        int[] mulige = brett.finnAlleMuligeTall(index);
        for (int i = 0; i < mulige.length; i++) {
            verdi = mulige[i];
            Rute nesteRute = brett.getNesteRute(index + 1);
            if (nesteRute != null) {
                nesteRute.fyllUtDenneRuteOgResten();
            } else {
                brett.sjekkLosning();
            }
        }
    }

    public int getIndex() {
        return index;
    }

    public int getVerdi() {
        return verdi;
    }

    public Boks getBoks() {
        return boks;
    }

    public Rad getRad() {
        return rad;
    }

    public Kolonne getKolonne() {
        return kolonne;
    }
}
