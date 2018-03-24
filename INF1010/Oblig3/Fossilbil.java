class Fossilbil extends Bil {
    protected double co2Utslipp;

    public void setUtslipp(String co2Utslipp) { // setter utslipp
        this.co2Utslipp = Double.parseDouble(co2Utslipp);
    }

    public double getUtslipp() { // faar utslipp
        return co2Utslipp;
    }
}
