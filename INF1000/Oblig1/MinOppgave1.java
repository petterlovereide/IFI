import java.util.Scanner;

public class MinOppgave1 {
    // Lager variabler
    static int totalPris = 0;
    static String prisIn;
    static int pris;
    static Scanner innleser = new Scanner(System.in);

    public static void main(String[] args) {
        //Skriver tekst på skjermen og kjoerer metoden nyPris
        System.out.println("Skriv inn saa mange priser du vil, og skriv `Ferdig` naar du er ferdig aa handle ");
        nyPris();
    }

    public static void nyPris() {
        //Brukeren skriver inn ny pris
        prisIn = innleser.nextLine();

        //Hvis bruken skriver Ferdig/ferdig vil programmet vaere over, og man vil faa totalpris på skjermen
        if (prisIn.equals("Ferdig") || prisIn.equals("ferdig")) {
            System.out.println("Din totalpris er " + totalPris + "kr");
        }
        //Hvis brukeren legger inn en ny pris blir den lagt til i totalen, og metoden nyPris kjoeres på nytt
        else {
            pris = Integer.parseInt(prisIn);
            totalPris = totalPris + pris;
            nyPris();
        }
    }
}