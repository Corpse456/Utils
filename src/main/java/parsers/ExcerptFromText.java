/**
 * добывает текст из HTML
 */

package parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fileOperation.ReaderFromFile;

public class ExcerptFromText {
    
    private final String path;
    private final String from;
    private final int start;
    private final String to;
    private final int end;

    public ExcerptFromText (String path, String from, String start, String to, String end) {
        this.path = path;
        this.from = from;
        this.start = start.length();
        this.to = to;
        this.end = end.length();
    }

    public List<String> extractExcerpts () {
        ReaderFromFile reader = new ReaderFromFile(path);
        String content = reader.readAll();
        
        List<String> matches = new ArrayList<>();
        
        String pattern = from + "[^(" + to + ")]+" + to;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        
        while (m.find()) {
            matches.add(content.substring(m.start() + start, m.end() - end));
        }
        return matches;
    }
}
