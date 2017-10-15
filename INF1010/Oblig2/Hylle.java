public class Hylle<E> implements GenHylle<E>{

  private int size;
  private E[] hylle;

  Hylle(int size){
    this.size = size;
    hylle = (E[])new Object[size];
  }



  public int getHylleLength(){ // storrelse paa hylle
    return hylle.length;
  }

  public void setIn(int plassNummer,E nyE){ // setter inn bok
    if(plassNummer < 0 || plassNummer > getHylleLength()){ // om plassen ikke finnes
      System.out.println("Bokhyllen har ikke denne plassen. Prov igjen med ny plass.");
    }
    else{
      if(sjekkLedig(plassNummer)){ // om plassen finnes og plassen er ledig
        hylle[plassNummer] = nyE;
      }
      else{ // om plassen ikke er ledig
        System.out.println("Plassen er ikke ledig. Prov igjen med ny plass.");
      }
    }
  }

  public boolean sjekkLedig(int plassNummer){ //  sjekk om plassen er ledig
    if(hylle[plassNummer] == null){
      return true;
    }
    return false;
  }

  public void taUt(int plassNummer){ // ta ut bok
    if(hylle[plassNummer] != null){ // om plassen er tatt
      hylle[plassNummer] = null;
    }
    else{
      System.out.println("Denne plassen er tom fra for. Prov igjen med en annen plass.");
    }
  }




}
