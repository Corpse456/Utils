package listCreators;

import htmlConnector.HtmlExecutor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Locale.ENGLISH;

public class Attendance {

    private static final DateTimeFormatter DATE_FORMATTER = ofPattern("yyyy-MM-dd", ENGLISH);
    private final static String PATH = "https://spirit.generation-p.com/attendance";

    private void execute() {
        HtmlExecutor exec = new HtmlExecutor();
        final HashMap<String, String> map = new HashMap<>();
        map.put("calendar", LocalDate.now().format(DATE_FORMATTER));
        map.put("user", "Neznaev");
        String content = exec.contentPostExecutor(PATH, map);
        System.out.println("content = " + content);
    }

    public static void main(String[] args) {
        new Attendance().execute();
    }
}
