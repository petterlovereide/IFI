import java.util.*;

class Gjennomkjoring {
// * Oppretter kopi av arrayet for a kjore.
// * Sjekker om arraey kan kjores
// * Hvis arrayet kan kjores, kjorer det gjennom og legger til
//   tidligste start, seneste start og slack til det orginale arrayet

    public List<Task> kjor(List<Task> arrayIn) {
        int time = 0;
        List<Integer> starting = new ArrayList<>();
        List<Integer> finishing = new ArrayList<>();
        List<Task> taskArray = copyList(arrayIn);

        Boolean gjennomforbar = sjekkGjennomforbar(arrayIn);
        if (!gjennomforbar) {
            return arrayIn;
        }

        while (taskArray.size() > 1) {
            int manpower = 0;
            for (int i = 1; i < taskArray.size(); i++) {
                if (taskArray.get(i).getDep().size() == 1) {
                    if (taskArray.get(i).getEarliestStart() == -1) {
                        starting.add(taskArray.get(i).getID());
                        taskArray.get(i).setEarliestStart(time);
                        arrayIn.get(taskArray.get(i).getID()).setEarliestStart(time);
                    }
                    taskArray.get(i).jobb();
                    manpower += taskArray.get(i).getManpower();
                }
            }
            for (int i = 1; i < taskArray.size(); i++) {
                if (taskArray.get(i).getTime() == 0) {
                    finishing.add(taskArray.get(i).getID());
                    for (int j = 1; j < taskArray.size(); j++) {
                        taskArray.get(j).removeDep(taskArray.get(i));
                    }
                    taskArray.remove(i);
                    i--;
                }
            }
            if (starting.size() > 0 || finishing.size() > 0) {
                System.out.println("Time: " + time + "     Manpower: " + manpower);
                for (int s : starting) {
                    System.out.println("	Starting " + s);
                }
                for (int f : finishing) {
                    System.out.println("	Finishing " + f);
                }
                starting.clear();
                finishing.clear();
            }
            time++;
        }
        arrayIn = setLatestStartAndSlack(arrayIn);
        return arrayIn;
    }

    public List<Task> setLatestStartAndSlack(List<Task> taskArray) {
        for (int i = 1; i < taskArray.size(); i++) {
            int latestStart = taskArray.get(i).getEarliestStart();

            List<Task> task_leads_to = new ArrayList<>();
            Task task = taskArray.get(i);
            for (int j = 1; j < taskArray.size(); j++) {
                List<Task> mid = taskArray.get(j).getDep();
                for (int k = 0; k < mid.size() - 1; k++) {
                    if (task.getID() == mid.get(k).getID()) {
                        task_leads_to.add(taskArray.get(j));
                    }
                }
            }

            for (int j = 0; j < task_leads_to.size(); j++) {
                if (latestStart == taskArray.get(i).getEarliestStart()) {
                    latestStart = task_leads_to.get(j).getEarliestStart() - taskArray.get(i).getTime();
                    taskArray.get(i).setLatestStart(latestStart);
                }
                if (latestStart > task_leads_to.get(j).getEarliestStart() - taskArray.get(i).getTime()) {
                    latestStart = task_leads_to.get(j).getEarliestStart() - taskArray.get(i).getTime();
                    taskArray.get(i).setLatestStart(latestStart);
                }
            }
            if (task_leads_to.size() == 0) {
                taskArray.get(i).setLatestStart(taskArray.get(i).getEarliestStart());
            }
            taskArray.get(i).setSlack();

        }
        return taskArray;
    }

    public List<Task> copyList(List<Task> arrayIn) {
        List<Task> taskArray = new ArrayList<>();
        for (int i = 0; i < arrayIn.size(); i++) {
            taskArray.add(new Task());
        }
        for (int i = 1; i < taskArray.size(); i++) {
            List<Task> dep = new ArrayList<>();
            for (int j = 0; j < arrayIn.get(i).getDep().size(); j++) {
                dep.add(taskArray.get(arrayIn.get(i).getDep().get(j).getID()));
            }
            taskArray.get(i).setData(arrayIn.get(i).getID(), arrayIn.get(i).getTime(), arrayIn.get(i).getManpower(),
                    arrayIn.get(i).getName(), dep);
        }
        return taskArray;
    }

    public Boolean sjekkGjennomforbar(List<Task> arrayIn) {
        for (int i = 1; i < arrayIn.size(); i++) {
            List<Integer> tasks_rekke = arrayIn.get(i).sjekkSirkel(arrayIn.get(i));
            if (tasks_rekke != null) {
                tasks_rekke.add(i);
                System.out.print("Denne rekken er ikke gjennomforbar: ");
                for (int j = tasks_rekke.size() - 1; j >= 0; j--) {
                    System.out.print(tasks_rekke.get(j) + ", ");
                }
                return false;
            }
        }
        return true;
    }
}