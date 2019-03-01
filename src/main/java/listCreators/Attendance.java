package listCreators;

import htmlConnector.HtmlExecutor;
import parsers.ExcerptFromText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Locale.ENGLISH;

public class Attendance {

    private static final DateTimeFormatter DATE_FORMATTER = ofPattern("yyyy-MM-dd", ENGLISH);
    private final static String PATH = "https://spirit.generation-p.com/attendance";
    private static final String LAST_NAME = "lastName";
    private static final String BEFORE = LAST_NAME + "</td><td></td><td></td><td>";
    private static final String FLOUR = "flour";
    private static final String AFTER = "<\\/td><td>" + FLOUR + "<\\/td>";
    private static final String TIME_REGEXP = "\\d:";

    private void execute(final String lastName, final String flour) {
        LocalDate startOfMonth = LocalDate.now().minusDays(1).withDayOfMonth(1);
        final int currentMonth = startOfMonth.getMonthValue();

        while (currentMonth == startOfMonth.getMonthValue()) {
            final String time = getForDay(lastName, flour, startOfMonth);
            System.out.println("content = " + time);
            startOfMonth = startOfMonth.plusDays(1);
        }
    }

    private String getForDay(final String lastName, final String flour, final LocalDate date) {
        HtmlExecutor exec = new HtmlExecutor();
        final HashMap<String, String> map = new HashMap<>();
        map.put("calendar", date.format(DATE_FORMATTER));
        map.put("user", lastName);
        String content = exec.contentPostExecutor(PATH, map);

        final String afterTotal = content.split("TOTAL")[1];
        final ExcerptFromText excerpt = new ExcerptFromText();

        final List<String> excerpts = excerpt.extractExcerptsFromText(afterTotal, BEFORE.replace(LAST_NAME, lastName),
                                                                      AFTER.replace(FLOUR, flour), TIME_REGEXP);
        if (excerpts.isEmpty()) {
            return null;
        }
        return excerpts.get(0);
    }

    public static void main(String[] args) {
        new Attendance().execute("Neznaev", "Floor 3");
    }
}
