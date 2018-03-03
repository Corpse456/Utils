/**
 * добывает текст из HTML
 */

package parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFromHtml {
    /**
     * @param html - содержимое html страницы
     * @param from - левая граница текста
     * @param to - права граница текста
     * @return
     */
    public String extractor(String htmlText, String from, String to) {
        String pattern = "(" + from + ")(.+?)(" + to + ")";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(htmlText);
        if (m.find()) {
            return m.group(2);
        }
        return null;
    }

    public static void main(String[] args) {
        TextFromHtml tx = new TextFromHtml();
        String in = tx.extractor("регулярные выражения это круто регулярные выражения это круто регулярные выражения это круто якороль Я СЕГОДНЯ ЕЛ БАНАНЫ якороль регулярные выражения это круто","(якороль)", "(якороль)");
        System.out.println(in);
    }
}
