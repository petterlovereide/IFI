import java.util.Scanner;


public class Oppgave54 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Isbod isboden = new Isbod();

        //3 nye ansatte
        for (int i = 0; i < 3; i++) {
            System.out.print("Ny ansatt: ");
            String navn = in.nextLine();
            isboden.ansett(navn);
        }
        System.out.println();

        //Printer alle ansatte
        isboden.printAlleAnsatte();
        System.out.println();

        //Gir sistemann sparken
        isboden.giSistemannSparken();

        //Printer ut alle ansatte
        isboden.printAlleAnsatte();
    }
}