import java.util.*;
import java.io.*;

class Oblig2{

	public static void main(String[] args){

		Innleser innleser = new Innleser();
		List<Task> taskArray = innleser.les_inn(args[0]);

		Gjennomkjoring gjennomkjoring = new Gjennomkjoring();
		taskArray = gjennomkjoring.kjor(taskArray);

		for(int i = 1; i < taskArray.size(); i++){
			taskArray.get(i).printData();
		}
	}
}
