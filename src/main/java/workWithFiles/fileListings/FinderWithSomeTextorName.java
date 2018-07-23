package workWithFiles.fileListings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import workWithFiles.fileIO.ReaderFromFile;

public class FinderWithSomeTextorName {

	public void findContent(String path, String extension, String text)  {
		File file = new File(path);
		findContent(file, extension, text);
	}
	
	public void findContent(File file, String extension, String text) {
		if (file.isDirectory()) findContent(file.listFiles(), extension, text);
		else System.err.println("Not a directory");
	}
	
	public void findContent(File[] folder, String extension, String text) {
		for (File f : folder) {
			if (f.isDirectory() && f.listFiles() != null) findContent(f.listFiles(), extension, text);
			else {
				if (f.getName().contains(extension)) {
					ReaderFromFile reader = new ReaderFromFile(f);
					String content = reader.readAll();
					
					if (content.contains(text)) System.out.println(f.getAbsolutePath());
				}
			}
		}
	}
	
	public void findName(String path, String name) {
		File file = new File(path);
		findName(file, name);
	}
	
	public void findName(File file, String name) {
		if (file.isDirectory()) findName(file.listFiles(), name);
		else System.err.println("Not a directory");
	}
	
	public void findName(File[] folder, String name) {
		for (File f : folder) {
			if (f.isDirectory() && f.listFiles() != null) findName(f.listFiles(), name);
			
			else if (f.getName().contains(name)) System.out.println(f.getAbsolutePath());
		}
	}
	
	public static void main(String[] args) throws IOException {
		new FinderWithSomeTextorName().findContent("/opt/workspace/pvengine/", ".java", "publish_description.properties");
	}
}
