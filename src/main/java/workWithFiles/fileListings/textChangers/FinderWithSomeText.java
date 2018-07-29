package workWithFiles.fileListings.textChangers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import workWithFiles.fileIO.ReaderFromFile;
import workWithFiles.fileIO.WriterToFile;

public class FinderWithSomeText {

    private List<File> files;
    private String extension;
    private String path;
    private String newString;

    public FinderWithSomeText (String path, String extension) {
        this.extension = extension;
        this.path = path;
    }

    public void find (String text) {
        find(text, false);
    }

    public void find (String text, boolean replace) {
        File file = new File(path);
        files = new ArrayList<>();
        if (file.isDirectory()) valk(file.listFiles());

        for (File f : files) {
            ReaderFromFile reader = new ReaderFromFile(f);
            String content = reader.readAll();
            reader.close();
            if (content.contains(text)) {
                if (!replace) System.out.println(f.getAbsolutePath());
                else {
                    content = content.replaceAll(text, newString);
                    WriterToFile writer = new WriterToFile(f);
                    writer.write(content);
                    writer.close();
                    System.out.println(f.getAbsolutePath());
                }
            }
        }
    }

    public void replace (String oldString, String newString) {
        this.newString = newString;
        find(oldString, true);
    }

    private void valk (File[] catalog) {
        for (final File file : catalog) {
            if (file.isDirectory() && file.listFiles() != null) valk(file.listFiles());
            else if (file.getName().contains(extension)) files.add(file);
        }
    }

    public static void main (String[] args) throws IOException {
        // new FinderWithSomeText("/opt/workspace/pvengine/pvengine_unit/",
        // ".java").find("Master Property Category");
        new FinderWithSomeText("d:\\Me\\JAVA\\Homeworks\\", ".java").replace("import home",
                "import com.itStep.home");
    }
}
