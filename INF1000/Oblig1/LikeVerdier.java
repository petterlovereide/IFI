import java.util.Scanner;

public class LikeVerdier {
    public static void main(String[] args) {
        //	Lager variablene c og d, og setter verdier
        int c, d;

        //	Leseren skriver inn to tall som blir gjort om til Integer, og deretter sjekker programmet om tallene er like.
        String c2, d2;

        Scanner innleser = new Scanner(System.in);
        System.out.println("Skriv inn tall nummer 1");
        c2 = innleser.nextLine();
        System.out.println("Skriv inn tall nummer 2");
        d2 = innleser.nextLine();

        c = Integer.parseInt(c2);
        d = Integer.parseInt(d2);

        if (c == d) {
            System.out.println("c og d er like");
        } else {
            System.out.println("c er ikke lik d");
        }
    }
}