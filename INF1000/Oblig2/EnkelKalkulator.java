import java.util.Scanner;

public class EnkelKalkulator {
    static int tall1, tall2;
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        //Skriver inn tallene
        System.out.println("Skriv inn tall 1:");
        tall1 = Integer.parseInt(in.nextLine());
        System.out.println("Skriv inn tall 2:");
        tall2 = Integer.parseInt(in.nextLine());

        //Kjorer metoden Pluss
        Pluss(tall1, tall2);
        //Kjorer metoden Minus
        Minus(tall1, tall2);
        //Kjorer metoden Gange
        Gange(tall1, tall2);
    }

    static void Pluss(int tall1Pluss, int tall2Pluss) {
        //Adderer og skriver ut
        int tallSum = tall1Pluss + tall2Pluss;
        System.out.println("Summer er: " + tallSum);
    }

    static void Minus(int tall1Minus, int tall2Minus) {
        //Subtraherer og skriver ut
        int tallSum = tall1Minus - tall2Minus;
        System.out.println("Differansen er: " + tallSum);
    }

    static void Gange(int tall1Gange, int tall2Gange) {
        //Ganger og skriver ut
        int tallSum = tall1Gange * tall2Gange;
        System.out.println("Produktet er: " + tallSum);
    }
}