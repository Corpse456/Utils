package combination;

import java.util.ArrayList;
import java.util.Arrays;

import fileOperation.WriterToFile;

public class CombinationString {

    private ArrayList<String> variants = new ArrayList<>(20000000);
    private String[] array;
    private int length;

    public static void main (String[] args) {
        String[] mass = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "A" };
        
        long time = System.currentTimeMillis();
        CombinationString comb = new CombinationString();
        ArrayList<String> combinateIt = comb.combinateIt(mass);
        System.out.println((System.currentTimeMillis() - time) / 1000.0);

        WriterToFile writer = new WriterToFile("C:/comb.txt");
        writer.write(combinateIt);
        writer.close();
    }

    public ArrayList<String> combinateIt (String[] mass) {
        array = mass;
        length = array.length;

        Arrays.sort(array);

        do {
            oneOptionAdd();
        } while (Set());

        return variants;
    }

    private boolean Set () {
        int i = length - 2;
        while (i != -1 && array[i].compareTo(array[i + 1]) >= 0) i--;

        if (i == -1) return false;

        int m = length - 1;
        while (array[i].compareTo(array[m]) >= 0) m--;

        swap(i, m);

        sortAfterI(i + 1, length - 1);

        return true;
    }

    private void sortAfterI (int startIndex, int endIndex) {
        while (startIndex < endIndex) {
            swap(startIndex, endIndex);
            startIndex++;
            endIndex--;
        }
    }

    private void swap (int x, int y) {
        String temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    private void oneOptionAdd () {
        StringBuilder stroka = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            stroka.append(array[i]);
        }
        variants.add(stroka.toString());
    }
}
