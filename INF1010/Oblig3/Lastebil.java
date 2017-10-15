class Lastebil extends Fossilbil{
  protected double nyttelast;

  public void setNyttelast(String nyttelast){ //  setter nyttelast
    this.nyttelast = Double.parseDouble(nyttelast);
  }
}
