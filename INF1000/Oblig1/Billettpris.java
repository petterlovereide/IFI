import java.util.Scanner;

public class Billettpris {
    public static void main(String[] args) {
        /*Brukene skal skrive inn sin alder, saa kommer billettprisen ut.
		Det er halv pris hvis man er yngre enn 12aar eller eldre enn 67aar. alle andre faar standarpris.*/
        String alderIn;
        int alder;
        int pris = 50;

        System.out.println("Skriv inn din alder");
        Scanner innleser = new Scanner(System.in);
        alderIn = innleser.nextLine();
        alder = Integer.parseInt(alderIn);

        if (alder < 12 || alder > 67) {
            System.out.println("Din pris er: " + (pris / 2) + "kr");
        } else {
            System.out.println("Din pris er: " + pris + "kr");
        }
    }
}