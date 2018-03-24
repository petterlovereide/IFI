import java.util.Scanner;

public class Metoder {
    static String navn, bosted;
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        //Kaller newRun() 3 ganger
        for (int i = 0; i < 3; i++) {
            newRun();
        }
    }

    public static void newRun() {
        //Brukeren skriver inn navn
        System.out.println("Skriv inn navn:");
        navn = in.nextLine();

        //Brukeren skriver inn bosted
        System.out.println("Skriv inn bosted:");
        bosted = in.nextLine();

        //Skriver ut navn og bosted
        System.out.println("Hei " + navn + "! Du er fra " + bosted + ".");
    }
}