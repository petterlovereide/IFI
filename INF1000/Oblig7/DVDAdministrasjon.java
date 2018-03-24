import java.util.*;
import java.io.*;


public class DVDAdministrasjon {
    static ArrayList<Person> PersonArray = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        //Leser inn data fra fil
        lesFil();

        //Printer meny og velger kommando til bruker skriver 7
        printMeny();
    }

    public static void lesFil() throws Exception {
        File myFile = new File("dvdarkiv.txt");
        Scanner fromFile = new Scanner(myFile);

        //Antall bindestreker
        int bs = 0;

        while (fromFile.hasNextLine()) {
            //Leser inn Personer, for forste bindestrek
            if (bs == 0) {
                String next = fromFile.nextLine();
                if (next.equals("-")) {
                    bs++;
                } else {
                    Person nyPerson = new Person(next);
                    PersonArray.add(nyPerson);
                }
            }

            //Ferdig lest inn personer
            if (bs > 0) {
                String navn = fromFile.nextLine();
                Person person = null;

                //Finner neste person
                for (int i = 0; i < PersonArray.size(); i++) {
                    if (navn.equals(PersonArray.get(i).getNavn())) {
                        person = PersonArray.get(i);
                        break;
                    }
                }

                //Legger til DVD-ene til denne personen
                while (fromFile.hasNextLine()) {
                    String dvd = fromFile.nextLine();

                    //Hvis neste linje inneholder "-", er personen ferdig
                    if (dvd.equals("-")) {
                        break;
                    }
                    //Hvis Dvden skal laanes
                    if (dvd.contains("*")) {
                        String laandvd = dvd.replace("*", "");
                        dvd = fromFile.nextLine();

                        //Ny DVD
                        DVD nyDVD = new DVD(laandvd, person);
                        person.nyDVD(nyDVD);
                        person.setDVDlaanerBort(nyDVD);

                        //Sjekker hvem den skal laanes til
                        for (int i = 0; i < PersonArray.size(); i++) {
                            if (dvd.equals(PersonArray.get(i).getNavn())) {
                                Person laanesTil = PersonArray.get(i);
                                //Hvem laanes den til
                                laanesTil.setDVDlaanerAv(nyDVD);
                                nyDVD.setLaanesAv(laanesTil);
                                break;
                            }
                        }
                    }
                    //Hvis dvden ikke skal laanes
                    else {
                        DVD nyDVD = new DVD(dvd, person);
                        person.nyDVD(nyDVD);
                    }
                }
            }
        }
    }

    public static void printMeny() {
        String[] meny = {"Ny Person", "Kjop", "Laan", "Vis Person", "Vis Oversikt", "Retur", "Avslutt"};
        for (int i = 0; i < meny.length; i++) {
            System.out.println("Tast " + (i + 1) + " for " + meny[i]);
        }

        Scanner in = new Scanner(System.in);
        Boolean kjor = true;
        while (kjor) {
            System.out.println("Gi ny kommando:");
            String s = in.nextLine();

            if (s.equals("1")) {
                nyPerson();
            }
            if (s.equals("2")) {
                Kjop();
            }
            if (s.equals("3")) {
                Laan();
            }
            if (s.equals("4")) {
                visPerson();
            }
            if (s.equals("5")) {
                visOversikt();
            }
            if (s.equals("6")) {
                Retur();
            }
            if (s.equals("7")) {
                kjor = false;
                System.out.println("Avsluttet.");
                break;
            }
            if (!s.equals("1") && !s.equals("2") && !s.equals("3") && !s.equals("4") && !s.equals("5") && !s.equals("6") && !s.equals("7")) {
                System.out.println("Ukjent kommando, prov igjen.");
            }
        }
    }

    public static void nyPerson() {
        //Spor bruker etter navn, og legger til ny person
        Scanner in = new Scanner(System.in);

        System.out.print("Navn: ");
        String navn = in.nextLine();

        Person nyPerson = new Person(navn);
        PersonArray.add(nyPerson);

        System.out.println("Person lagt til.");
        System.out.println();
    }

    public static void Kjop() {
        Scanner in = new Scanner(System.in);

        System.out.print("Navn paa person: ");
        String personNavn = in.nextLine();

        Person person = null;

        //Finner personen
        for (int i = 0; i < PersonArray.size(); i++) {
            if (personNavn.equals(PersonArray.get(i).getNavn())) {
                person = PersonArray.get(i);
                break;
            }
        }

        //Hvis personen finnes
        if (person != null) {
            System.out.print("Navn paa DVD: ");
            String dvdNavn = in.nextLine();

            DVD dvdSjekk = person.getDVD(dvdNavn);

            //Hvis personen ikke eier DVDen fra for
            if (dvdSjekk == null) {
                DVD nyDVD = new DVD(dvdNavn, person);
                person.nyDVD(nyDVD);
            }
            //Hvis personen eier DVDen fra for
            else {
                System.out.println("Personen eier allerede denne DVDen.");
            }
        }
        //Hvis personen ikke finnes i systemet
        else {
            System.out.println("Personen finnes ikke i systemet.");
        }
    }

    public static void Laan() {
        Scanner in = new Scanner(System.in);

        System.out.print("Hvem skal laane? ");
        String laanerTil = in.nextLine();
        System.out.print("Hvem skal laane ut? ");
        String laanerFra = in.nextLine();
        if (laanerFra.equals(laanerTil)) {
            System.out.println("Kan ikke laane fra seg selv.");
        } else {
            System.out.print("Hvilken DVD skal laanes? ");
            String laanerDVD = in.nextLine();

            Person person1 = null;
            Person person2 = null;
            DVD dvd = null;
            DVD dvdSjekk = null;

            for (int i = 0; i < PersonArray.size(); i++) {
                if (PersonArray.get(i).getNavn().equals(laanerTil)) {
                    person1 = PersonArray.get(i);
                }
                if (PersonArray.get(i).getNavn().equals(laanerFra)) {
                    person2 = PersonArray.get(i);
                }
            }
            //Hvis person1 ikke finnes
            if (person1 == null) {
                System.out.println("Personen som skal laane finnes ikke.");
            }
            //Hvis person2 ikke finnes
            if (person2 == null) {
                System.out.println("Personen som skal laane ut finnes ikke.");
            }

            //Hvis dvden ikke finnes
            dvd = person2.getDVD(laanerDVD);
            if (dvd == null) {
                System.out.println("DVDen finnes ikke.");
            }

            //Sjekker om DVDen allerede er laant ut
            dvdSjekk = person2.getLaantUt(laanerDVD);
            if (dvdSjekk != null) {
                System.out.println("DVDen er allerede laant ut.");
            }

            //Hvis alle finnes og den ikke er laant ut
            if (person1 != null && person2 != null && dvd != null && dvdSjekk == null) {
                person1.setDVDlaanerAv(dvd);
                person2.setDVDlaanerBort(dvd);
                dvd.setLaanesAv(person1);

                System.out.println(dvd.getNavn() + " laanes til " + person1.getNavn() + " fra " + person2.getNavn());
            }
        }
    }

    public static void visPerson() {
        Scanner in = new Scanner(System.in);
        System.out.print("Navn: ");
        String navn = in.nextLine();
        System.out.println();

        Person person = null;

        //Skriver ut alle
        if (navn.equals("*")) {
            for (int i = 0; i < PersonArray.size(); i++) {
                PersonArray.get(i).visPerson();
            }
        }

        //Skriver ut en bestemt
        else {
            for (int i = 0; i < PersonArray.size(); i++) {
                if (navn.equals(PersonArray.get(i).getNavn())) {
                    person = PersonArray.get(i);
                }
            }
            if (person != null) {
                person.visPerson();
            } else {
                System.out.println("Personen finnes ikke i systemet.");
            }
        }
    }

    public static void visOversikt() {
        for (int i = 0; i < PersonArray.size(); i++) {
            System.out.println(PersonArray.get(i).getNavn());
            System.out.println("Eier " + PersonArray.get(i).getAntallEier());
            System.out.println("Laaner " + PersonArray.get(i).getAntallLaanerAv());
            System.out.println("Laaner ut " + PersonArray.get(i).getAntallLaanerBort());
            System.out.println();
        }
    }

    public static void Retur() {
        Scanner in = new Scanner(System.in);

        System.out.print("Hvem skal levere inn DVD? ");
        String navn = in.nextLine();
        System.out.print("Hvilken DVD skal leveres inn? ");
        String dvd = in.nextLine();

        DVD dvdSjekk = null;
        Person person = null;

        //Hvem person som laaner, og fjerner at de laaner den
        for (int i = 0; i < PersonArray.size(); i++) {
            if (PersonArray.get(i).getNavn().equals(navn)) {
                person = PersonArray.get(i);
                break;
            }
        }
        //Hvis personen ikke finnes
        if (person == null) {
            System.out.println("Personen finnes ikke.");
        }

        //Hvis personen finnes
        if (person != null) {
            dvdSjekk = person.getLaantAv(dvd);
            if (dvdSjekk != null) {
                person.removeDVDlaanerAv(dvd);
                System.out.println("Dvden er returnert.");
            }
            if (dvdSjekk == null) {
                System.out.println("DVDen blir ikke laant av " + person.getNavn());
            }
        }
    }
}