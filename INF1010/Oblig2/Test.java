class Test{

  private Hylle<Bok> hylle;

  public Test(Hylle<Bok> hylle){
    this.hylle = hylle;
  }

  public void kjorTest(){
    System.out.println("TEST START");

    Bok bok1 = new Bok("Per", "Boken");
    Bok bok2 = new Bok("Odd", "Ny bok");
    Bok bok3 = new Bok("Odd", "Ny bok");

    hylle.setIn(0, bok1);
    hylle.setIn(1, bok2);
    hylle.setIn(2, bok3);

    hylle.taUt(0);
    hylle.taUt(1);
    hylle.taUt(2);

    Test3(bok1, 0);
    Test4(bok1, 101);
    Test1(bok2,0);
    Test2(60);

    System.out.println("TEST SLUTT");
  }

  public void Test1(Bok bok, int plass){ // Sette inn to boker paa samme plass
    hylle.setIn(plass, bok);
    hylle.setIn(plass, bok);
  }
  public void Test2(int plass){ // Ta ut bok fra tom plass
    hylle.taUt(plass);
  }
  public void Test3(Bok bok, int plass){ // Sett inn bok, ta ut og sjekk ledig
    hylle.setIn(plass, bok);
    hylle.taUt(plass);
    hylle.sjekkLedig(plass);
  }
  public void Test4(Bok bok, int plass){ // sette inn paa plass som ikke finnes
    hylle.setIn(101, bok);
  }

}
