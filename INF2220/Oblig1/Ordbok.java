import java.util.*;
import java.io.*;

class Ordbok {
    Node ordbokStart = null;
    ArrayList<Node> nodeList = new ArrayList<>();
    BufferedWriter writer;

    Ordbok(String filnavn) {
        try {
            File fil = new File(filnavn);
            Scanner fraFil = new Scanner(fil);
            while (fraFil.hasNextLine()) {
                String data = fraFil.nextLine();
                if (ordbokStart == null) {
                    Node nyNode = new Node(data);
                    ordbokStart = nyNode;
                    nodeList.add(nyNode);
                } else {
                    settInnOpprett(data);
                }
            }
        } catch (Exception e) {
            System.out.println("Could not read file.");
        }
    }

    class Node {
        Node venstre, hoyre;
        String data;
        int depth;

        public Node(String data) {
            this.data = data;
            venstre = null;
            hoyre = null;
            depth = 0;
        }

        public void settInn(String string, Node nyNode) {
            int i = nyNode.getData().compareTo(data);
            if (i < 0) {
                nyNode.depth++;
                if (venstre != null) {
                    venstre.settInn(string, nyNode);
                } else {
                    venstre = nyNode;
                }
            }
            if (i > 0) {
                nyNode.depth++;
                if (hoyre != null) {
                    hoyre.settInn(string, nyNode);
                } else {
                    hoyre = nyNode;
                }
            }
            if (i == 0) {
                System.out.println("The word already exists in the dictionary.");
            }
        }

        public void fjernOrd(String string, Node parent) {
            int i = string.compareTo(data);
            if (parent == null) {
                parent = ordbokStart;
            }
            if (i == 0) {
                if (parent.venstre.getData().equals(data)) {
                    parent.venstre = this.venstre;
                    Node mid = this;
                    while (mid.hoyre != null) {
                        mid = mid.hoyre;
                    }
                    mid.hoyre = this.hoyre;
                }
                if (parent.hoyre.getData().equals(data)) {
                    parent.hoyre = this.hoyre;
                    Node mid = this;
                    while (mid.venstre != null) {
                        mid = mid.venstre;
                    }
                    mid.venstre = this.venstre;
                }
                System.out.println("Ordet " + string + " ble fjernet fra ordboken.");
            }
            if (i < 0) {
                if (venstre != null) {
                    venstre.fjernOrd(string, this);
                } else {
                    System.out.println("Ordet " + string + " finnes ikke i ordboken, og kan ikke fjernes.");
                }
            }
            if (i > 0) {
                if (hoyre != null) {
                    hoyre.fjernOrd(string, this);
                } else {
                    System.out.println("Ordet " + string + " finnes ikke i ordboken, og kan ikke fjernes.");
                }
            }
        }

        public String getData() {
            return data;
        }

        public int getDepth() {
            return depth;
        }
    }

    public void settInnOpprett(String data) {
        Node nyNode = new Node(data);
        nodeList.add(nyNode);
        ordbokStart.settInn(data, nyNode);
    }

    public ArrayList<String> ordIkkeFunnet(String string) {
        long start = System.currentTimeMillis();
        ArrayList<String> returnArray = new ArrayList<>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] charArray = string.toCharArray();

        //To bokstaver har byttet plass
        for (int i = 0; i < string.length() - 1; i++) {
            char[] midArray = charArray.clone();
            char midChar = midArray[i];
            midArray[i] = midArray[i + 1];
            midArray[i + 1] = midChar;
            String s = new String(midArray);
            if (ordSok(s) != null) {
                returnArray.add(s);
            }
        }

        //En bokstav har blitt byttet ut med en annen
        for (int i = 0; i < string.length(); i++) {
            char[] midArray = charArray.clone();
            for (int j = 0; j < alphabet.length; j++) {
                midArray[i] = alphabet[j];
                String s = new String(midArray);
                if (ordSok(s) != null) {
                    returnArray.add(s);
                }
            }
        }

        //En bokstav har blitt fjernet
        for (int i = 0; i < string.length(); i++) {
            char[] midArray = charArray.clone();
            for (int j = 0; j < alphabet.length; j++) {
                char[] mid = new char[string.length() + 1];
                for (int k = 0; k < mid.length; k++) {
                    if (k == i) {
                        mid[k] = alphabet[j];
                    }
                    if (k > i) {
                        mid[k] = midArray[k - 1];
                    }
                    if (k < i) {
                        mid[k] = midArray[k];
                    }
                }
                String s = new String(mid);
                if (ordSok(s) != null) {
                    returnArray.add(s);
                }
            }
        }

        //En bokstav har blitt lagt til
        for (int i = 0; i < string.length(); i++) {
            char[] midArray = charArray.clone();
            String s = "";
            for (int j = 0; j < string.length(); j++) {
                if (i != j) {
                    s += midArray[j];
                }
            }
            if (ordSok(s) != null) {
                returnArray.add(s);
            }
        }

        try {
            long slutt = System.currentTimeMillis() - start;
            float time = slutt / 1000F;
            writer = Oblig1.getWriter();
            System.out.println(string + " was not found in the dictionary.\n" + returnArray.size() + " similar words was found in " + time + " seconds: ");
            writer.write(string + " was not found in the dictionary.\n" + returnArray.size() + " similar words was found in " + time + " seconds: " + "\r\n");
            for (int i = 0; i < returnArray.size(); i++) {
                System.out.print(returnArray.get(i) + ", ");
                writer.write(returnArray.get(i) + ", \r\n");
            }
        } catch (Exception e) {
        }
        return returnArray;
    }

    public String ordSok(String string) {
        Boolean ordFunnet = false;
        Node naa = ordbokStart;
        while (!ordFunnet) {
            if (naa.getData().equals(string)) {
                return string;
            }

            int i = string.compareTo(naa.getData());
            if (i < 0) {
                naa = naa.venstre;
                if (naa == null) {
                    return null;
                }
            }
            if (i >= 0) {
                naa = naa.hoyre;
                if (naa == null) {
                    return null;
                }
            }
        }
        return null;
    }

    public int depthOfTree() {
        int depth = 0;
        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i).getDepth() > depth) {
                depth = nodeList.get(i).getDepth();
            }
        }
        return depth;
    }

    public int[] numberPerDepth() {
        int[] array = new int[depthOfTree() + 1];

        for (int j = 0; j < array.length; j++) {
            for (int i = 0; i < nodeList.size(); i++) {
                if (j == nodeList.get(i).getDepth()) {
                    array[j]++;
                }
            }
        }
        return array;
    }

    public double averageDepth() {
        double total = 0;
        for (int i = 0; i < nodeList.size(); i++) {
            total += nodeList.get(i).getDepth();
        }
        double average = total / nodeList.size();
        return average;
    }

    public String[] firstAndLast() {
        String[] array = new String[2];
        Node mid = ordbokStart;
        while (mid.venstre != null) {
            mid = mid.venstre;
        }
        array[0] = mid.getData();

        mid = ordbokStart;
        while (mid.hoyre != null) {
            mid = mid.hoyre;
        }
        array[1] = mid.getData();

        return array;
    }
}