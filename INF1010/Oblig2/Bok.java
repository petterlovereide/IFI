class Bok implements TilUtLaan {
    private String tittel;
    private String forfatter;

    Bok(String tittel, String forfatter) {
        this.tittel = tittel;
        this.forfatter = forfatter;
    }

    String utlaanTil = "";

    public void laanUt(String navn) {
        if (utlaanTil.equals("")) {
            utlaanTil = navn;
        } else {
            System.out.println("Boken er allerede laant ut.");
        }
    }

    public void bokTilbake() {
        utlaanTil = "";
    }

    public String getTittel() {
        return tittel;
    }
}
