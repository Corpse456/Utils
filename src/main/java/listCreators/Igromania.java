package listCreators;

import java.util.ArrayList;
import java.util.List;

import htmlConnector.HtmlExecutor;
import parsers.ExcerptFromText;

public class Igromania {
    
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
        if (!attr.isEmpty()) attributes.add(attr.get(0));
        
        attr = excerpt.extractExcerptsFromText(block, "<div class=\"genre\">", "</div>");
        if (!attr.isEmpty()) attributes.add(attr.get(0));
        
        attr = excerpt.extractExcerptsFromText(block, "<div class=\"release_data\">", "</div>");
        if (!attr.isEmpty()) attributes.add(attr.get(0));
        
        attr = excerpt.extractExcerptsFromText(block, "<span class=\"rate_box rate_im\">", "</span>");
        if (!attr.isEmpty()) attributes.add(attr.get(0));
        
        attr = excerpt.extractExcerptsFromText(block, "<span class=\"rate_box rate_user\">", "</span>");
        if (!attr.isEmpty()) attributes.add(attr.get(0));
        
        return attributes;
    }

    public static void main(String[] args) {
        String PATH = "https://www.igromania.ru/games/";
        List<List<String>> games = new ArrayList<>();
 start: for (int i = 1; i < 10; i++) {
            HtmlExecutor exec = new HtmlExecutor();
            String content = exec.contentExecutor(PATH + i + "/1/58/0");
            
            Igromania igromania = new Igromania();
            List<String> attribute = igromania.gameBox(content);
            for (String block : attribute) {
                List<String> game = igromania.gameAttribute(block);
                if (games.contains(game)) break start;
                if (game != null) games.add(game);
            }
        }
        print(games);
    }

    private static void print(List<List<String>> games) {
        for (List<String> list : games) {
            for (String string : list) {
                System.out.print(string + " | ");
            }
            System.out.println();
        }
    }
}
