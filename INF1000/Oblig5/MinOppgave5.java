import java.util.Scanner;

public class MinOppgave5 {
    public static void main(String[] args) {
        //Lag ett program hvor man kan legge til fag og skrive dem ut på skjermen
        Scanner in = new Scanner(System.in);
        Student student1 = new Student();

        //Legger til fag
        for (int i = 0; i < 3; i++) {
            System.out.print("Nytt fag: ");
            String fagIn = in.nextLine();
            student1.newFag(fagIn);
        }

        //Skriver ut fag
        System.out.println();
        student1.skrivFag();
    }
}