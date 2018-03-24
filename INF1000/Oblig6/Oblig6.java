import java.util.*;
import java.io.*;

public class Oblig6 {
    public static void main(String[] args) throws Exception {
        //Variabler
        OrdListe liste = new OrdListe();
        liste.lesBok("scarlet.text");
        Scanner in = new Scanner(System.in);

        //Antall ulike ord
        System.out.println("Antall ulike ord: " + liste.antallOrd());
        System.out.println("");

        //Antall forekomster av ordene Holmes og elementery, kjorer metode og sender med soke ord og ordliste
        antallForekomsterMain("holmes", liste);
        antallForekomsterMain("elementary", liste);

        System.out.println("");

        //De vanligste ordene
        Ord[] vanligsteOrd = liste.alleVanligste();
        int antall = vanligsteOrd[0].hentAntall();

        //Hvis ett ord har flest forekomster
        if (vanligsteOrd.length == 1) {
            System.out.println("Det vanligste ordet er: " + vanligsteOrd[0]);
            System.out.println("Dette ordet forekommer " + antall + " ganger.");
        }
        //Hvis flere ord har flest forekomster
        else {
            System.out.println("De vanligste ordene er: ");
            for (int i = 0; i < vanligsteOrd.length; i++) {
                System.out.println("-" + vanligsteOrd[i]);
            }
            System.out.println("Disse ordene forekommer " + antall + " ganger.");
        }
    }

    //Metode for antall forekomster av ord
    public static void antallForekomsterMain(String ord, OrdListe list) {
        int antfor;
        antfor = list.antallForekomster(ord);
        System.out.println("Antall forekomster av ordet" + ord + ": " + antfor);
    }
}