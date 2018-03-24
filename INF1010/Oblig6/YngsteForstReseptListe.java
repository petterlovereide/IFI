import java.util.*;

class YngsteForstReseptListe implements Iterable {
    Node lh = new Node(null);

    private class Node {
        Resept data = null;
        Node neste = null;

        Node(Resept data) {
            this.data = data;
        }
    }

    public Iterator iterator() {
        return new ListeIterator();
    }

    private class ListeIterator implements Iterator {
        Node pos = lh;

        public boolean hasNext() {
            if (pos.neste != null) {
                return true;
            }
            return false;
        }

        public Resept next() {
            if (hasNext()) {
                pos = pos.neste;
                return pos.data;
            }
            return null;
        }

        public void remove() {

        }
    }

    public void settInnForan(Resept data) {
        Node temp = new Node(data);
        temp.neste = lh.neste;
        lh.neste = temp;
    }

    public boolean tom() {
        if (lh.neste == null) {
            return true;
        }
        return false;
    }
}