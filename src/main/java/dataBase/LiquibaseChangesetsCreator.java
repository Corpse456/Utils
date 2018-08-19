package dataBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import workWithFiles.fileIO.ReaderFromFile;

public class LiquibaseChangesetsCreator {

	public static void main(String[] args) {
		new LiquibaseChangesetsCreator().findEnities("d:\\Me\\JAVA\\Study\\Kinopoisk\\");
	}

	private void findEnities(String destination) {
		List<File> collect = walk(destination).filter(Files::isRegularFile)
			.filter(f -> f.toFile().getName().endsWith(".java"))
			.filter(f -> isEntity(f))
			.map(Path::toFile)
			.collect(Collectors.toList());
		collect.forEach(e -> createTable(e));
	}

	private void createTable(File f) {
		Map<String, Set<String>> fields = new HashMap<>();
		ReaderFromFile reader = new ReaderFromFile(f);
		
		while(reader.isReady()) {
			String currentLine = reader.readLine();
			if (currentLine.startsWith("@")) {
				fieldAdd(fields, reader, currentLine);
			}
		}
	}

	private void fieldAdd(Map<String, Set<String>> fields, ReaderFromFile reader, String currentLine) {
		Set<String> annotations = new TreeSet<>();
		do {
			annotations.add(currentLine.split("(")[0].replace("@", ""));
			currentLine = reader.readLine();
		} while (currentLine.contains("@"));
		
	}

	private Stream<Path> walk(String destination) {
		try {
			return Files.walk(Paths.get(destination));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean isEntity(Path f) {
		ReaderFromFile reader = new ReaderFromFile(f.toFile());
		String content = reader.readAll();
		if (content.contains("@Entity")) return true;
		else return false;
	}
}
