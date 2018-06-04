package workWithFiles.fileListings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import dataBase.postgre.PostgreSQLWork;
import workWithFiles.fileIO.ReaderFromFile;

public class GamesIntoDataBase {

    private static final String DISCS_TABLE = "discs";
    private static final String GAMES_TABLE = "my_games";
    private static final String CSV = ".csv";

    public static void main (String[] args) throws IOException {
        filling();
        System.out.println("Done");
    }

    private static void filling () throws IOException {
        List<File> collect = Files.walk(Paths.get("d:\\Games.exe\\"), 1)
                .filter(Files::isRegularFile)
                .filter(filter())
                .map(Path::toFile)
                .collect(Collectors.toList());
        
        PostgreSQLWork postgre = new PostgreSQLWork("Games");
        collect.forEach(f -> {
            String discName = f.getName();
            discName = discName.substring(0, discName.length() - CSV.length());
            
            ReaderFromFile reader = new ReaderFromFile(f);
            String freeSpace = reader.readLine();
            String totalSpace = reader.readLine();
            
            postgre.insertInto(DISCS_TABLE, discName, totalSpace, freeSpace);
            
            while(reader.isReady()) {
                String[] game = reader.readLine().split(GamesCataloging.DELIMETER);
                
                String id = findGameId(postgre, game[0]);
                if (id.isEmpty()) continue;
                
                postgre.insertInto(GAMES_TABLE, id, dateFormatter(game), game[1], findDiscId(postgre, discName));
            }
            
            reader.close();
        });        
        postgre.close();
    }

    private static String findDiscId (PostgreSQLWork postgre, String name) {
        List<List<String>> columns = postgre.findColumnsOfSomeName("discs", "disc_name", name, "id");
        return columns.get(0).get(0);
    }
    
    private static String findGameId (PostgreSQLWork postgre, String name) {
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf > 0) {
            name = name.substring(0, lastIndexOf);
        }
        
        List<List<String>> columns = postgre.findColumnsOfSomeName("games", "title", name, "id");
        try {
            return columns.get(0).get(0);
        } catch (IndexOutOfBoundsException e) {
            System.err.println(name);
            return "";
        }
    }

    private static String dateFormatter (String[] game) {
        if (game.length < 3) return "";
        else {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            Date date = new Date(Long.parseLong(game[2]));
            return format.format(date);
        }
    }

    private static Predicate<? super Path> filter () {
        return f -> {
            String name = f.getFileName().toString();
            int length = name.length();
            
            if (length < CSV.length()) return false;
            
            return CSV.equals(name.substring(length - CSV.length(), length));
        };
    }
}
