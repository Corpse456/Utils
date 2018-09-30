/**
 * добывает текст из HTML
 */

package parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import htmlConnector.HtmlExecutor;
import workWithFiles.fileIO.ReaderFromFile;

public class ExcerptFromText {

    /**
     * @param content
     * @return
     */
    public String titleFromHtmlPage (String content) {
        List<String> list = extractExcerptsFromText(content, "<title>", "</title>");
        String title = !list.isEmpty() ? list.get(0) : "";
        return title;
    }

    /**
     * @param path
     * @return
     */
    public String titleFromLink (String path) {
        List<String> list = extractExcerptsFromURL(path, "<title>", "</title>");
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
    public List<String> extractExcerptsFromText (String content, String from, String to) {
        return extract(content, from, to);
    }

    /**
     * @param file
     * @param from
     * @param to
     * @return
     */
    public List<String> extractExcerptsFromFile (File file, String from, String to) {
        ReaderFromFile reader = new ReaderFromFile(file);
        String content = reader.readAll();

        return extract(content, from, to);
    }

    /**
     * @param path
     * @param from
     * @param to
     * @return
     */
    public List<String> extractExcerptsFromFile (String path, String from, String to) {
        return extractExcerptsFromFile(new File(path), from, to);
    }

    /**
     * @param path
     * @param from
     * @param to
     * @return
     */
    public List<String> extractExcerptsFromURL (String path, String from, String to) {
        HtmlExecutor exec = new HtmlExecutor();
        String content = exec.contentExecutor(path);

        return extract(content, from, to);
    }

    private static List<String> extract (String content, String from, String to) {
        List<String> matches = new ArrayList<>();

        String pattern = "(?<=" + from + ")([\\s\\S]*?)(?=" + to + ")";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);

        while (m.find()) {
            matches.add(content.substring(m.start(), m.end()));
        }
        return matches;
    }
}
