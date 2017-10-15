class Oblig5{
  public static void main(String[] args){

    Lenkeliste<String> ll = new Lenkeliste<String>();

    System.out.println("Er tom: " + ll.tom());

    ll.leggTil("hei");
    ll.leggTil("halla");
    ll.leggTil("hallo");
    ll.leggTil("hey");

    System.out.println("Er tom: " + ll.tom());

    System.out.println("Minste: " + ll.fjernMinste());

    System.out.println("Inneholder hei: " + ll.inneholder("hei"));
    System.out.println("Inneholder hoho: " + ll.inneholder("hoho"));

    System.out.println("Minste: " + ll.fjernMinste());

    System.out.println("Minste: " + ll.fjernMinste());

    System.out.println("Minste: " + ll.fjernMinste());

  }
}
