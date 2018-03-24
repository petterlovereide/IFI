import java.util.Scanner;
import java.io.*;

public class Innlesing {
    public static void main(String[] args) throws Exception {
        //Variabler og scanner
        File fileWinnie = new File("winnie.txt");
        Scanner fromFile = new Scanner(fileWinnie);
        Scanner in = new Scanner(System.in);
        int i = 0;
        String sokeOrd;
        int ordTeller = 0;

        //Brukeren skriver inn sokeord som skal sokes etter
        System.out.print("Skriv inn et ord som skal sokes etter: ");
        sokeOrd = in.nextLine();

        //Sjekker hvor mange ganger sokeordet kommer opp
        while (fromFile.hasNextLine()) {
            String winnie = fromFile.nextLine();
            if (winnie.equals(sokeOrd)) {
                ordTeller++;
            }
            i++;
        }

        System.out.println("Ordet '" + sokeOrd + "' er " + ordTeller + " ganger i teksten.");
    }
}