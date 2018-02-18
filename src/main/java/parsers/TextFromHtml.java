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
	String text = "Not find";
	System.out.println(htmlText.contains(from));
	System.out.println(htmlText.contains(to));
	String pattern = from + ".+" + to;
	Pattern p = Pattern.compile(pattern);
	Matcher m = p.matcher(htmlText);
	if (m.find()) {
	    System.out.println("have");
	    text = htmlText.substring(m.start(), m.end());
	    return text;
	}
	return text;
    }
    
    public static void jopa() {
    }
}
