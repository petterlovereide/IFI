public class Rotte {
    private String navn;
    private String tilstand;

    //konstroktor
    public Rotte(String navn) {
        this.navn = navn;
        tilstand = "levende";
    }

    //metode for aa angripe rotte
    public void angrip() {
        if (tilstand.equals("skadet")) {
            tilstand = "dod";
            System.out.println("Rotta " + navn + " gikk fra aa vaere skadet til aa vaere dod");
        }
        if (tilstand.equals("levende")) {
            tilstand = "skadet";
            System.out.println("Rotta " + navn + " gikk fra aa vaere levende til aa vaere skadet");
        }
    }

    //metode for aa faa tilstand
    public String getTilstand() {
        return tilstand;
    }

    //metode for aa faa navn
    public String getNavn() {
        return navn;
    }
}
