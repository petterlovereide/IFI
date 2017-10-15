public class Katt{
  private String navn;

//konstoktor
  public Katt(String navn){
    this.navn = navn;
  }

  //metode for jakt, faar to bol som parametre
  public void jakt(Bol<Mus> musebol, Bol<Rotte> rottebol){

   Boolean angrepet = false;

   //hvis musebolet ikke er tomt
    if(!musebol.sjekkTomt()){
      String tilstand = musebol.getObj().getTilstand();
      if(tilstand.equals("levende")){
        System.out.println("Katten " + navn + " gjorde ett angrep paa musen " + musebol.getObj().getNavn());
        musebol.getObj().angrip();
        angrepet = true;
      }
    }

    //hvis rottebolet ikke er tomt, og katten ikke angrep musen
    if(!rottebol.sjekkTomt() && !angrepet){
      String tilstand = rottebol.getObj().getTilstand();
      if(tilstand.equals("skadet")){
        System.out.println("Katten " + navn + " gjorde ett angrep paa rotta " + rottebol.getObj().getNavn());
        rottebol.getObj().angrip();
        angrepet = true;
      }
      if(tilstand.equals("levende")){
        System.out.println("Katten " + navn + " gjorde ett angrep paa rotta " + rottebol.getObj().getNavn());
        rottebol.getObj().angrip();
        angrepet = true;
      }
    }
    //hvis katten ikke angrep mus eller katt
    if(!angrepet){
      System.out.println("Katten " + navn + " fant ingen gnagere. Jakten avsluttes.");
    }





  }
}
