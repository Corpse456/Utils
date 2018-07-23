package workWithFiles.fileListings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import workWithFiles.fileIO.ReaderFromFile;

public class FinderWithSomeText {

	private List<File> files = new ArrayList<>();
	private String extension;

	private void find(String path, String extension, String text) throws IOException {
		File file = new File(path);
		this.extension = extension;
		if (file.isDirectory()) valk(file.listFiles());
		
		for (File f : files) {
			ReaderFromFile reader = new ReaderFromFile(f);
			String content = reader.readAll();
			if (content.contains(text)) System.out.println(f.getAbsolutePath());
		}
	}

	private void valk(File[] catalog) {
		for (final File file : catalog) {
            if (file.isDirectory() && file.listFiles() != null) {
                valk(file.listFiles());
            } else if (file.getName().contains(extension)) files.add(file);
        }
	}
	
	public static void main(String[] args) throws IOException {
		new FinderWithSomeText().find("/opt/workspace/pvengine/", ".java", "Go to current description");
	}
}
