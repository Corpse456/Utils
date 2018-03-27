package fileOperation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class GZIPExtractor {

    private final int bufferSize = 1024;

    public String unGZIP(String fileName, String newFolder) {
        String newPath = newFolder + new File(fileName).getName();
        return unGZIP(newPath);
    }

    public String unGZIP(String fileName) {
        byte[] buffer = new byte[bufferSize];
        String newName = fileName.substring(0, fileName.lastIndexOf("."));

        try (GZIPInputStream gzipInput = new GZIPInputStream(new FileInputStream(fileName));
                FileOutputStream output = new FileOutputStream(newName)) {
            int length = 0;
            while ((length = gzipInput.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return newName;
    }

    public String fromGzipToMemory(String fileName) {
        StringBuilder content = new StringBuilder();
        List<String> contentList = fromGzipToMemoryAsList(fileName);

        for (String string : contentList) {
            content.append(string);
        }
        return content.toString();
    }

    public List<String> fromGzipToMemoryAsList(String fileName) {
        String filePath = unGZIP(fileName);

        ReaderFromFile reader = new ReaderFromFile(filePath);
        List<String> content = reader.readAllAsLIst();
        reader.close();

        File file = new File(filePath);
        file.delete();
        return content;
    }

    public static void main(String[] args) {
        URL url = GZIPExtractor.class.getClassLoader().getResource("Games.csv.gz");
        List<String> list = new GZIPExtractor().fromGzipToMemoryAsList(url.getPath());

        int max = 0;
        String name = "";
        for (String string : list) {
            String[] split = string.split(";");
            if (split[1].length() > max) {
                max = split[1].length();
                name = split[1];
            }
            if (split[2].isEmpty()) System.out.println(split[0]);
        }
        System.out.println(max + "\n" + name);
    }
}
