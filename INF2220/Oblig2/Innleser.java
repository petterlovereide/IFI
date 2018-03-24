import java.util.*;
import java.io.*;

class Innleser {
    // * Leser inn antall tasks forst
// * Leser inn hele linjer om gangen, deretter deler den opp til
//   id, manpower, time, name og dependecies
// * Bruker substring til a fjerne det som er lest
    public List<Task> les_inn(String filnavn) {
        File file = new File(filnavn);
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (Exception c) {
            System.out.println("File not found");
            System.exit(0);
        }

        int number_of_tasks = Integer.parseInt(in.nextLine());
        List<Task> taskArray = new ArrayList<>();
        for (int i = 0; i <= number_of_tasks; i++) {
            taskArray.add(new Task());
        }
        String all = in.nextLine();
        for (int i = 0; i < number_of_tasks; i++) {
            all = in.nextLine();
            int id = 0;
            int time = 0;
            int manpower = 0;

            String stringID = "";
            for (int j = 0; j < all.length(); j++) {
                if (all.charAt(j) == ' ') {
                    all = all.substring(j, all.length());
                    id = Integer.parseInt(stringID);
                    break;
                }
                stringID += all.charAt(j);
            }
            String name = "";
            for (int j = 0; j < all.length(); j++) {
                if (all.charAt(j) != ' ' && all.charAt(j) != '	') {
                    name = name + all.charAt(j);
                }
                if (name != "" && (all.charAt(j) == ' ' || all.charAt(j) == '	')) {
                    all = all.substring(j, all.length());
                    break;
                }
            }
            String stringTime = "";
            forTime:
            for (int j = 0; j < all.length(); j++) {
                if (all.charAt(j) != ' ' && all.charAt(j) != '	') {
                    for (int k = j; k < all.length(); k++) {
                        if (all.charAt(k) == ' ') {
                            time = Integer.parseInt(stringTime);
                            all = all.substring(k, all.length());
                            break forTime;
                        }
                        stringTime += all.charAt(k);
                    }
                }
            }
            String stringManpower = "";
            forManpower:
            for (int j = 0; j < all.length(); j++) {
                if (all.charAt(j) != ' ' && all.charAt(j) != '	') {
                    for (int k = j; k < all.length(); k++) {
                        if (all.charAt(k) == ' ') {
                            manpower = Integer.parseInt(stringManpower);
                            all = all.substring(k, all.length());
                            break forManpower;
                        }
                        stringManpower += all.charAt(k);
                    }
                }
            }
            ArrayList<Task> dep = new ArrayList<>();
            for (int j = 0; j < all.length(); j++) {
                if (all.charAt(j) != ' ' && all.charAt(j) != '	') {
                    String stringDep = "";
                    for (int k = j; k < all.length(); k++) {
                        if (all.charAt(k) == ' ') {
                            dep.add(taskArray.get(Integer.parseInt(stringDep)));
                            j = k;
                            break;
                        }
                        if (k == all.length() - 1) {
                            stringDep += all.charAt(k);
                            dep.add(taskArray.get(Integer.parseInt(stringDep)));
                            break;
                        }
                        stringDep += all.charAt(k);
                    }
                }
            }
            taskArray.get(id).setData(id, time, manpower, name, dep);
        }
        return taskArray;
    }
}