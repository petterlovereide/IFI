import java.util.Scanner;
import java.io.*;


public class Tempratur {
    public static void main(String[] args) throws Exception {
        //Variabler og scanner
        int temp, iTemp = 0;
        double gjennomsnittTempratur;
        int tempArray[] = new int[12];
        File fileTemp = new File("tempratur.txt");
        Scanner in = new Scanner(fileTemp);

        //Legger tekst fra fil til array
        while (in.hasNextLine()) {
            String tempString = in.nextLine();
            temp = Integer.parseInt(tempString);
            tempArray[iTemp] = temp;
            iTemp++;
        }

        //Kaller metode og skriver ut gjennomsnitt
        gjennomsnittTempratur = GjennomsnittTemp(tempArray);
        System.out.println("Gjennomsnittstempraturen er: " + gjennomsnittTempratur);
    }

    public static double GjennomsnittTemp(int tempArray[]) {
        double sum = 0;
        for (int i = 0; i < tempArray.length; i++) {
            sum = sum + tempArray[i];
        }

        double sumGjennomsnitt = (sum / tempArray.length);
        return sumGjennomsnitt;
    }
}