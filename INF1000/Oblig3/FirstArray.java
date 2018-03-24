import java.util.Scanner;

public class FirstArray {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int arrayInt[] = new int[4];
        String arrayString[] = new String[5];
        int iArrayInt = 0, iArrayString = 0;
        int i = 0, j = 0;

        //Legger verdier in arrayInt
        while (iArrayInt < (arrayInt.length)) {
            if (iArrayInt == 0) {
                arrayInt[iArrayInt] = 1337;
            }
            if (iArrayInt == 3) {
                arrayInt[iArrayInt] = 1337;
            }
            if (iArrayInt == 1 || iArrayInt == 2) {
                arrayInt[iArrayInt] = iArrayInt;
            }
            iArrayInt++;
        }

        //Brukeren legger verdier i arrayString
        System.out.println("Skriv inn fem navn");
        while (iArrayString < (arrayString.length)) {
            arrayString[iArrayString] = in.nextLine();
            iArrayString++;
        }

        //Skriver ut veridiene i int arrayet
        System.out.println();
        System.out.println("Innhold i int-array");
        while (i < (arrayInt.length)) {
            System.out.println(arrayInt[i]);
            i++;
        }

        //Skriver ut verdiene i string arrayet
        System.out.println();
        System.out.println("Innhold i string-array");
        while (j < (arrayString.length)) {
            System.out.println(arrayString[j]);
            j++;
        }
    }
}