class Personbil extends Fossilbil {
    protected int antallSeter;

    public void setSeter(String antallSeter) { // stter seter
        this.antallSeter = Integer.parseInt(antallSeter);
    }

    public int getSeter() { //  faar seter
        return antallSeter;
    }
}
