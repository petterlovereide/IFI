interface GenHylle<E>{

    public int getHylleLength();

    public void setIn(int plassNummer,E nyE);

    public boolean sjekkLedig(int plassNummer);

    public void taUt(int plassNummer);



}
