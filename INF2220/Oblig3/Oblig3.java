import java.util.*;
import java.io.*;

class Oblig3 {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Feil antall argumenter.");
            System.exit(0);
        }

        File file_needle = new File(args[0]);
        File file_haystack = new File(args[1]);
        Scanner in_needle = null, in_haystack = null;

        try {
            in_needle = new Scanner(file_needle);
            in_haystack = new Scanner(file_haystack);
        } catch (Exception c) {
            System.out.println("Feil filnavn.");
            System.exit(0);
        }
        char[] haystack_array = null, needle_array = null;

        if (in_haystack.hasNext()) {
            haystack_array = in_haystack.nextLine().toCharArray();
        } else {
            System.out.println("Haystack is empty");
            System.exit(0);
        }

        if (in_needle.hasNext()) {
            needle_array = in_needle.nextLine().toCharArray();
        } else {
            System.out.println("Needle is empty");
            System.exit(0);
        }

        ArrayList<Integer> posArray = new ArrayList<>();
        ArrayList<String> stringArray = new ArrayList<>();

        //finds the last wildcard
        int latest_wildcard = 0;
        for (int i = 0; i < needle_array.length; i++) {
            if (needle_array[i] == '_') {
                latest_wildcard = i + 1;
            }
        }

        int removed = 0;
        int index = 0;
        while (index != -1) {
            index = needle_in_haystack(needle_array, haystack_array, latest_wildcard);
            if (index == -1) {
                break;
            }
            String s = "";
            for (int i = 0; i < needle_array.length; i++) {
                s += haystack_array[index + i];
            }

            //* cuts the array to new length, from index + needle_length
            char[] newArray = new char[haystack_array.length - index - needle_array.length];
            for (int i = index + needle_array.length; i < haystack_array.length; i++) {
                newArray[i - index - needle_array.length] = haystack_array[i];
            }
            haystack_array = newArray;

            posArray.add(index + removed);
            removed += index + needle_array.length;
            stringArray.add(s);
        }

        for (int i = 0; i < posArray.size(); i++) {
            System.out.println(posArray.get(i) + ": " + stringArray.get(i));
        }
    }

    public static int needle_in_haystack(char[] needle_array, char[] haystack_array, int latest_wildcard) {
        int[] badCharShift = new int[256];

        int position = 0;
        int scan = 0;
        int last = needle_array.length - 1;
        int maxposition = haystack_array.length - needle_array.length;

        if (latest_wildcard >= needle_array.length) {
            latest_wildcard = last;
        }

        //can max move the length of the needle - latest_wildcard
        for (int i = 0; i < 256; i++) {
            badCharShift[i] = needle_array.length - latest_wildcard;
        }

        //changes the values of the letters after the last wildcard
        for (int i = latest_wildcard; i < last; i++) {
            badCharShift[needle_array[i]] = last - i;
        }

        while (position <= maxposition) {
            for (scan = last; needle_array[scan] == haystack_array[scan + position] || needle_array[scan] == '_'; scan--) {
                if (scan == 0) {
                    return position;
                }
            }
            position += badCharShift[haystack_array[position + last]];
        }
        return -1;
    }
}