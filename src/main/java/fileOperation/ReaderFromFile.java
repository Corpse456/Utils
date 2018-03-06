package fileOperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Neznaev
 *
 */
public class ReaderFromFile {
    private File file;
    private Reader reader;
    private BufferedReader bufferedReader;

    /**
     * @param path - путь к файлу в виде строки
     */
    public ReaderFromFile(String path) {
        file = new File(path);
        rederCreator();
    }

    /**
     * @param file объект File, который необхоимо считать
     */
    public ReaderFromFile(File file) {
        this.file = file;
        rederCreator();
    }

    /**
     * @param url путь к файлу в виде URL
     */
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
     * Считывает строку из файла
     * 
     * @return String со строкой
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
     * @return String сразу со всем текстом файла
     */
    public String readAll() {
        StringBuilder content = new StringBuilder();
        while (isReady()) {
            content.append(readLine() + "\n");
        }
        return content.toString();
    }
    
    /**
     * Cчитывает сразу весь текст файла разбивая его построчно в List<String>
     * 
     * @return List String со всем содержимым фала построчно
     */
    public List<String> readAllAsLIst () {
        List<String> content = new ArrayList<>();
        while (isReady()) {
            content.add(readLine());
        }
        return content;
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
     * Проверяет дальнейшую возможность чтения
     * 
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
            if (bufferedReader != null) bufferedReader.close();
            if (reader != null) reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
