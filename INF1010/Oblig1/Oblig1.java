public class Oblig1{
  public static void main(String[] args){

    Bol<Mus> musebol = new Bol<Mus>();
    Bol<Rotte> rottebol = new Bol<Rotte>();

    Katt katt = new Katt("Tom");
    katt.jakt(musebol,rottebol);

    Rotte rotte = new Rotte("Knut");
    rottebol.setIn(rotte);

    katt.jakt(musebol,rottebol);

    Mus mus = new Mus("Jerry");
    musebol.setIn(mus);

    Mus mus2 = new Mus("Per");
    musebol.setIn(mus2);

    katt.jakt(musebol,rottebol);


  }
}
