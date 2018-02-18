package fileListings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fileOperation.WriterToFile;

public class Catalogs {
    
    private static final String DiscLetter = "g:";
    private static List<String> folders = new ArrayList<>();
    
    public static void main(String[] args) {
	WriterToFile writer = new WriterToFile("d://Games.exe//" + "One.csv");
	File disc = new File(DiscLetter);
	
	writer.write(discAtributes(disc));
	for (File f : disc.listFiles()) {
	    if (isGameFolder(f)) {
		folders.add(f.getName());
		writer.writeLine(f.getName() + ";" + calculateSize(f));
	    }
	}
	writer.close();
	System.out.println("Done");
    }

    private static String discAtributes(File disc) {
	String string = disc.getPath() + "\r\n";
	string += disc.getFreeSpace() + "\r\n";
	string += disc.getTotalSpace() + "\r\n";
	return string;
    }

    private static long calculateSize(File f) {
	long size = 0;
	if (f.listFiles() != null) {
	    for(File f1 : f.listFiles()) {
		if (f1.isFile()) size += f1.length();
		if (f1.isDirectory()) size += calculateSize(f1);
	    }
	}
	return size;
    }

    private static boolean isGameFolder(File f) {
	if (!f.isDirectory()) return false;
	if ("$RECYCLE.BIN".equals(f.getName()) || "System Volume Information".equals(f.getName())) return false;
	return true;
    }
}
