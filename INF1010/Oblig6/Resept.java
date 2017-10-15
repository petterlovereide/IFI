class Resept{
  private int reseptNummer;
  private Legemiddel legemiddel;
  private String navnLege;
  private String navnPasient;

  public Resept(int reseptNummer){
    this.reseptNummer = reseptNummer;
  }

  public int getReseptNummer(){
    return reseptNummer;
  }
}
