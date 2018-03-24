import java.util.Scanner;

public class Beslutninger {
    public static void main(String[] args) {
        //  Brukeren skal skrive inn sin alder, ogsaa kommer tekst ut om du er myndig eller ikke
        String alderString;
        int alder;

        Scanner innleser = new Scanner(System.in);
        System.out.println("Skriv inn din alder");
        alderString = innleser.nextLine();
        alder = Integer.parseInt(alderString);

        if (alder >= 18) {
            System.out.println("Du er myndig");
        } else {
            System.out.println("Du er ikke myndig");
        }
    }
}