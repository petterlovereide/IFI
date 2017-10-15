import java.util.*;

class EnkelReseptListe implements Iterable{

  private Node lh = new Node(null);
  private Node bak = new Node(null);

  private class Node{
    Resept data = null;
    Node neste = null;

    Node(Resept data){
      this.data = data;
    }
  }

  public Iterator iterator(){
	   return new ListeIterator();
  }

  private class ListeIterator implements Iterator{

    	Node pos = lh;

    	public boolean hasNext(){
        if(pos.neste != null){
          return true;
        }
          return false;
    	}

    	public Resept next(){
        if(hasNext()){
          pos = pos.neste;
      		return pos.data;
        }
        return null;
    	}

    	public void remove(){

    	}

  }

  public void settInnForan(Resept data){
    Node temp = new Node(data);
    if(tom()){
      temp.neste = bak;
    }
    else{
      temp.neste = lh.neste;
    }
    lh.neste = temp;

  }

  public boolean tom(){
    if(lh.neste == null){
      return true;
    }
    return false;
  }


  public Resept finnResept(int reseptNummer){
    Node temp = lh;
    try{
      while(temp.neste != null){
        temp = temp.neste;
        if(reseptNummer == temp.data.getReseptNummer()){
          System.out.println("Respet funnet.");
          return temp.data;
        }
      }
    }
    catch(Exception e){
      System.out.println("Resept ikke funnet.");
      return null;
    }
    return null;
  }

}
