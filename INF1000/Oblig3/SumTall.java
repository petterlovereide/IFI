import java.util.Scanner;

public class SumTall {
    public static void main(String[] args) {
        //Variabler
        Scanner in = new Scanner(System.in);
        int tall = 1;
        int sum = 0;

        //Loop som legger til i sum og avslutter
        while (tall > 0) {
            System.out.println("Skriv inn ett tall over 0 eller skriv 0 for aa avslutte");
            tall = in.nextInt();
            sum = sum + tall;
            if (tall == 0) {
                System.out.println("Din sum ble: " + sum);
            }
        }
    }
}