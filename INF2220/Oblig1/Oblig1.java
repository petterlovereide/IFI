import java.util.*;
import java.io.*;

class Oblig1{
  public static Ordbok ordbok = new Ordbok("dictionary.txt");
  public static Scanner in = new Scanner(System.in);
  public static BufferedWriter writer;

  public static void main(String args[]){
    try{
      File fileOut = new File("utskrift.txt");
      FileOutputStream fos = new FileOutputStream(fileOut);
      OutputStreamWriter osw = new OutputStreamWriter(fos);
      writer = new BufferedWriter(osw);
    }
    catch (Exception e){
    }

    System.out.println("Available commands: insert, search, delete, q");
    while(ordbok != null){
      System.out.print("\n" + "Enter command: ");
      String input = in.nextLine();
      if(input.equals("q")){
        quit();
        break;
      }
      if(input.equals("insert")){
        input = in.nextLine();
        ordbok.settInnOpprett(input);
        continue;
      }
      if(input.equals("search")){
        input = in.nextLine();
        String svar = ordbok.ordSok(input);
        if(input == svar){
          System.out.println(input + " was found in the dictionary.");
          continue;
        }
        else{
          ordbok.ordIkkeFunnet(input);
          continue;
        }
      }
      if(input.equals("delete")){
        input = in.nextLine();
        ordbok.ordbokStart.fjernOrd(input, null);
        continue;
      }
      else{
        System.out.println("Command not recognized.");
      }
    }

  }

  public static void quit(){
    try{
      System.out.println("Depth of tree: " + ordbok.depthOfTree());
      writer.write("Depth of tree: " + ordbok.depthOfTree()+ "\r\n");
      int[] array = ordbok.numberPerDepth();
      for(int i = 0; i < array.length; i++){
        System.out.println("Number of nodes with depth " + i + ": " + array[i]);
        writer.write("Number of nodes with depth " + i + ": " + array[i]+ "\r\n");
      }
      System.out.println("Average depth of all nodes: " + ordbok.averageDepth());
      writer.write("Average depth of all nodes: " + ordbok.averageDepth() + "\r\n");
      String firstAndLast[] = ordbok.firstAndLast();
      System.out.println("Alphabetically first word: " + firstAndLast[0]);
      writer.write("Alphabetically first word: " + firstAndLast[0]+ "\r\n");
      System.out.println("Alphabetically last word: " + firstAndLast[1]);
      writer.write("Alphabetically last word: " + firstAndLast[1]+ "\r\n");
      writer.close();
    }catch (Exception e){}
  }

  public static  BufferedWriter getWriter(){
    return writer;
  }
}
