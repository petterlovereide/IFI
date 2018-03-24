class Student {
    private String[] fagArray = new String[3];
    private int fagNummer = 0;

    //Nytt fag
    public void newFag(String fag) {
        fagArray[fagNummer] = fag;
        fagNummer++;
    }

    //Skriver ut fag
    public void skrivFag() {
        System.out.println("Dine fag er:");
        for (int i = 0; i < fagNummer; i++) {
            System.out.println("Fag nr." + (i + 1) + ": " + fagArray[i]);
        }
    }
}