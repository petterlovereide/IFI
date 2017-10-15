import java.util.*;

class Brett{

  SudokuBeholder beholder = new SudokuBeholder();

  private int x;
  private int y;
  int antallLosninger = 1;

  ArrayList<Rute> arrayBrett = new ArrayList<>();
  ArrayList<Integer[]> alleMuligeBrett = new ArrayList<>();
  ArrayList<Integer> orginalBrett = new ArrayList<>();
  ArrayList<Integer[]> riktigeLosninger = new ArrayList<>();

  Boks boksArray[];
  Rad radArray[];
  Kolonne kolonneArray[];

  public Brett(int x, int y, ArrayList<Character> array){
    this.x = x;
    this.y = y;
    opprettDataStruktur(array);
    } // konstroktor slutt

  public void opprettDataStruktur(ArrayList<Character> array){
    int boks = 0;
    int rad = 0;
    int kolonne = -1;
    int ruteIndex = 0;

    boksArray = new Boks[x*y];
    radArray = new Rad[x*y];
    kolonneArray = new Kolonne[x*y];

    for(int i = 0; i < x*y; i++){ // oppretter bokser, rader og kolonner
      Boks newBoks = new Boks();
      boksArray[i] = newBoks;

      Rad newRad = new Rad();
      radArray[i] = new Rad();

      Kolonne newKolonne = new Kolonne();
      kolonneArray[i] = new Kolonne();
    }
    int nummer = 0;
    for(int i = 0; i < array.size(); i++){
      // Rad verdi
      if(i == (x * y) + (rad * x * y)){
        rad++;
      }
      // Kolonne verdi
      kolonne++;
      if(kolonne == x * y){
        kolonne = 0;
      }
      //Boks verdi
      if(nummer == x){
        boks = ((rad / y) * y) + (kolonne / x);
        nummer = 0;
      }
      nummer++;

      int verdi = tegnTilVerdi(array.get(i));
      if(verdi < 0 || verdi > x*y){ // sjekker om tegnene er gyldige eller utenfor lovlig intervall
        System.out.println("Ugyldig tegn i fil. Avslutter program.");
        System.exit(0);
      }
      Rute rute = new Rute(verdi, boksArray[boks], radArray[rad], kolonneArray[kolonne], this, ruteIndex);
      arrayBrett.add(rute);
      ruteIndex++;
      orginalBrett.add(verdi);

      boksArray[boks].leggTilRute(rute);
      radArray[rad].leggTilRute(rute);
      kolonneArray[kolonne].leggTilRute(rute);
    }
  }

  public void sjekkLosning(){
    for(int i = 0; i < boksArray.length; i++){
      if(!boksArray[i].sjekkOmRiktig()){
        return;
      }
    }
    for(int i = 0; i < radArray.length; i++){
      if(!radArray[i].sjekkOmRiktig()){
        return;
      }
    }
    for(int i = 0; i < kolonneArray.length; i++){
      if(!kolonneArray[i].sjekkOmRiktig()){
        return;
      }
    }
    Integer[] arrayNaa = new Integer[arrayBrett.size()];
    for(int i = 0; i < arrayNaa.length; i++){
      arrayNaa[i] = arrayBrett.get(i).getVerdi();
    }
    beholder.settInn(arrayNaa);
  }

  public void skrivLosninger(){
    if(beholder.hentAntallLosninger() > 1){
      int antLosninger = beholder.hentAntallLosninger();
      if(antLosninger > 3500){
        antLosninger = 3500;
      }

      for(int i = 0; i < antLosninger; i++){
        System.out.print((i+1) + ": ");
        Integer[] a = beholder.taUt();

        int nesteRad = 0;
        for(int j = 0; j < a.length; j++){
          if(nesteRad == x*y){
            System.out.print("//");
            nesteRad = 0;
          }
          System.out.print(a[j]);
          nesteRad++;
        }
        System.out.println();
      }
    }

    if(beholder.hentAntallLosninger() == 1){
      System.out.println(y);
      System.out.println(x);

      int nesteRad = 0;
      Integer[] a = beholder.taUt();

      for(int i = 0; i < a.length; i++){
        if(nesteRad == x*y){
          System.out.println();
          nesteRad = 0;
        }
        System.out.print(a[i]);
        nesteRad++;
      }
    }
  }

