package listCreators;

import java.util.ArrayList;
import java.util.List;

import fileOperation.ReaderFromFile;

public class NamesAndWords {
    
    /**
     * @return ArrayList с английскими мужскими именами
     */
    public static List<String> englishManNames() {
	List<String> list = new ArrayList<>();
	ReaderFromFile reader = new ReaderFromFile(ReaderFromFile.class.getClassLoader().getResource("EngMan.txt"));
	while (reader.isReady()) {
	   list.add(reader.readLine());
	}
	reader.close();
	return list;
    }
    
    /**
     * @return ArrayList с английскими женскими именами
     */
    public static List<String> englishWomanNames() {
	List<String> list = new ArrayList<>();
	ReaderFromFile reader = new ReaderFromFile(ReaderFromFile.class.getClassLoader().getResource("EngWoman.txt"));
	while (reader.isReady()) {
	    list.add(reader.readLine());
	}
	reader.close();
	return list;
    }
    
    /**
     * @return ArrayList с русскими женскими именами
     */
    public static List<String> russianWomanNames() {
	List<String> list = new ArrayList<>();
	ReaderFromFile reader = new ReaderFromFile(ReaderFromFile.class.getClassLoader().getResource("rusWoman.txt"));
	while (reader.isReady()) {
	    list.add(reader.readLine());
	}
	reader.close();
	return list;
    }
    
    /**
     * @return ArrayList с русскими мужскими именами
     */
    public static List<String> russianManNames() {
	List<String> list = new ArrayList<>();
	ReaderFromFile reader = new ReaderFromFile(ReaderFromFile.class.getClassLoader().getResource("rusWoman.txt"));
	while (reader.isReady()) {
	    list.add(reader.readLine());
	}
	reader.close();
	return list;
    }
    
    /**
     * @return ArrayList с ~100000 словами англисйкого языка
     */
    public static List<String> manyEnglishWords() {
	List<String> list = new ArrayList<>();
	ReaderFromFile reader = new ReaderFromFile(ReaderFromFile.class.getClassLoader().getResource("words.txt"));
	while (reader.isReady()) {
	    list.add(reader.readLine());
	}
	reader.close();
	return list;
    }
}
