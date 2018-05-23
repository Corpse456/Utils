package textChangers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ListFiles {
    
    public static void main (String[] args) throws IOException {
        long time = System.nanoTime();
        List<File> collect = Files.walk(Paths.get("z:\\"))
        .filter(Files::isRegularFile)
        .map(Path::toFile)
        .collect(Collectors.toList());
        /
        System.out.println((System.nanoTime() - time) / 1000000000.0 + collect.size());
        //collect.forEach(e -> System.out.println(e));
    }
}
