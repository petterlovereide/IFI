import java.io.*;
import java.util.*;

public class Person {
    private String navn;
    ArrayList<DVD> arkiv = new ArrayList<>();
    ArrayList<DVD> laanerBort = new ArrayList<>();
    ArrayList<DVD> laanerAv = new ArrayList<>();

    public Person(String name) {
        navn = name;
    }

    public void nyDVD(DVD dvd) {
        arkiv.add(dvd);
    }

    //Finner dvd fra arkiv
    public DVD getDVD(String s) {
        for (int i = 0; i < arkiv.size(); i++) {
            if (s.equals(arkiv.get(i).getNavn())) {
                return arkiv.get(i);
            }
        }
        return null;
    }

    // finner dvd fra laantut
    public DVD getLaantUt(String s) {
        for (int i = 0; i < laanerBort.size(); i++) {
            if (s.equals(laanerBort.get(i).getNavn())) {
                return laanerBort.get(i);
            }
        }
        return null;
    }

    //finner dvd fra laant inn
    public DVD getLaantAv(String s) {
        for (int i = 0; i < laanerAv.size(); i++) {
            if (s.equals(laanerAv.get(i).getNavn())) {
                return laanerAv.get(i);
            }
        }
        return null;
    }

    //finner navn paa person
    public String getNavn() {
        return navn;
    }

    //Viser person
    public void visPerson() {
        System.out.println(navn);

        //Hvor mange dvd man laaner
        for (int i = 0; i < laanerAv.size(); i++) {
            System.out.println(laanerAv.get(i).getNavn() + ". Laanes fra " + laanerAv.get(i).getEier().getNavn());
        }

        //Hvor mange DVD man eier
        for (int i = 0; i < arkiv.size(); i++) {
            if (laanerBort.size() > 0) {
                for (int j = 0; j < laanerBort.size(); j++) {
                    if (arkiv.get(i).getNavn().equals(laanerBort.get(j).getNavn())) {
                        System.out.println(arkiv.get(i).getNavn() + ". Laanes til " + arkiv.get(i).getLaanesAv().getNavn());
                    } else {
                        System.out.println(arkiv.get(i).getNavn());
                    }
                }
            } else {
                System.out.println(arkiv.get(i).getNavn());
            }
        }
        System.out.println();
    }

    //laaner bort dvd
    public void setDVDlaanerBort(DVD dvd) {
        laanerBort.add(dvd);
    }

    //laaner inn dvd
    public void setDVDlaanerAv(DVD dvd) {
        laanerAv.add(dvd);
    }

    //fjerner laaner dvd
    public void removeDVDlaanerAv(String dvdString) {
        for (int i = 0; i < laanerAv.size(); i++) {
            if (dvdString.equals(laanerAv.get(i).getNavn())) {
                DVD dvd = laanerAv.get(i);
                Person eier = dvd.getEier();

                eier.removeDVDlaanerBort(dvd);
                laanerAv.remove(i);
            }
        }
    }

    //fjerner laaner bort dvd
    public void removeDVDlaanerBort(DVD dvd) {
        for (int i = 0; i < laanerBort.size(); i++) {
            if (dvd.getNavn().equals(laanerBort.get(i).getNavn())) {
                laanerBort.remove(i);
            }
        }
    }

    public int getAntallEier() {
        return arkiv.size();
    }

    public int getAntallLaanerAv() {
        return laanerAv.size();
    }

    public int getAntallLaanerBort() {
        return laanerBort.size();
    }
}