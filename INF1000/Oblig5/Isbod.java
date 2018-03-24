class Isbod {
    private String[] ansatte = new String[10];
    int antallAnsatte = 0;

    //Metode for aa ansette
    public void ansett(String navn) {
        ansatte[antallAnsatte] = navn;
        antallAnsatte++;
    }

    //Gi sistemann sparken
    public void giSistemannSparken() {
        antallAnsatte--;
        ansatte[antallAnsatte] = "";
    }

    //Print ut alle ansatte
    public void printAlleAnsatte() {
        for (int i = 0; i < antallAnsatte; i++) {
            System.out.println("Ansatt nr " + (i + 1) + ": " + ansatte[i]);
        }
    }
}

