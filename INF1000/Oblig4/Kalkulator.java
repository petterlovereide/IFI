


public class Kalkulator {
    public static void main(String[] args) {
        int addSvar = addisjon(3, 4);
        int subSvar = subtraksjon(5, 2);
        int helDivSvar = heltalldivisjon(10, 3);
        double divSvar = divisjon(10, 3);

        System.out.println(addSvar);
        System.out.println(subSvar);
        System.out.println(helDivSvar);
        System.out.println(divSvar);
    }

    public static int addisjon(int tall1, int tall2) {
        int sum = tall1 + tall2;
        return sum;
    }

    public static int subtraksjon(int tall1, int tall2) {
        int sum = tall1 - tall2;
        return sum;
    }

    public static int heltalldivisjon(int tall1, int tall2) {
        int sum = tall1 / tall2;
        return sum;
    }

    public static double divisjon(double tall1, double tall2) {
        double sum = tall1 / tall2;
        return sum;
    }
}