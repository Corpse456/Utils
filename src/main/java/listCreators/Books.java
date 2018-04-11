package listCreators;

import java.util.List;

import constant.Delimiters;
import fileOperation.WriterToFile;
import parsers.ExcerptFromText;

public class Books {

    public static void main(String[] args) {
        ExcerptFromText excerpt = new ExcerptFromText();
        StringBuilder content = new StringBuilder();
        
        for (int i = 1; i <= 10; i++) {
            List<String> tegs = excerpt.extractExcerptsFromURL("http://www.100bestbooks.ru/index.php?page=" + i, "<TR style='text-align: center'","</b></TD></TR>");
            for (String string : tegs) {
                String oneBook = executeOneBook(string, excerpt);
                content.append(oneBook + "\n");
            }
        }
        WriterToFile writer = new WriterToFile("src\\main\\resources\\books.txt");
        writer.write(content.toString());
        writer.close();
    }

    private static String executeOneBook(String line, ExcerptFromText excerpt) {
        StringBuilder book = new StringBuilder();

        List<String> author = excerpt.extractExcerptsFromText(line, "<span itemprop=\"name\">", "</span>");
        book.append(author.get(0) + Delimiters.SEMICOLON);
        
        List<String> title = excerpt.extractExcerptsFromText(line, "<span itemprop='name'>", "</span>");
        book.append(title.get(0) + Delimiters.SEMICOLON);
        
        List<String> years = excerpt.extractExcerptsFromText(line, "<TD class='vline-year'>", "</TD>");
        String year = years.get(0);
        if (year)
        book.append(year);
        
        return book.toString();
    }
}
