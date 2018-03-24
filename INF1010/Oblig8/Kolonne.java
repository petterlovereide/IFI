import java.util.*;

class Kolonne {
    ArrayList<Rute> ruter = new ArrayList<>();
    ArrayList<Integer> opptatt = new ArrayList<>();

    public void leggTilRute(Rute rute) {
        ruter.add(rute);
        if (rute.getVerdi() > 0) {
            opptatt.add(rute.getVerdi());
        }
    }

    public Boolean sjekkOmRiktig() { // sjekker om kolonnen er riktig
        ArrayList<Integer> mid = new ArrayList<>();
        for (int i = 0; i < ruter.size(); i++) {
            Integer integer = new Integer(ruter.get(i).getVerdi());
            if (mid.contains(integer)) {
                return false;
            } else {
                mid.add(integer);
            }
        }
        return true;
    }

    public int[] getOpptatt() { // sender hvilke verdier som er i kolonnen
        int[] mid = new int[opptatt.size()];
        for (int i = 0; i < mid.length; i++) {
            mid[i] = opptatt.get(i);
        }
        return mid;
    }
}
