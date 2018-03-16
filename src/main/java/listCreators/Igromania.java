package listCreators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fileOperation.WriterToFile;
import htmlConnector.HtmlExecutor;
import parsers.ExcerptFromText;

public class Igromania {
    
    private String startPath = "https://www.igromania.ru/games/";
    private String endPath = "/1/58/0/";
    /**
     * @return List with games and attributes 
     */
    private List<List<String>> createList () {
        List<List<String>> games = Collections.synchronizedList(new ArrayList<>());
 start: for (int i = 2220; i <= 2221; i++) {
            HtmlExecutor exec = new HtmlExecutor();
            String content = exec.contentExecutor(startPath + i + endPath);
            
            List<String> attribute = gameBox(content);
            for (String block : attribute) {
                List<String> game = gameAttribute(block);
                //if (games.contains(game)) break start;
                if (game != null) addition(games, game);
            }
        }
        return games;
    }

    /**
     * @param games
     * @param game
     */
    private synchronized void addition (List<List<String>> games, List<String> game) {
        games.add(game);
    }
    /**
     * @param content - содержимое странички Игромании с базой игр
     * @return List String блоков посвящённых игре 
     */
    private List<String> gameBox(String content) {
        ExcerptFromText excerpt = new ExcerptFromText();
        List<String> gameBlock = excerpt.extractExcerptsFromText(content, "<div class=\"gamebase_box\">", "</a>");
        return gameBlock;
    }
    
    private List<String> gameAttribute(String block) {
        List<String> attributes = new ArrayList<>();
        
        ExcerptFromText excerpt = new ExcerptFromText();
        List<String> attr = excerpt.extractExcerptsFromText(block, "<div class=\"platform\"><span>", "</span></div>");
        if (!attr.get(0).contains("PC")) return null;
        
        attr = excerpt.extractExcerptsFromText(block, "<div class=\"release_name\">", "</div>");
        System.out.println(attr.get(0));
        if (!attr.isEmpty()) attributes.add(attr.get(0).replaceAll("\\&\\#\\d+;", "A"));
        else attributes.add("");
        
        attr = excerpt.extractExcerptsFromText(block, "<div class=\"genre\">", "</div>");
        if (!attr.isEmpty()) attributes.add(attr.get(0));
        else attributes.add("");
        
        attr = excerpt.extractExcerptsFromText(block, "<div class=\"release_data\">", "</div>");
        if (!attr.isEmpty()) attributes.add(attr.get(0));
        else attributes.add("");
        
        attr = excerpt.extractExcerptsFromText(block, "<span class=\"rate_box rate_im\">", "</span>");
        if (!attr.isEmpty()) attributes.add(attr.get(0).replace("<span class=\"rate_s\">", ""));
        else attributes.add("");
        
        attr = excerpt.extractExcerptsFromText(block, "<span class=\"rate_box rate_user\">", "</span>");
        if (!attr.isEmpty()) attributes.add(attr.get(0).replace("<span class=\"rate_s\">", ""));
        else attributes.add("");
        
        return attributes;
    }

    public static void main(String[] args) {
        List<List<String>> games = new Igromania().createList();
        print(games);
    }
    
    private static void print(List<List<String>> games) {
        WriterToFile writer = new WriterToFile("Games.csv");
        for (List<String> list : games) {
            for (String string : list) {
                writer.write(string + ";");
            }
            writer.writeLine();
        }
        writer.close();
    }
}
