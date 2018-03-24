import java.util.Scanner;
import java.io.*;

public class Valg {
    public static void main(String[] args) throws Exception {
        File fileValg = new File("stemmer.txt");
        Scanner fromFile = new Scanner(fileValg);
        String valgArray[] = new String[456];
        int stemmerArray[] = new int[4];
        int SP = 0, AP = 1, H = 2, KRF = 3;

        //Legger inn stemmer i Array, og teller stemmer på hvert parti
        for (int i = 0; i < valgArray.length; i++) {
            valgArray[i] = fromFile.nextLine();
            if (valgArray[i].equals("Sp")) {
                stemmerArray[SP]++;
            }
            if (valgArray[i].equals("Ap")) {
                stemmerArray[AP]++;
            }
            if (valgArray[i].equals("H")) {
                stemmerArray[H]++;
            }
            if (valgArray[i].equals("KrF")) {
                stemmerArray[KRF]++;
            }
        }

        System.out.println("Stemmer SP: " + stemmerArray[SP]);
        System.out.println("Stemmer AP: " + stemmerArray[AP]);
        System.out.println("Stemmer H: " + stemmerArray[H]);
        System.out.println("Stemmer KrF: " + stemmerArray[KRF]);

        //Regner ut prosent stemmer, og skriver det ut
        double totalstemmer = stemmerArray[SP] + stemmerArray[AP] + stemmerArray[H] + stemmerArray[KRF];
        double prosentSP = (stemmerArray[SP] / totalstemmer) * 100;
        double prosentAP = (stemmerArray[AP] / totalstemmer) * 100;
        double prosentH = (stemmerArray[H] / totalstemmer) * 100;
        double prosentKRF = (stemmerArray[KRF] / totalstemmer) * 100;

        System.out.println();

        System.out.println("Prosent stemmer SP: " + prosentSP);
        System.out.println("Prosent stemmer AP: " + prosentAP);
        System.out.println("Prosent stemmer H: " + prosentH);
        System.out.println("Prosent stemmer KRF: " + prosentKRF);

        System.out.println();

        if (prosentSP > prosentAP && prosentSP > prosentH && prosentSP > prosentKRF) {
            System.out.println("Vinner er Sp");
        }
        if (prosentAP > prosentSP && prosentAP > prosentH && prosentAP > prosentKRF) {
            System.out.println("Vinner er AP");
        }
        if (prosentH > prosentAP && prosentH > prosentSP && prosentH > prosentKRF) {
            System.out.println("Vinner er H");
        }
        if (prosentKRF > prosentAP && prosentKRF > prosentH && prosentKRF > prosentSP) {
            System.out.println("Vinner er KRF");
        }
    }
}