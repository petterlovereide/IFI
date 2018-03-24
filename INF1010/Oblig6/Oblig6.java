class Oblig6 {
    public static void main(String[] args) {
        SortertEnkelListe<String> liste = new SortertEnkelListe<String>();
        liste.settInnForan("God Morgen");
        liste.settInnForan("Hei");
        liste.settInnForan("Hallo");
        liste.settInnForan("Morn");

        liste.skrivRek();

        String data = (String) liste.getData("Hei");
        //data = (String) liste.getData("Halla");

        Tabell<String> tabell = new Tabell<String>(10);
        boolean a = tabell.setData(5, "Hei");

        Resept resept0 = new Resept(0);
        Resept resept1 = new Resept(1);

        EnkelReseptListe reseptListe = new EnkelReseptListe();
        reseptListe.settInnForan(resept0);
        reseptListe.settInnForan(resept1);

        Resept r0 = reseptListe.finnResept(0);
        Resept r2 = reseptListe.finnResept(2);

        YngsteForstReseptListe yngsteListe = new YngsteForstReseptListe();
        yngsteListe.settInnForan(resept0);

        EldsteForstReseptListe eldsteListe = new EldsteForstReseptListe();
        eldsteListe.settInnForan(resept1);
    }
}
