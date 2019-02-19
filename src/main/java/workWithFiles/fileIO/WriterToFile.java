package workWithFiles.fileIO;

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
 */
public class WriterToFile {
    private File file;
    private Writer writer = null;
    private BufferedWriter bufferedWriter = null;

    /**
     * @param path where nedd to save
     */
    public WriterToFile(String path) {
        file = new File(path);
        writerCreator(false);
    }

    /**
     * @param f - file where nedd to save
     */
    public WriterToFile(File f) {
        file = f;
        writerCreator(false);
    }

    /**
     * @param path   where nedd to save
     * @param append true if need to add to exists file</br>
     *               false - write a new
     */
    public WriterToFile(String path, boolean append) {
        file = new File(path);
        writerCreator(append);
    }

    /**
     * @param f      - file where nedd to save
     * @param append true if need to add to exists file</br>
     *               false - write a new
     */
    public WriterToFile(File f, boolean append) {
        file = f;
        writerCreator(append);
    }

    private void writerCreator(boolean append) {
        if (!file.isFile() || (!append && file.exists())) {
            createFile();
        }

        try {
            writer = new FileWriter(file, append);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bufferedWriter = new BufferedWriter(writer);
    }

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
     * @param list добавляемый в файл
     */
    public <N> void write(List<N> list) {
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                writeLine(list.get(i).toString());
            } else {
                write(list.get(i) + ",");
            }
        }
    }

    /**
     * @param map       добавляемая в файл
     * @param separator - разделитель ключа и значения
     */
    public <C, N> void write(Map<C, N> map, String separator) {
        for (Entry<C, N> entry : map.entrySet()) {
            writeLine(entry.getKey() + separator + entry.getValue());
        }
    }

    /**
     * @param map добавляемая в файл
     */
    public <C, N> void write(Map<C, N> map) {
        write(map, "=");
    }

    /**
     * Закрывает все потоки вводы-вывода
     */
    public void close() {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
