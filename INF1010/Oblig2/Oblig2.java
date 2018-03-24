class Oblig2 {
    public static void main(String[] args) {
        Hylle<Bok> nyHylle = new Hylle<Bok>(100);

        Test nyTest = new Test(nyHylle);
        nyTest.kjorTest();
    }
}
