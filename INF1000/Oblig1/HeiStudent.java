import java.util.Scanner;

public class HeiStudent {
    public static void main(String[] args) {
        //	Skriver ut teksten "Hei Student!"
        System.out.println("Hei Student!");

        //	Skriver ut teksten "Hei" og navnet mitt som ligger under variabelen navn
        String navn = "Petter";
        System.out.println("Hei " + navn);

        //	Lager en scanner så leseren kan skrive inn sitt eget navn i terminalen, saa blir det skrevet ut med "Hello" foran
        Scanner innleser = new Scanner(System.in);
        String navn2;
        System.out.println("Skriv inn ditt navn");
        navn2 = innleser.nextLine();
        System.out.println("Hello " + navn2);

        //	Brukeren skal skrive to tall i terminalen, ogsaa blir de plusset sammen og skrevet ut
        String sTall1, sTall2;
        int iTall1, iTall2;

        System.out.println("Skriv inn tall nr 1");
        sTall1 = innleser.nextLine();
        iTall1 = Integer.parseInt(sTall1);

        System.out.println("Skriv inn tall nr 2");
        sTall2 = innleser.nextLine();
        iTall2 = Integer.parseInt(sTall2);

        System.out.println("Summen er: " + (iTall1 + iTall2));
    }
}

