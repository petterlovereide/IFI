import java.util.Scanner;
import java.util.Random;

public class MinOppgave2 {
    /* Oppgavetekst: Lag ett program som tar to tilfeldigde tall fra 1-10, og brukeren skal gange dem sammen.
    Programmet skal gjore dette 5 ganger, og til slutt legge ut scoren til brukeren. */
    static int poeng = 0;
    static int antallRun = 5;

    public static void main(String[] args) {
        //En for loop som lager to tilfeldige tall og kjorer Gangetabellen(), gjor dette fem ganger
        for (int i = 1; i <= antallRun; i++) {
            Random randomX = new Random();
            int tallX = randomX.nextInt(10) + 1;

            Random randomY = new Random();
            int tallY = randomY.nextInt(10) + 1;

            Gangetabellen(tallX, tallY);
        }
        //Legger ut poengsummen
        System.out.println("Din poengsum ble: " + poeng + " av " + antallRun);
    }

    public static void Gangetabellen(int tall1, int tall2) {
        Scanner in = new Scanner(System.in);

        System.out.println(tall1 + " x " + tall2 + " = ?");

        //Brukeren skal svare på tall1 * tall2
        String svar = in.nextLine();

        //Rigtig eller feil svar
        if (Integer.parseInt(svar) == (tall1 * tall2)) {
            System.out.println("Riktig svar.");
            poeng++;
        } else {
            System.out.println("Feil svar.");
        }
    }
}