import java.util.*;

class SortertEnkelListe<T extends Comparable<T>> implements AbstraktSortertEnkelListe<T>, Iterable<T>{

  private Node lh = null;

  public SortertEnkelListe(){
	   lh = new Node(null);
  }

  private class Node{
    	T data;
    	Node neste = null;

    	Node (T data){
    	    this.data = data;
    	}

    	void skriv(){
    	    if (neste != null){
            neste.skriv();
          }
          System.out.println(data.toString());
    	}
    }


  boolean tomListe(){
     return lh.neste == null;
  }

  public Iterator<T> iterator(){
	   return new ListeIterator();
  }

  private  class ListeIterator implements Iterator<T>{

    	Node pos = lh;

    	public boolean hasNext(){
    	    if(pos.neste != null){
            return true;
          }
          return false;
    	}

    	public T next(){
        if(hasNext()){
          pos = pos.neste;
      		return pos.data;
        }
        return null;
    	}

    	public void remove(){

    	}

  }


  public void sorterListe(){
	   if (! tomListe() ){

       ArrayList<T> array = new ArrayList<T>();
       Iterator<T> it = iterator();
       while(it.hasNext()){
         T t = taUtForan();
         array.add(t);
       }
       Collections.sort(array);
       for(int i = 0; i < array.size(); i++){
         Node temp = new Node(array.get(i)) ;
         temp.neste = lh.neste;
         lh.neste = temp;
       }
	   }
    }

  public void settInnForan(T o){
    	Node temp = new Node(o);
      temp.neste = lh.neste;
      lh.neste = temp;

      sorterListe();
    }

    public T getData(String key){
      Iterator<T> it = iterator();
      Node temp = lh;
      while(it.hasNext()){
        temp = temp.neste;

        if(temp.data.toString().equals(key)){
          return temp.data;
        }

      }

      return null;
    }

    public T taUtForan(){
        Node temp = lh.neste;
	      lh.neste = lh.neste.neste;
	      return temp.data;
    }


    public void skrivRek() {
	     if (lh.neste != null){
         System.out.println("Utskrift fra minste til storste:");
         System.out.println("----------");
         lh.neste.skriv();
         System.out.println("----------");
       }
    }





}
