package dataBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import workWithFiles.fileIO.ReaderFromFile;

public class LiquibaseChangesetsCreator {

//    private static List<String> DATA_TYPES = Arrays.asList("String", "int", "Integer");
    private static List<String> ACCESS_TYPES = Arrays.asList("protected", "public", "private");
    private List<Entity> fields = new ArrayList<>();

    public static void main (String[] args) {
        new LiquibaseChangesetsCreator().findEnities("d:\\Me\\JAVA\\Study\\Kinopoisk\\");
    }

    private void findEnities (String destination) {
        List<File> collect = walk(destination)
                .filter(Files::isRegularFile)
                .filter(f -> f.toFile().getName().endsWith(".java"))
                .filter(f -> isEntity(f)).map(Path::toFile)
                .collect(Collectors.toList());
        collect.forEach(e -> findEntities(e));
        fields.forEach(a -> System.out.println(a));
    }

    private void findEntities (File f) {
        ReaderFromFile reader = new ReaderFromFile(f);
        while(!reader.readLine().contains("class " + f.getName().substring(0, f.getName().length() - 5)));
        while (reader.isReady()) {
            String currentLine = reader.readLine();
            if (currentLine.contains("@")) {
                fieldAdd(reader, currentLine);
            }
        }
    }

    private void fieldAdd (ReaderFromFile reader, String currentLine) {
        Entity currentEntity = new Entity();
        do {
            String replace = currentLine.replaceAll(" ", "").replace("@", "");
            currentEntity.addAnnotation(replace);
            currentLine = reader.readLine();
        } while (currentLine.contains("@"));
        String[] fieldParametr = currentLine.split(" ");
        for (int i = 0; i < fieldParametr.length; i++) {
            if (ACCESS_TYPES.contains(fieldParametr[i])) {
                currentEntity.setType(fieldParametr[i + 1]);
                currentEntity.setName(fieldParametr[i + 2].replace(";", ""));
                fields.add(currentEntity);
                return;
            }
        }
    }

    private Stream<Path> walk (String destination) {
        try {
            return Files.walk(Paths.get(destination));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isEntity (Path f) {
        ReaderFromFile reader = new ReaderFromFile(f.toFile());
        String content = reader.readAll();
        if (content.contains("@Entity")) return true;
        else return false;
    }

    public class Entity {
        private String name;
        private Set<String> annotationList;
        private String type;

        public Entity () {
            annotationList = new TreeSet<>();
        }

        public String getName () {
            return name;
        }

        public void setName (String name) {
            this.name = name;
        }

        public Set<String> getAnnotationList () {
            return annotationList;
        }

        public void setAnnotationList (Set<String> annotationList) {
            this.annotationList = annotationList;
        }
        
        public void addAnnotation (String annotation) {
            annotationList.add(annotation);
        }
        
        public String getType () {
            return type;
        }

        public void setType (String type) {
            this.type = type;
        }

        @Override
        public String toString () {
            return type + " " + name + " " + annotationList;
        }
    }
}
