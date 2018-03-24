import java.util.Scanner;
import java.io.*;

public class MinOppgave4 {
    public static void main(String[] args) throws Exception {
        //Lag ett program som tar inn en by fra bruker, og sjekker om man kan fly dit fra gardemoen
        File minFile = new File("minfil.txt");
        Scanner fromFile = new Scanner(minFile);
        Scanner in = new Scanner(System.in);
        String destinasjon;

        System.out.print("Skriv inn en by du vil fly til fra Gardemoen Oslo:");
        destinasjon = in.nextLine();

        while (fromFile.hasNextLine()) {
            String s = fromFile.nextLine();
            boolean kanFly = sjekkFly(destinasjon, s);
            if (kanFly) {
                System.out.println("Du kan fly fra Gardemoen Oslo til " + destinasjon + ". God tur!");
                break;
            }
            if (!fromFile.hasNextLine()) {
                System.out.println("Du kan ikke fly fra Gardemoen Oslo til " + destinasjon);
            }
        }
    }

    public static boolean sjekkFly(String destinasjon, String s) {
        if (s.equals(destinasjon)) {
            return true;
        } else {
            return false;
        }
    }
}