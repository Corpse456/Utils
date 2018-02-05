package fileOperation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Corpse
 *
 */
public class FileWorks {
    private File file;
    private Writer writer = null;
    private BufferedWriter bufferedWriter = null;
    private Reader reader;
    private BufferedReader bufferedReader;

    
    public FileWorks() {
    }
    
    public FileWorks(String path) {
	try {
	    reader = new FileReader(path);
	    bufferedReader = new BufferedReader(reader);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
    }
    
    public FileWorks(File f) {
	try {
	    reader = new FileReader(f);
	    bufferedReader = new BufferedReader(reader);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
    }

    /**
     * путь к файлу задайётся параметром path
     * 
     * @param path
     */
    public void createFile(String path) {
	file = new File(path);
	file.mkdirs();
	if (!file.isFile() || file.exists()) {
	    file.delete();
	    try {
		file.createNewFile();
		writer = new FileWriter(file);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	bufferedWriter = new BufferedWriter(writer);
    }
    
    /**
     * Добавляет начало новой строки
     *            
     */
    public void writeLine() {
	try {
	    bufferedWriter.write("\r\n");
	    //System.out.println(str);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * @param str
     *            Добавляемая в файл строка
     */
    public void writeLine(String str) {
	try {
	    bufferedWriter.write(str + "\r\n");
	    //System.out.println(str);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * @param str
     *            Добавляемая в файл строка
     */
    public void write(String str) {
	try {
	    bufferedWriter.write(str);
	    //System.out.println(str);
	} catch (IOException e) {
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
	    if (bufferedWriter != null) bufferedWriter.close();
	    if (writer != null) writer.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
