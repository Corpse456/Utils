package combination;

import java.util.ArrayList;
import java.util.Arrays;

import workWithFiles.fileIO.WriterToFile;

public class CombinationString {

    private ArrayList<String> variants = new ArrayList<>(20000000);
    private String[] array;
    private int length;

    public static void main2 (String[] args) {
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
        } while (canSet());

        return variants;
    }

    private boolean canSet() {
        int leftBorder = length - 2;
        while (leftBorder != -1 && array[leftBorder].compareTo(array[leftBorder + 1]) >= 0) leftBorder--;

        if (leftBorder == -1) return false;

        int rigthBorder = length - 1;
        while (array[leftBorder].compareTo(array[rigthBorder]) >= 0) rigthBorder--;

        swap(leftBorder, rigthBorder);

        sortAfterLeftBorder(leftBorder + 1, length - 1);

        return true;
    }

    private void sortAfterLeftBorder (int startIndex, int endIndex) {
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
