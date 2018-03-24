import java.util.*;
import java.io.*;

class OrdListe {
    ArrayList<Ord> unikeOrd = new ArrayList<Ord>();

    public void lesBok(String filnavn) throws Exception {
        File myFile = new File(filnavn);
        Scanner fromFile = new Scanner(myFile);

        //Leser inn hele i alleOrdArray
        while (fromFile.hasNextLine()) {
            String nextOrd = fromFile.nextLine().toLowerCase();
            leggTilOrd(nextOrd);
        }
    }

    private void leggTilOrd(String ord) {
        //Sjekker om ordet finnes i Ordlisten
        Ord test = finnOrd(ord);

        //Hvis ordet finnes
        if (test != null) {
            test.oekAntall();
        }
        //Hvis ordet ikke finnes
        else {
            Ord nyttOrd = new Ord(ord);
            unikeOrd.add(nyttOrd);
        }
    }

    public Ord finnOrd(String tekst) {
        for (int i = 0; i < unikeOrd.size(); i++) {
            String test = unikeOrd.get(i).toString();
            if (test.equals(tekst)) {
                return unikeOrd.get(i);
            }
        }
        return null;
    }

    public int antallOrd() {
        //Antall ord i Ordlisten
        return unikeOrd.size();
    }

    public int antallForekomster(String tekst) {
        for (int i = 0; i < unikeOrd.size(); i++) {
            String sjekk = unikeOrd.get(i).toString();
            if (sjekk.equals(tekst)) {
                int ant = unikeOrd.get(i).hentAntall();
                return ant;
            }
        }
        return 0;
    }

    //Finne det eller de ordene som er mest vanlig
    public Ord[] alleVanligste() {
        ArrayList<Ord> vanligsteI = new ArrayList<Ord>();
        int verdiVanligste = 0;

        for (int i = 0; i < unikeOrd.size(); i++) {
            int verdiNeste = unikeOrd.get(i).hentAntall();
            //Hvis flere ord har like hoy "verdi", altsa antall forekomster
            if (verdiNeste == verdiVanligste) {
                vanligsteI.add(unikeOrd.get(i));
            }
            //Hvis ordet har flere forekomster enn alle andre
            if (verdiNeste > verdiVanligste) {
                vanligsteI.clear();
                vanligsteI.add(unikeOrd.get(i));
                verdiVanligste = verdiNeste;
            }
        }

        //Legger de vanligste ordene inn i ett array og returner det
        Ord[] rVanlig = new Ord[vanligsteI.size()];
        for (int i = 0; i < rVanlig.length; i++) {
            rVanlig[i] = vanligsteI.get(i);
        }
        return rVanlig;
    }
}