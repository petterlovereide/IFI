import java.util.*;

class Tabell<T> implements AbstraktTabell<T>, Iterable<T> {
    private T[] array;

    public Tabell(int arrayLengde) {
        Object arr[] = new Object[arrayLengde];
        array = (T[]) arr;
    }

    public Iterator<T> iterator() {
        return new ListeIterator();
    }

    private class ListeIterator implements Iterator<T> {
        int pos = 0;

        public boolean hasNext() {
            if (array[pos + 1] != null) {
                return true;
            }
            return false;
        }

        public T next() {
            if (hasNext()) {
                pos++;
                return array[pos];
            }
            return null;
        }

        public void remove() {

        }
    }

    public boolean setData(int index, T data) {
        if (index >= 0 && index < array.length) {
            array[index] = data;
            return true;
        }
        return false;
    }

    public T getData(int index) {
        if (index >= 0 && index < array.length) {
            return array[index];
        }
        return null;
    }
}