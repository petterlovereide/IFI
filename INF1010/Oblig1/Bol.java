public class Bol<T> {
    private T obj;

    //metode for aa sette inn nytt objekt, sjekker om bolet er fult eller tomt
    public void setIn(T obj) {
        if (sjekkTomt()) {
            this.obj = obj;
        } else {
            System.out.println("Bolet er fult");
        }
    }

    //sjekker om bolet er tomt
    public Boolean sjekkTomt() {
        if (obj == null) {
            return true;
        }
        return false;
    }

    //metode for aa returnere objektet i bolet
    public T getObj() {
        return obj;
    }
}
