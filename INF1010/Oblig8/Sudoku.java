import java.util.*;
import java.io.*;

class Sudoku {
    static Brett brett = null;

    public static void main(String[] args) {
        lesFil();
        brett.getNesteRute(0).fyllUtDenneRuteOgResten();
        brett.skrivLosninger();
    }

    public static void skrivAlleMulige(int index) { // Finner alle mulige aa skriver ut
        System.out.println("Alle mulige tall: ");
        int[] mulige = brett.finnAlleMuligeTall(index);
        for (int i = 0; i < mulige.length; i++) {
            System.out.print(mulige[i] + " ");
        }
    }

    public static void lesFil() { // oppretter charArray med alle char, saa sender til brett
        Scanner in = new Scanner(System.in);
        System.out.println("Skriv inn filnavn:");
        String filNavn = in.nextLine();
        File fil = new File(filNavn);

        try {
            Scanner fraFil = new Scanner(fil);
            int y = Integer.parseInt(fraFil.nextLine());
            int x = Integer.parseInt(fraFil.nextLine());

            ArrayList<Character> charArray = new ArrayList<>();

            while (fraFil.hasNextLine()) {
                String mid = fraFil.nextLine();
                for (int i = 0; i < mid.length(); i++) {
                    char c = mid.charAt(i);
                    charArray.add(c);
                }
            }

            if (charArray.size() != x * y * x * y) { // antall tegn stemmer ikke
                System.out.println("Antall tegn stemmer ikke. Avslutter program. ");
                System.exit(0);
            }
            if (x * y > 64) { // for stort brett
                System.out.println("For stort brett. Avslutter program.");
                System.exit(0);
            }

            System.out.println("Innlesning fullfort.");
            brett = new Brett(x, y, charArray);
        } catch (FileNotFoundException e) { // filnavn er feil
            System.out.println("Fil ikke funnet. Avslutter program");
            System.exit(0);
        }
    }
}
