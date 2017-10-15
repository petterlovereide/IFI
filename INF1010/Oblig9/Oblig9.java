import java.util.*;
import java.io.*;

class Oblig9 {

	static Monitor monitor;

	public static void main(String [] argumenter) {
		 int antTraader = Integer.parseInt(argumenter[0]);
     String filNavn = argumenter[1];
     String utnavn = argumenter[2];
     ArrayList<String> usortert = filLeser(filNavn);
     monitor = new Monitor(usortert,antTraader);
     Thread traad = new Thread(monitor);
     traad.start();

	}
	public static ArrayList<String> filLeser(String filNavn){
        File fil = new File(filNavn);
        ArrayList<String> a = new ArrayList<String>();

        try {

	        Scanner in = new Scanner(fil);
	    		int antallOrd = 1+in.nextInt();
	        String innlest = "";
	        while(in.hasNextLine()) {
	            innlest = in.nextLine();
	            a.add(innlest);
        }

 		if(antallOrd != a.size()){return null;} //feil antall ord
    } catch (FileNotFoundException e) {}

    return a;
    }
	public void filSkriver(String filnavn) {
		File fil = new File(filnavn);

		try{
		PrintWriter filSkriver = new PrintWriter(fil);
	}catch(FileNotFoundException e1) {}

		//m� vente til vi f�tt et sortert array.
	}
	public static Monitor getMonitor() {
		return monitor;
	}
}
