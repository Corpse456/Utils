package listCreators;

import org.apache.commons.io.FileUtils;
import parsers.ExcerptFromText;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.String.valueOf;
import static java.nio.charset.Charset.defaultCharset;

public class LetterCodes {

    public static void main(String[] args) throws IOException {
        final Set<String> excerpt = new TreeSet<>();

        final String content = FileUtils.readFileToString(new File("/opt/temp.txt"), valueOf(defaultCharset()));
        final List<String> strings = ExcerptFromText.extract(content, "<td>", " / ", "A-Z", "{2}");
        final List<String> strings2 = new ExcerptFromText().extractExcerptsFromText(content, "\">", "</a></td>");
        for (int i = 0; i < strings.size(); i++) {
            if (Dict.LOCATIONS.contains(strings2.get(i))) {
                final String template = Dict.template.replace("NAME_CODE", strings2.get(i))
                    .replace("VALUE_CODE", strings.get(i));
                System.out.println(template);
            } else {
                excerpt.add(strings2.get(i));
            }
        }
        System.err.println(excerpt);
    }
}
