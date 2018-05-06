package combination;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import fileOperation.WriterToFile;

public class CombinationString {

    private ArrayList<String> variants = new ArrayList<>();
    private String[] array;
    private int n;
    
    public static void main (String[] args) {
        CombinationString comb = new CombinationString();
        String[] mass = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        long time = System.currentTimeMillis();
        ArrayList<String> combinateIt = comb.combinateIt(mass);
        System.out.println((System.currentTimeMillis() - time) / 1000.0);
        
        WriterToFile writer = new WriterToFile(new File("C:/comb.txt"));
        writer.write(combinateIt);;
        writer.close();
    }

    public ArrayList<String> combinateIt (String[] mass) {
        array = mass;
        n = array.length;
        
        Arrays.sort(array);
        
        oneOptionAdd();
        while (Set()) oneOptionAdd();
        
        return variants;
    }

    private boolean Set () {
        int i = n - 2;
        while (i != -1 && array[i].compareTo(array[i + 1]) >= 0) i--;
        
        if (i == -1) return false;

        int m = n - 1;
        while (array[i].compareTo(array[m]) >= 0) m--;

        swap(i, m);

        int b = i + 1;
        int c = n - 1;

        while (b < c) {
            swap(b, c);
            b++;
            c--;
        }

        return true;
    }

    private void swap (int i, int m) {
        String temp = array[i];
        array[i] = array[m];
        array[m] = temp;
    }

    private void oneOptionAdd () {
        StringBuilder stroka = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            stroka.append(array[i]);
        }
        variants.add(stroka.toString());
    }
}
