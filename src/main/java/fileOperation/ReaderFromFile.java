package fileOperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;

/**
 * @author Corpse
 *
 */
public class ReaderFromFile {
    private File file;
    private Reader reader;
    private BufferedReader bufferedReader;

    /**
     * @param path
     */
    public ReaderFromFile(String path) {
        file = new File(path);
        rederCreator();
    }

    /**
     * @param file
     */
    public ReaderFromFile(File file) {
        this.file = file;
        rederCreator();
    }

    public ReaderFromFile(URL url) {
        file = new File(url.getFile());
        rederCreator();
    }

    private void rederCreator() {
        try {
            reader = new FileReader(file);
            bufferedReader = new BufferedReader(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Считывает строку из файла
     */
    public String readLine() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Считывает весь файл в один String
     * 
     * @return
     */
    public String readAll() {
        StringBuilder content = new StringBuilder();
        while (isReady()) {
            content.append(readLine() + "\n");
        }
        return content.toString();
    }

    /**
     * @return Считывает символ из файла
     */
    public int readInt() {
        try {
            return bufferedReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @return true, если доступны строки для чтения
     */
    public boolean isReady() {
        try {
            return bufferedReader.ready();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Закрывает все потоки вводы-вывода
     */
    public void close() {
        try {
            if (bufferedReader != null)
                bufferedReader.close();
            if (reader != null)
                reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
