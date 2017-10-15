import java.util.*;
import java.io.*;

class Oblig3{

  public static ArrayList<Bil> biler = new ArrayList<>();

  public static void main(String[] args){

    Scanner in = new Scanner(System.in);
    String filnavn = in.nextLine(); // Bruker skriver inn fil navn
    System.out.println("");

    lesFil(filnavn);

    skrivUt();

  }

  public static void lesFil(String filnavn){
    try { // Hvis filen finnes

        File fil = new File(filnavn + ".txt");
        Scanner fraFil = new Scanner(fil);

        while(fraFil.hasNextLine()){
          String nextline = fraFil.nextLine();
          String s = getInfo(1, nextline); // Finner type bil

          if(s.equals("BIL")){ // Hvis bil
            Bil bil = new Bil();
            bil.setType(s);
            biler.add(bil);
            s = getInfo(2, nextline); // Finner bilnummer
            bil.setBilnummer(s);
          }
          if(s.equals("EL")){ // Hvis elbil
            Elbil bil = new Elbil();
            bil.setType(s);
            biler.add(bil);
            s = getInfo(2, nextline); // finner bilnummer
            bil.setBilnummer(s);
            s = getInfo(3, nextline); // finner batteri
            bil.setBatteri(s);
          }
          if(s.equals("FOSSIL")){ // hvis fossil
            Fossilbil bil = new Fossilbil();
            bil.setType(s);
            biler.add(bil);
            s = getInfo(2, nextline); // finner bilnummer
            bil.setBilnummer(s);
            s = getInfo(3, nextline); // finner utslipp
            bil.setUtslipp(s);
          }
          if(s.equals("LASTEBIL")){ // hvis lastebil
            Lastebil bil = new Lastebil();
            bil.setType(s);
            biler.add(bil);
            s = getInfo(2, nextline); // finner bilnummer
            bil.setBilnummer(s);
            s = getInfo(3, nextline); // finner utslipp
            bil.setUtslipp(s);
            s = getInfo(4, nextline); // finner nyttelast
            bil.setNyttelast(s);
          }
          if(s.equals("PERSONFOSSILBIL")){ // hvis personfossilbil
            Personbil bil = new Personbil();
            bil.setType(s);
            biler.add(bil);
            s = getInfo(2, nextline); // finner bilnummer
            bil.setBilnummer(s);
            s = getInfo(3, nextline); // finner utslipp
            bil.setUtslipp(s);
            s = getInfo(4, nextline); // finner antall seter
            bil.setSeter(s);
          }
        }
    }
    catch (FileNotFoundException e) { // hvis filen ikke finnes
        System.out.println("Filen eksisterer ikke.");
    }

  }

  public static String getInfo(int antallSpace, String nextline){ // antallSpace velger hvilken informasjon vi skal hente
    String s = "";
    int antSpace = 0;

    for(int i = 0; i < nextline.length(); i++){

      if(nextline.charAt(i) == ' '){
          antSpace++;
          if(antSpace == antallSpace){ // hvis det er denne informasjon vi skal hente
            return s;
          }
          s = ""; // hvis ikke det er denne informasjon vi skal hente
      }
      else{
        s = s + nextline.charAt(i);
      }

      if(i == nextline.length()-1){
        return s;
      }
    }
    return "";
  }

  public static void skrivUt(){ // skriver ut info
    for(Bil bil : biler){
      if(bil.getType().equals("PERSONFOSSILBIL")){ // skriver ut om bilen er personfossilbil
        Personbil personbil = (Personbil) bil;
        System.out.println(personbil.getType());
        System.out.println(personbil.getBilnummer());
        System.out.println(personbil.getUtslipp());
        System.out.println(personbil.getSeter());
      }
    }
  }



}
