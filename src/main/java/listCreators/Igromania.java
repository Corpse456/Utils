package listCreators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dataBase.postgre.PostgreSQL;
import htmlConnector.HtmlExecutor;
import parsers.ExcerptFromText;
import workWithFiles.fileIO.GZIPCreator;
import workWithFiles.fileIO.WriterToFile;

public class Igromania {

    private String startPath = "https://www.igromania.ru/games/";
    private String endPath = "/1/58/0/";
    private int count = 0;
    private List<List<String>> games = Collections.synchronizedList(new ArrayList<>());

    /**
     * @return List with games and attributes
     */
    private List<List<String>> createList() {
        for (int i = 1; true; i++) {
            nextPage(i);
            if (count > 100) break;
        }
        return games;
    }

    private void nextPage(int i) {
        HtmlExecutor exec = new HtmlExecutor();
        String content = exec.contentExecutor(startPath + i + endPath);
        
        List<String> attribute = gameBox(content);
        for (String block : attribute) {
            List<String> game = gameAttribute(block);
            if (game != null) addition(game);
        }
    }

    /**
     * @param games
     * @param game
     */
    private synchronized void addition(List<String> game) {
        if (!games.contains(game)) games.add(game);
        else count++;
    }

    /**
     * @param content - the contents of the Igromania page with a base of games
     * @return List String blocks of dedicated games
     */
    private List<String> gameBox(String content) {
        ExcerptFromText excerpt = new ExcerptFromText();
        List<String> gameBlock = excerpt.extractExcerptsFromText(content, "<div class=\"gamebase_box\">", "</a>");
        return gameBlock;
    }

    private List<String> gameAttribute(String block) {
        List<String> attributes = new ArrayList<>();

        ExcerptFromText excerpt = new ExcerptFromText();
        
        List<String> attr;
        
        /*attr = excerpt.extractExcerptsFromText(block, "<div class=\"platform\"><span>", "</span></div>");
        if (!attr.isEmpty()) if (!attr.get(0).contains("PC")) return null;*/

        attr = excerpt.extractExcerptsFromText(block, "<div class=\"release_name\">", "</div>");
        
        //the name of the game
        if (!attr.isEmpty()) attributes.add(attr.get(0).replaceAll("\\&\\#\\d+;", "A").replaceAll(";", " "));
        else attributes.add("");

        //genre
        attr = excerpt.extractExcerptsFromText(block, "<div class=\"genre\">", "</div>");
        if (!attr.isEmpty()) attributes.add(attr.get(0));
        else attributes.add("");

        //release date
        attr = excerpt.extractExcerptsFromText(block, "<div class=\"release_data\">", "</div>");
        if (!attr.isEmpty()) attributes.add(dateFormatter(attr.get(0)));
        else attributes.add("");

        //rating by igromania
        attr = excerpt.extractExcerptsFromText(block, "<span class=\"rate_box rate_im\">", "</span>");
        if (!attr.isEmpty()) attributes.add(attr.get(0).replace("<span class=\"rate_s\">", "").replace(",", "."));
        else attributes.add("");
        
        //rating by users
        attr = excerpt.extractExcerptsFromText(block, "<span class=\"rate_box rate_user\">", "</span>");
        if (!attr.isEmpty()) attributes.add(attr.get(0).replace("<span class=\"rate_s\">", "").replace(",", "."));
        else attributes.add("");
        
        //link
        attr = excerpt.extractExcerptsFromText(block, "<a href=\"", "\"");
        if (!attr.isEmpty()) attributes.add("https://www.igromania.ru/" + attr.get(0));
        else attributes.add("");

        return attributes;
    }

    public String dateFormatter (String badDate) {
        if ("Дата выхода неизвестна".equals(badDate)) return "";
        
        SimpleDateFormat from = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat to = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date docDate = null;
        
        try {
            docDate = from.parse(badDate);
            return to.format(docDate);
        } catch (ParseException e) {
            try {
                SimpleDateFormat from2 = new SimpleDateFormat("MMMM yyyy");
                docDate = from2.parse(badDate);
                return to.format(docDate);
            } catch (ParseException e2) {
                try {
                    SimpleDateFormat from3 = new SimpleDateFormat("yyyy");
                    badDate = badDate.replaceAll("[^\\d]", "");
                    docDate = from3.parse(badDate);
                    return to.format(docDate);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                    return to.format(new Date());
                }
            }
        }
    }
    
    public static void main(String[] args) {
        String path = "Games.csv";
        long time = System.nanoTime();
        List<List<String>> games = new Igromania().createList();
        System.out.println((System.nanoTime() - time) / 1000000000.0 / 60 / 60);
        write(games, path);
        
        PostgreSQL.main(path);
        
        GZIPCreator gzip = new GZIPCreator();
        gzip.gzipIt(path);
    }

    private static void write(List<List<String>> games, String path) {
        WriterToFile writer = new WriterToFile(path);
        for (List<String> list : games) {
            for (String string : list) {
                writer.write(string + ";");
            }
            writer.writeLine();
        }
        writer.close();
    }
}
