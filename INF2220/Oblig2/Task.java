import java.util.*;

class Task {
    int id = 0, time = 0, manpower = 0, earlistStart = -1, latestStart, slack;
    String name = null;
    List<Task> outEdges = null;

    public void setData(int id, int time, int manpower, String name, List<Task> outEdges) {
        this.outEdges = outEdges;
        this.id = id;
        this.time = time;
        this.manpower = manpower;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public void printData() {
        System.out.println("ID: " + id + ". Name: " + name + ". Time: " + time + ". Manpower: " + manpower + " ");
        System.out.println("Earliest start: " + earlistStart + ". Latest start: " + latestStart + ". Slack: " + slack);
        System.out.print("Dependecies: ");
        for (Task task : outEdges) {
            System.out.print(task.getID() + ", ");
        }
        System.out.println();
    }

    public void removeDep(Task task) {
        for (int i = 0; i < outEdges.size(); i++) {
            if (outEdges.get(i).getID() == task.getID()) {
                outEdges.remove(i);
            }
        }
    }

    // * Sjekker om den er avhengige av den opprinnelige tasken
    //   Hvis den er det, er arrayet ikke oppnaelig
    public List<Integer> sjekkSirkel(Task task) {
        List<Integer> sirkelArray = new ArrayList<>();
        if (outEdges == null) {
            return null;
        }
        for (Task dep : outEdges) {
            if (dep.getID() == task.getID()) {
                sirkelArray.add(dep.getID());
                return sirkelArray;
            }
            sirkelArray = dep.sjekkSirkel(task);
            if (sirkelArray != null) {
                sirkelArray.add(dep.getID());
                return sirkelArray;
            }
        }
        return null;
    }

    public void setEarliestStart(int time) {
        earlistStart = time;
    }

    public int getEarliestStart() {
        return earlistStart;
    }

    public void setLatestStart(int time) {
        latestStart = time;
    }

    public int getLatestStart() {
        return latestStart;
    }

    public void setSlack() {
        slack = latestStart - earlistStart;
    }

    public List<Task> getDep() {
        return outEdges;
    }

    public String getName() {
        return name;
    }

    public int getManpower() {
        return manpower;
    }

    public int getTime() {
        return time;
    }

    public void jobb() {
        time--;
    }
}