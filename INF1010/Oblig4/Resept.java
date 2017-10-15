class Resept{
  private int reseptNummer;
  private Legemiddel legemiddel;
  private String navnLege;
  private String navnPasient;

  public Resept(int reseptNummer, Legemiddel legemiddel, String navnLege, String navnPasient){
    this.reseptNummer = reseptNummer;
    this.legemiddel = legemiddel;
    this.navnLege = navnLege;
    this.navnPasient = navnPasient;
  }
}