  public Rute getNesteRute(int index){
    if(index < arrayBrett.size()){
      for(int i = index; i < arrayBrett.size(); i++){
        if(orginalBrett.get(i) == 0){
          return arrayBrett.get(i);
        }
      }
      return null;
    }
    else{
      return null;
    }
  }

  public int[] finnAlleMuligeTall(int index){
    if(orginalBrett.get(index) != 0){
      int[] mid = new int[1];
      mid[0] = orginalBrett.get(index);
      return mid;
    }

    Rute rute = arrayBrett.get(index);
    int[] boksOpptatt = rute.getBoks().getOpptatt();
    int[] radOpptatt = rute.getRad().getOpptatt();
    int[] kolonneOpptatt = rute.getKolonne().getOpptatt();
    ArrayList<Integer> ledige = new ArrayList<>();

    outerloop:
    for(int i = 1; i <= x*y; i++){

      for(int j = 0; j < boksOpptatt.length; j++){ // sjekker om boksen inneholder verdi i
        if(i == boksOpptatt[j]){
          continue outerloop;
        }
      }
      for(int j = 0; j < radOpptatt.length; j++){ // sjekker om raden inneholder verdi i
        if(i == radOpptatt[j]){
          continue outerloop;
        }
      }
      for(int j = 0; j < kolonneOpptatt.length; j++){ // sjekker om kolonnen inneholder verdi i
        if(i == kolonneOpptatt[j]){
          continue outerloop;
        }
      }
      ledige.add(i); // hvis ingen inneholder verdi i, legges den til i ledig
    }

    int[] ledigeArray = new int[ledige.size()];
    for(int i = 0; i < ledigeArray.length; i++){
      ledigeArray[i] = ledige.get(i);
    }
    return ledigeArray;
  }

  public void skrivBrett(){
    System.out.println();

    int xCounter = 0, yCounter = 0;
    int i = 0;
    while(i < arrayBrett.size()){
      if(i == x*y + (i/(x*y))*x*y -1){
        yCounter++;

        int verdi = arrayBrett.get(i).getVerdi();
        if(verdi == 0){
          System.out.print(" ");
        }
        else{
          System.out.print(verdi);
        }
        System.out.println("");
        i++;
        xCounter = 0;

        if(yCounter == y && i != arrayBrett.size()){
          for(int j = 0; j < y; j++){
            for(int k = 0; k < x; k++){
              System.out.print("-");
            }
            if(j < y-1){
              System.out.print("+");
            }
          }
          System.out.println("");
          yCounter = 0;
        }
      }
      else if(xCounter == x){
        System.out.print("|");
        xCounter = 0;
      }
      else{
        int verdi = arrayBrett.get(i).getVerdi();
        if(verdi == 0){
          System.out.print(" ");
        }
        else{
          System.out.print(verdi);
        }
        i++;
        xCounter++;
      }
    }
  }

  public static int tegnTilVerdi(char tegn) {
        char TOM_RUTE_TEGN = '.';
        if (tegn == TOM_RUTE_TEGN) {                // tom rute, DENNE KONSTANTEN MAA DEKLARERES
            return 0;
        } else if ('1' <= tegn && tegn <= '9') {    // tegn er i [1, 9]
            return tegn - '0';
        } else if ('A' <= tegn && tegn <= 'Z') {    // tegn er i [A, Z]
            return tegn - 'A' + 10;
        } else if (tegn == '@') {                   // tegn er @
            return 36;
        } else if (tegn == '#') {                   // tegn er #
            return 37;
        } else if (tegn == '&') {                   // tegn er &
            return 38;
        } else if ('a' <= tegn && tegn <= 'z') {    // tegn er i [a, z]
            return tegn - 'a' + 39;
        } else {                                    // tegn er ugyldig
            return -1;
        }
    }
}
