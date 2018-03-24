

public class DVD {
    private String DVDNavn;
    private Person eier;
    private Person laanesAv = null;

    public DVD(String name, Person person) {
        DVDNavn = name;
        eier = person;
    }

    public String getNavn() {
        return DVDNavn;
    }

    public Person getEier() {
        return eier;
    }

    public Person getLaanesAv() {
        return laanesAv;
    }

    public void setLaanesAv(Person person) {
        laanesAv = person;
    }
}