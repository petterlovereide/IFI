

class Ord {
    private int antallGanger;
    private String ordNavn;

    Ord(String tekst) {
        ordNavn = tekst;
        antallGanger = 1;
    }

    public String toString() {
        return ordNavn;
    }

    public int hentAntall() {
        return antallGanger;
    }

    public void oekAntall() {
        antallGanger++;
    }
}