package workWithFiles.fileListings;

import workWithFiles.fileIO.ReaderFromFile;
import workWithFiles.fileIO.WriterToFile;

import java.util.ArrayList;
import java.util.List;

public class GamesIntoOneFile {

    private final static String FOLDER = "d:\\Games.exe\\";
    private final static String ALL = "All.csv";
    private final static String[] FILES = {FOLDER + "One.csv", FOLDER + "Two.csv", FOLDER + "Three.csv"};

    private static List<String> games = new ArrayList<>();
/*    private static Set<String> all = new TreeSet<>();

    public static void main(String[] args) {
        final ReaderFromFile reader = new ReaderFromFile(FOLDER + ALL);
        while (reader.isReady()) {
            final String[] split = reader.readLine().split(",");
            if (games.contains(split[0])) {
                System.out.println(split[0] + "/" + split[1]);
            } else {
                games.add(split[0]);
            }
        }
        games.forEach(System.out::println);
    }*/

    public static void main(String[] args) {
        for (final String path : FILES) {
            final ReaderFromFile reader = new ReaderFromFile(path);
            reader.readLine();

            final String discSize = reader.readLine();
            while (reader.isReady()) {
                games.add(reader.readLine().split(",")[0] + "," + discSize);
            }
        }
        games.sort(String::compareTo);

        final WriterToFile writer = new WriterToFile(FOLDER + ALL);
        writer.write(games);
        writer.close();
    }
}
