package listCreators;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fileOperation.GZIPExtractor;
import fileOperation.ReaderFromFile;

public class NamesAndWords {

    /**
     * @return ArrayList с английскими мужскими именами
     */
    public static List<String> englishManNames () {
        return readFromTxt("EngMan.txt");
    }

    /**
     * @return ArrayList с английскими женскими именами
     */
    public static List<String> englishWomanNames () {
        return readFromTxt("EngWoman.txt");
    }

    /**
     * @return ArrayList с русскими женскими именами
     */
    public static List<String> russianWomanNames () {
        return readFromTxt("rusWoman.txt");
    }

    /**
     * @return ArrayList с русскими мужскими именами
     */
    public static List<String> russianManNames () {
        return readFromTxt("rusMan.txt");
    }

    /**
     * @return ArrayList с ~100000 словами англисйкого языка
     */
    public static List<String> manyEnglishWords () {
        return readFromTxt("words.txt");
    }

    /**
     * @return ArrayList with many english last names
     */
    public static List<String> englishLastNames () {
        return readFromTxt("EngLastNames.txt");
    }
    
    /**
     * @return ArrayList with many english nicknames
     */
    public static List<String> englishManNickNames () {
        return readFromTxt("ManNicknames.txt");
    }
    
    /**
     * @return ArrayList with many english nicknames
     */
    public static List<String> englishWomanNickNames () {
        return readFromTxt("WomanNicknames.txt");
    }

    private static List<String> readFromTxt (String fileName) {
        URL resource = NamesAndWords.class.getClassLoader().getResource(fileName);
        ReaderFromFile reader = new ReaderFromFile(resource);
        List<String> answer = reader.readAllAsLIst();
        reader.close();
        return answer;
    }
    
    /**
     * @return ArrayList with many belorussian cities
     */
    public static List<String> belorussianCities () {
        GZIPExtractor gzip = new GZIPExtractor();
        URL fileName = NamesAndWords.class.getClassLoader().getResource("BelarusCities.csv.gz");

        List<String> content = gzip.fromGzipToMemoryAsList(fileName);
        List<String> answer = new ArrayList<>();
        
        for (String string : content) {
            String[] split = string.split(",");
            if (split.length > 0) answer.add(split[0]);
        }
        return answer;
    }
}
