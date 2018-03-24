
public class Oppgave51 {
    static Bil bil1 = new Bil();

    public static void main(String[] args) {
        //Kjorer bilen
        kjorBil(25);
        kjorBil(40);
        kjorBil(50);

        //Fyller paa tanken
        bil1.fyllTank(50);

        //Kjorer en gang til
        kjorBil(50);

        //Henter og skriver ut km stand
        int kmStand = bil1.hentKilometerstand();
        System.out.println("Bilen har kjort " + kmStand + " km.");
    }

    public static void kjorBil(int km) {
        System.out.println("Kjorer en tur paa " + km + " km");
        bil1.kjorTur(km);
        System.out.println("Antall liter igjen paa tanken: " + bil1.tankLiter);

        System.out.println();
    }
}