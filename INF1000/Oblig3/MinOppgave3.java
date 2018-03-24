import java.util.Scanner;

public class MinOppgave3 {
    //Lag ett program som brukeren skriver inn en by, og info kommer ut
    public static void main(String[] args) {
        //Variabler
        Scanner in = new Scanner(System.in);
        String textIn;
        String by[] = {"Oslo", "Bergen", "Fredrikstad"};
        int innbyggere[] = {620000, 260000, 75000};
        String fylke[] = {"Oslo", "Hordaland", "Ostfold"};

        System.out.println("Skriv Oslo, Bergen eller Fredrikstad for aa faa mer informasjon");

        //Skiv inn by
        textIn = in.nextLine();

        //Sjekker hvilken by, og skriver ut info
        for (int i = 0; i < (by.length); i++) {
            if (textIn.equals(by[i])) {
                System.out.println("Fylke: " + fylke[i]);
                System.out.println("Innbyggertall: " + innbyggere[i]);
            }
        }
    }
}