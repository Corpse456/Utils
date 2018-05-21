package workWithFiles.fileIO;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Corpse
 */
public class DataReader {

    private DataInputStream reader;

    public DataReader(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            reader = new DataInputStream(bufferedInputStream);
        } catch (FileNotFoundException e) {
            System.out.print("File not found");
        }
    }

    /**
     * @return int из Data файла
     */
    public int readInt() {
        int number;
        try {
            number = reader.readInt();
        } catch (IOException e) {
            System.out.println("Can't read number");
            return Integer.MAX_VALUE;
        }
        return number;
    }

    /**
     * @return массив int из Data файла
     */
    public int[] readArrayInt() {
        ArrayList<Integer> list = null;
        try {
            list = readListInt();
        } catch (IOException e) {
            System.out.println("Can't read numbers");
        }
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private ArrayList<Integer> readListInt() throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        while (true) {
            try {
                list.add(reader.readInt());
            } catch (EOFException e) {
                break;
            }
        }
        return list;
    }

    /**
     * @return List int-ов из Data файла
     */
    public ArrayList<Integer> readIntList() {
        ArrayList<Integer> list = null;
        try {
            list = readListInt();
        } catch (IOException e) {
            System.out.println("Can't read numbers");
        }
        return list;
    }

    /**
     * @return double из Data файла
     */
    public double readDouble() {
        double number;
        try {
            number = reader.readDouble();
        } catch (IOException e) {
            System.out.println("Can't read number");
            return Double.NaN;
        }
        return number;
    }

    /**
     * @return массив double из Data файла
     */
    public double[] readArrayDouble() {
        ArrayList<Double> list = null;
        try {
            list = readListDouble();
        } catch (IOException e) {
            System.out.println("Can't read numbers");
        }
        double[] array = new double[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private ArrayList<Double> readListDouble() throws IOException {
        ArrayList<Double> list = new ArrayList<>();
        while (true) {
            try {
                list.add(reader.readDouble());
            } catch (EOFException e) {
                break;
            }
        }
        return list;
    }

    /**
     * @return List double-ов из Data файла
     */
    public ArrayList<Double> readDoubleList() {
        ArrayList<Double> list = null;
        try {
            return readListDouble();
        } catch (IOException e) {
            System.out.println("Can't read numbers");
        }
        return list;
    }

    /**
     * @return String из Data файла
     */
    public String readString() {
        String text = null;
        try {
            text = readChars();
        } catch (IOException e) {
            System.out.println("Can't read text");
        }
        return text;
    }

    private String readChars() throws IOException {
        return reader.readUTF();
    }

    /**
     * Закрывает поток ввода
     */
    public void close () {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
