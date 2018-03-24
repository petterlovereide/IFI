

public class NegativeTall {
    public static void main(String[] args) {
        //Variabler
        int[] heltall = {1, 4, 5, -2, -4, 6, 10, 3, -2};
        int i = 0;
        int antallNegative = 0;

        //Loop som sjekker om tallet er nagativt, bytter det ut og skriver ut til slutt
        while (i < (heltall.length)) {
            if (heltall[i] < 0) {
                antallNegative++;
                heltall[i] = i;
            }
            System.out.println(heltall[i]);
            i++;
        }
    }
}