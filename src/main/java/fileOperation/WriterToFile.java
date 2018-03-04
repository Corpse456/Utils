package fileOperation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Corpse
 *
 */
public class WriterToFile {
    private File file;
    private Writer writer = null;
    private BufferedWriter bufferedWriter = null;

    public WriterToFile(String path) {
        file = new File(path);
        writerCreator();
    }

    public WriterToFile(File f) {
        file = f;
        writerCreator();
    }

    /**
     * 
     */
    private void writerCreator() {
        if (!file.isFile() || file.exists()) createFile();
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedWriter = new BufferedWriter(writer);
    }

    /**
     * путь к файлу задайётся параметром path
     * 
     * @param path
     */
    private void createFile() {
        file.mkdirs();
        if (!file.isFile() || file.exists()) {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Добавляет начало новой строки
     * 
     */
    public void writeLine() {
        write("\r\n");
    }

    /**
     * @param str Добавляемая в файл строка
     */
    public void writeLine(String str) {
        write(str + "\r\n");
    }

    /**
     * @param i - int, добавляемый в файл
     */
    public void writeLine(int i) {
        write(i + "\r\n");
    }

    /**
     * @param str Добавляемая в файл строка
     */
    public void write(String str) {
        try {
            bufferedWriter.write(str);
            // System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param i - Добавляемый в файл int
     */
    public void write(int i) {
        write(i + "");
    }

    /**
     * @param map добавляемая в файл 
     */
    public <N> void write(List<N> list) {
        for (N n : list) {
            writeLine(n.toString());
        }
    }
    /**
     * @param map добавляемая в файл 
     */
    public <C, N> void write(Map<C, N> map) {
        for (Entry<C, N> entry : map.entrySet()) {
            writeLine(entry.getKey() + ": " + entry.getValue());
        }
    }

    /**
     * Закрывает все потоки вводы-вывода
     */
    public void close() {
        try {
            if (bufferedWriter != null) bufferedWriter.close();
            if (writer != null) writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
