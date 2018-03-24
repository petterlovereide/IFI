class Elbil extends Bil {
    protected int batteri;

    public void setBatteri(String batteri) { // setter batteri
        this.batteri = Integer.parseInt(batteri);
    }
}
