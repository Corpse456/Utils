/**
 * добывает текст из HTML
 */

package parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fileOperation.ReaderFromFile;
import htmlConnector.HtmlExecutor;

public class ExcerptFromText {

    /**
     * @param content
     * @return
     */
    public String TitleFromHtmlPage (String content) {
        List <String> list = extractExcerptsFromText(content, "<title>", "<title>", "</title>", "</title>");
        String title = !list.isEmpty() ? list.get(0) : "";
        return title;
    }
    
    /**
     * @param path
     * @return
     */
    public String TitleFromLink (String path) {
        List <String> list = extractExcerptsFromURL(path, "<title>", "<title>", "</title>", "</title>");
        String title = !list.isEmpty() ? list.get(0) : "";
        return title;
    }
    
    /**
     * @param content
     * @param from
     * @param startString
     * @param to
     * @param endString
     * @return
     */
    public List<String> extractExcerptsFromText (String content, String from, String startString, String to, String endString) { 
        return extract(content, from, startString, to, endString);
    }
    
    /**
     * @param content
     * @param from
     * @param to
     * @return
     */
    public List<String> extractExcerptsFromText(String content, String from, String to) {
        return extract(content, from, from, to, to);
    }
    
    /**
     * @param file
     * @param from
     * @param startString
     * @param to
     * @param endString
     * @return
     */
    public List<String> extractExcerptsFromFile (File file, String from, String startString, String to, String endString) {
        ReaderFromFile reader = new ReaderFromFile(file);
        String content = reader.readAll();
        
        return extract(content, from, startString, to, endString);
    }
    
    /**
     * @param path
     * @param from
     * @param startString
     * @param to
     * @param endString
     * @return
     */
    public List<String> extractExcerptsFromFile(String path, String from, String startString, String to, String endString) {
        return extractExcerptsFromFile (new File(path), from, startString, to, endString);
    }
    
    /**
     * @param path
     * @param from
     * @param startString
     * @param to
     * @param endString
     * @return
     */
    public List<String> extractExcerptsFromFile(String path, String from, String to) {
        return extractExcerptsFromFile (new File(path), from, from, to, to);
    }
    
    /**
     * @param path
     * @param from
     * @param to
     * @return
     */
    public List<String> extractExcerptsFromURL (String path, String from, String to) {        
        return extractExcerptsFromURL(path, from, from, to, to);
    }
    
    /**
     * @param path
     * @param from
     * @param startString
     * @param to
     * @param endString
     * @return
     */
    public List<String> extractExcerptsFromURL (String path, String from, String startString, String to, String endString) {
        HtmlExecutor exec = new HtmlExecutor();
        String content = exec.contentExecutor(path);
        
        return extract(content, from, startString, to, endString);
    }

    private List<String> extract(String content, String from, String startString, String to, String endString) {
        int start = startString.length(); 
        int end = endString.length(); 
        List<String> matches = new ArrayList<>();
        
        String pattern = from + "(.|\n)*?" + to;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        
        while (m.find()) {
            matches.add(content.substring(m.start() + start, m.end() - end));
        }
        return matches;
    }
}
