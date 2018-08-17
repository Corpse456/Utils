package dataBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
		ReaderFromFile reader = new ReaderFromFile(f);
		while(reader.isReady()) {
			String currentLine = reader.readLine();
		}
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
