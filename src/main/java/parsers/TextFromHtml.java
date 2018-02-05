/**
 * добывает текст из HTML
 */

package parsers;

import java.util.ArrayList;

import fileOperation.ReaderFromFile;
import fileOperation.WriterToFile;

public class TextFromHtml {

    //private static final String FROM = "<p>";

    public static void main (String[] args) {
	ReaderFromFile reader = new ReaderFromFile(TextFromHtml.class.getClassLoader().getResource("6.txt"));
	String stroka = "";
	ArrayList<String> list = new ArrayList<String>();

	do {
	    stroka = reader.readLine();
	} while (!stroka.contains("<h2>Name Ц Nickname</h2>"));

	while (reader.isReady()) {
	    stroka = reader.readLine();
	    if (!stroka.isEmpty()) {
		for (int i = 0; i < stroka.length() - 1; i++) {
		    if (stroka.charAt(i) == '>' && Character.isAlphabetic(stroka.charAt(i + 1))) {
			//int start = stroka.indexOf(FROM) + FROM.length();
			int start = i + 1;
			int end = start;
			while (Character.isAlphabetic(stroka.charAt(++end)));
			list.add(stroka.substring(start, end));
		    }
		}
	    }
	}
	reader.close();
	WriterToFile write = new WriterToFile("C:/EngWoman.txt");
	for (int i = 0; i < list.size(); i++) {
	    write.writeLine(list.get(i));
	}
	write.close();
	System.out.println(list);
    }
}
