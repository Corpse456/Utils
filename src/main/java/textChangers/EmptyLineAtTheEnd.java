package textChangers;

import java.io.File;
import java.util.List;

import fileOperation.ReaderFromFile;
import fileOperation.WriterToFile;

/**
 * Add empty line for all of java files in directory and subirectory
 * 
 * @author Alexander Neznaev
 *
 */
public class EmptyLineAtTheEnd {

    private String path;

    public EmptyLineAtTheEnd(String path) {
        this.path = path;
    }

    public void doIt() {
        File f = new File(path);
        insideDirectory(f);
    }

    /**
     * @param f - current file
     */
    private void insideDirectory(File f) {
        File[] listFiles = f.listFiles();
        for (final File file : listFiles) {
            if (file.isDirectory()) {
                insideDirectory(file);
            }
            if (file.isFile()) {
                String name = file.getName();
                int len = name.length();
                if (len > 5 && ".java".equals(name.substring(len - 5, len))) {
                    new Thread(new Runnable() {
                        public void run() {
                            addingLineIfneeded(file);
                        }
                    }).start();
                }
            }
        }
    }

    /**
     * @param file - current .java file
     */
    private void addingLineIfneeded(File file) {
        ReaderFromFile reader = new ReaderFromFile(file);
        List<String> fileLines = reader.readAllAsLIst();
        reader.close();

        if (fileLines.isEmpty()) return;

        if (!fileLines.get(fileLines.size() - 1).isEmpty()) {
            fileLines.add("");

            WriterToFile writer = new WriterToFile(file);
            writer.write(fileLines);
            writer.close();
        }
    }

    public static void main(String[] args) {
        EmptyLineAtTheEnd empty = new EmptyLineAtTheEnd("d:\\Neznaev\\EclipseWS\\");
        empty.doIt();
        System.out.println("Done");
    }
}
