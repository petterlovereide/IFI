class Bil {
    protected String bilnummer;
    protected String biltype;

    public void setType(String biltype) { // Setter biltype
        this.biltype = biltype;
    }

    public String getType() { // faar biltype
        return biltype;
    }

    public void setBilnummer(String bilnummer) { // settet bilnummer
        this.bilnummer = bilnummer;
    }

    public String getBilnummer() { //  faar bilnummer
        return bilnummer;
    }
}
