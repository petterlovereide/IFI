class LenkeListe<T> {
    Node lh = null;

    class Node {
        Node neste = null;
        T data;

        public Node(T data) {
            this.data = data;
            neste = lh;
            lh = this;
        }
    }

    public void add(T data) {
        Node mid = new Node(data);
    }

    public T remove() {
        Node mid = lh;
        lh = lh.neste;

        return mid.data;
    }
}
