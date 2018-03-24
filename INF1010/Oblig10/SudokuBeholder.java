import java.util.*;

class SudokuBeholder {
    int antallLosninger = 0;
    LenkeListe<Integer[]> array = new LenkeListe<>();

    public void settInn(Integer[] a) {
        if (antallLosninger < 3500) {
            array.add(a);
        }
        antallLosninger++;
    }

    public Integer[] taUt() {
        return array.remove();
    }

    public int hentAntallLosninger() {
        return antallLosninger;
    }
}
