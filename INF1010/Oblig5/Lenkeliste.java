class Lenkeliste<E extends Comparable<E>> {
    public class Node { // node klasse
        E data;
        Node next = null;

        public Node(E data) {  // konstruktor
            this.data = data;
        }

        public void setData(E data) { // setter data
            this.data = data;
        }

        public E returnData() { // get data
            return data;
        }
    }

    private Node foran = null;

    public Boolean tom() { // sjekk om tom
        if (foran == null) {
            return true;
        }
        return false;
    }

    public void leggTil(E data) { // legger til ny node
        Node temp = new Node(data);
        temp.next = foran;
        foran = temp;
    }

    public E fjernMinste() { // fjerner minste node
        Node iter = foran;
        Node minste = foran;

        while (iter != null) { // hvis det er flere noder
            if (iter.returnData() == null) { // hvis noden er null
                iter = iter.next;
                continue;
            }
            if (iter.returnData().compareTo(minste.returnData()) <= 0) { // hvis noden er mindre enn de andre
                minste = iter;
            }
            iter = iter.next;
        }
        E eMinste = minste.returnData();
        minste.setData(null);
        return eMinste;
    }

    public Boolean inneholder(E e) { // hvis en av nodene inneholder
        Node iter = foran;
        while (iter != null) {
            if (iter.returnData() == null) {
                iter = iter.next;
                continue;
            }
            if (e.compareTo(iter.returnData()) == 0) {
                return true;
            }
            iter = iter.next;
        }
        return false;
    }
}