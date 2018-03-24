public class Mus {
    private String navn;
    private String tilstand;

    //konstruktor
    public Mus(String navn) {
        this.navn = navn;
        tilstand = "levende";
    }

    //metode for angrep paa mus
    public void angrip() {
        tilstand = "dod";
        System.out.println("Musen " + navn + " gikk fra aa vaere levende til aa vaere dod");
    }

    //metode for aa faa tilstnden til musen
    public String getTilstand() {
        return tilstand;
    }

    //metode for aa faa navnet til musen
    public String getNavn() {
        return navn;
    }
}
