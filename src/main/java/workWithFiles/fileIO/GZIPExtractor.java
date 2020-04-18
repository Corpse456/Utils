package workWithFiles.fileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;

import listCreators.Igromania;

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

    public String fromGzipToMemory(URL fileName) {
        return fromGzipToMemory(fileName.getPath());
    }
    
    public String fromGzipToMemory(String fileName) {
        StringBuilder content = new StringBuilder();
        List<String> contentList = fromGzipToMemoryAsList(fileName);

        for (String string : contentList) {
            content.append(string);
        }
        return content.toString();
    }

    public List<String> fromGzipToMemoryAsList(URL fileName) {
        return fromGzipToMemoryAsList(fileName.getPath());
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

    public static void main2(String[] args) {
        URL url = GZIPExtractor.class.getClassLoader().getResource("Games.csv.gz");
        List<String> list = new GZIPExtractor().fromGzipToMemoryAsList(url.getPath());

        Igromania igromania = new Igromania();
        WriterToFile writer = new WriterToFile("C:/games.csv");
        for (String string : list) {
            String[] split = string.split(";");
            split[2] = igromania.dateFormatter(split[2]);
            split[3] = split[3].replaceAll(",", ".");
            split[4] = split[4].replaceAll(",", ".");
            for (int i = 0; i < split.length; i++) {
                writer.write(split[i]);
                if (i != split.length - 1) writer.write(";");
            }
            writer.writeLine();
        }
        writer.close();
    }
}
