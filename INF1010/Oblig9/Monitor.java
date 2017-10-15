import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.*;
import java.util.concurrent.*;


class Monitor implements Runnable {

TraadSort[] traadArray;
int intervall, antTraader, antKjoring = 0;
static int intervallcounter = 0;
ArrayList<String> usortert;
ArrayList<String[]> taImotArray = new ArrayList<>();
LinkedList<String[]> sortertSamling = new LinkedList<String[]>();

	public Monitor(ArrayList usortert,int antTraader) {
		int storrelse = usortert.size();
		intervall = storrelse/antTraader;
		this.usortert = usortert;
		this.antTraader = antTraader;

		for(int i = 0; i < usortert.size(); i++){
			System.out.println(usortert.get(i));
		}



	}
	public void run() {
		ArrayList samling = new ArrayList();
		 traadArray = new TraadSort[antTraader];

		for(int i = 0; i < antTraader; i++){
			TraadSort traad = new TraadSort();
			Thread t = new Thread(traad);
			t.start();
			traadArray[i] = traad;


		}


	}
	// Gir fra seg 1 usortert Array til TraadSort
	synchronized public String[] hentUsortertArray() {
		int arrayStart = intervall*intervallcounter;
		int arraySlutt = intervall*(1+intervallcounter);

		String [] array = new String [intervall];
		int arrayCounter = 0;

		for(int i = arrayStart; i < arraySlutt; i++){
			array[arrayCounter] = usortert.get(i);
			arrayCounter++;
		}

		intervallcounter++;
		return array;
	} //ikke ferdig maa ta hoyde for oddetall

	synchronized public String [] hentSortertArray() {
	 	String [] temp = null;
	 	try {

	 	if(sortertSamling.peek() == null) {return null;} // wait()
	 	else{temp = sortertSamling.remove();}

	 	}
	 	finally {
	 	}
	 	return temp;


	 }

	synchronized public void taImotSortert(String [] a) {

		if(a != null){
			//System.out.println(Arrays.toString(a));
			taImotArray.add(a);
		}

		try{
			antKjoring++;
			if(antKjoring == antTraader){
				antKjoring = 0;
				startThreads();
				antKjoring++;
			}
			wait();

		}catch(InterruptedException e6) {}

	}

	synchronized public void startThreads(){
		for(int i = 0; i < taImotArray.size(); i++){
			sortertSamling.add(taImotArray.get(i));
		}
		if(sortertSamling.size() == 1){
			System.out.println("Sortering fullfort.");
			String[] utArray = sortertSamling.get(0);
			for(int i = 0; i < utArray.length; i++){
				System.out.println(utArray[i]);
			}
		    System.exit(0);
		}
		taImotArray.clear();
		notifyAll();




	}
}
