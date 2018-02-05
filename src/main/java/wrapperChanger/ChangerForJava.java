package wrapperChanger;

import fileOperation.FileWorks;

public class ChangerForJava {

    public static void main(String[] args) {
	if (args.length != 1) {
	    System.err.println("Please input filepath");
	    return;
	}
	
	String path = ChangerForJava.class.getResource(args[0]).toExternalForm().replaceAll("file:/", "");
	FileWorks file = new FileWorks(path);
	file.createFile(path.replace(".java", "Changed.java"));
	
	while(file.isReady()) {
	    String currentLine = file.readLine();
	    if (currentLine.contains("//")) {
		continue;
	    }
	    System.out.println(currentLine);
	    file.writeLine(currentLine);
	}
	file.close();
    }
}
