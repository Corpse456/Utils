package fileOperation;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author Corpse
 */
public class DataWriter {

    private DataOutputStream writer;
    
    public DataWriter(String path) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            writer = new DataOutputStream(bufferedOutputStream);
        } catch (FileNotFoundException e) {
            System.out.print("File not found");
        }
    }

    /**
     * @param number - int, записываемый в Data файл
     * @return true, если операция записи выполнена успешно
     */
    public boolean write(int number) {
        try {
            writer.writeInt(number);
            writer.close();
        } catch (IOException e) {
            System.out.println("Can't write number");
            return false;
        }
        return true;
    }
    
    /**
     * @param numbers - массив int-ов, записываемый в Data файл
     * @return true, если операция записи выполнена успешно
     */
    public boolean write(int[] numbers) {
        try {
            for (int i = 0; i < numbers.length; i++) {
                writer.writeInt(numbers[i]);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Can't write some number");
            return false;
        }
        return true;
    }
    
    /**
     * @param numbers - List int-ов, записываемый в Data файл
     * @return true, если операция записи выполнена успешно
     */
    public boolean writeIntList(List<Integer> numbers) {
        try {
            Iterator<Integer> iterator = numbers.iterator();
            while (iterator.hasNext()) {
                writer.writeInt(iterator.next());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Can't write some number");
            return false;
        }
        return true;
    }
    
    /**
     * @param number - double, записываемый в Data файл
     * @return true, если операция записи выполнена успешно
     */
    public boolean write(double number) {
        try {
            writer.writeDouble(number);
            writer.close();
        } catch (IOException e) {
            System.out.println("Can't write number");
            return false;
        }
        return true;
    }
    
    /**
     * @param numbers - массив double, записываемый в Data файл
     * @return true, если операция записи выполнена успешно
     */
    public boolean write(double[] numbers) {
        try {
            for (int i = 0; i < numbers.length; i++) {
                writer.writeDouble(numbers[i]);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Can't write some number");
            return false;
        }
        return true;
    }
    
    /**
     * @param numbers - List double, записываемый в Data файл
     * @return true, если операция записи выполнена успешно
     */
    public boolean writeDoubleList(List<Double> numbers) {
        try {
            Iterator<Double> iterator = numbers.iterator();
            while (iterator.hasNext()) {
                writer.writeDouble(iterator.next());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Can't write some number");
            return false;
        }
        return true;
    }
    
    /**
     * @param text - String, записываемый в Data файл
     * @return true, если операция записи выполнена успешно
     */
    public boolean write(String text) {
        try {
            writer.writeChars(text);
            writer.close();
        } catch (IOException e) {
            System.out.println("Can't write text");
            return false;
        }
        return true;
    }
}
