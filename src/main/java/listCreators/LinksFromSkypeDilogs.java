package listCreators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import parsers.ExcerptFromText;
import workWithFiles.fileIO.ReaderFromFile;
import workWithFiles.fileIO.WriterToFile;

/**
 * @author Alxander Neznaev
 *
 */
public class LinksFromSkypeDilogs {
    
    private Map<String, String> result = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
    private int count = 0;

    public Map<String, String> extractLinks(String path, String from, String startString, String to, String endString) {
        ExcerptFromText excerpt = new ExcerptFromText();
        List<String> links = excerpt.extractExcerptsFromFile(path, from, startString, to, endString);

        return extractTitle(links);
    }

    public Map<String, String> extractTitle(List<String> links) {
        for (String link : links) {
            if (link.contains("login.skype.com")) continue;
            queue.add(1);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Document doc = null;
                    try { doc = Jsoup.connect(link).get(); } catch (Exception e) { }
                    
                    String title = doc != null ? doc.title() : "";
                    
                    if (title.isEmpty()) { try { title = new ExcerptFromText().TitleFromLink(link); } catch (Exception e) {} }
                    
                    if (title.isEmpty()) title = "NoTitle" + count++;
                    result.put(title, link);
                    
                    queue.remove();
                    System.out.println(link);
                    System.out.println(title);
                }
            }).start();
        }
        while (!queue.isEmpty());
        
        return result;
    }

    public static void main(String[] args) {
        List <String> links = new ArrayList<>();
        ReaderFromFile reader = new ReaderFromFile("C:/links.txt");
        
        while (reader.isReady()) {
            String[] split = reader.readLine().split("~");
            links.add(split[1]);
        }
        
        WriterToFile writer = new WriterToFile("C:result.csv");
        Map<String, String> titleLinks = new LinksFromSkypeDilogs().extractTitle(links);
        writer.write(titleLinks , "~");
        writer.close();
    }
}
