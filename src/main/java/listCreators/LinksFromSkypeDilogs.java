package listCreators;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import fileOperation.WriterToFile;
import parsers.ExcerptFromText;

/**
 * @author Alxander Neznaev
 *
 */
public class LinksFromSkypeDilogs {
    
    private Map<String, String> result = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
    private int count = 0;

    private Map<String, String> extractLinksAndTitle(String path, String from, String startString, String to, String endString) {
        ExcerptFromText excerpt = new ExcerptFromText();
        List<String> links = excerpt.extractExcerptsFromFile(path, from, startString, to, endString);

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
        System.out.println(result.size());
        return result;
    }

    public static void main(String[] args) {
        LinksFromSkypeDilogs links = new LinksFromSkypeDilogs();
        Map<String, String> linkss = links.extractLinksAndTitle("C:/SkypeChatHistory.csv", ">http", ">", "<", "<");

        WriterToFile writer = new WriterToFile("C:/result.csv");
        writer.write(linkss, "~");
        writer.close();
        System.out.println("Done");
    }
}
