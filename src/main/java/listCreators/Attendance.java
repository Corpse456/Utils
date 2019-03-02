package listCreators;

import htmlConnector.HtmlExecutor;
import parsers.ExcerptFromText;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Locale.ENGLISH;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class Attendance {

    private static final DateTimeFormatter DATE_FORMATTER = ofPattern("yyyy-MM-dd", ENGLISH);
    private final static String PATH = "https://spirit.generation-p.com/attendance";
    private static final String LAST_NAME = "lastName";
    private static final String BEFORE = LAST_NAME + "</td><td></td><td></td><td>";
    private static final String FLOUR = "Flour";
    private static final String AFTER = "<\\/td><td>" + FLOUR + "<\\/td>";
    private static final String TIME_REGEXP = "\\d:";
    private static final int SECONDS_IN_8 = 60 * 60 * 8;
    private static final String CREDENTIAL_PATH = System.getProperty("java.io.tmpdir") + "/attendance.csv";

    private void execute(final String lastName, final String flour) {
        LocalDate startOfMonth = LocalDate.now().minusDays(7).withDayOfMonth(1);
        final int currentMonth = startOfMonth.getMonthValue();

        int jobDays = 0;
        int totalSeconds = 0;
        while (currentMonth == startOfMonth.getMonthValue()) {
            final String time = getForDay(lastName, FLOUR + flour, startOfMonth);
            if (time != null) {
                final int[] split = Stream.of(time.split(":")).mapToInt(Integer::parseInt).toArray();
                totalSeconds += split[0] * 3600 + split[1] * 60 + split[2];
                jobDays++;
            }
            startOfMonth = startOfMonth.plusDays(1);
        }
        final int expectedSeconds = jobDays * SECONDS_IN_8;
        final int different = totalSeconds - expectedSeconds;
        System.out.println((different > 0 ? "overtime: " : "not enough: ") + parseDifferent(Math.abs(different)));
    }

    private LocalTime parseDifferent(final int different) {
        final int hours = different / 3600;
        final int minutes = (different % 3600) / 60;
        final int seconds = (different % 3600) % 60;
        return LocalTime.of(hours, minutes, seconds);
    }

    private String getForDayStub(final String lastName, final String flour, final LocalDate date) {
        if (Math.random() < 0.2) {
            return null;
        }
        final Random random = new Random();
        return random.nextInt(4) + 6 + ":" + random.nextInt(60) + ":" + random.nextInt(60);
    }
    
    private String getForDay(final String lastName, final String flour, final LocalDate date) {
        HtmlExecutor exec = new HtmlExecutor();
        final HashMap<String, String> map = new HashMap<>();
        map.put("calendar", date.format(DATE_FORMATTER));
        map.put("user", lastName);
        String content = exec.contentPostExecutor(PATH, map);
        try {
            final FileWriter writer = new FileWriter("content" + System.currentTimeMillis());
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String afterTotal = content.split("TOTAL")[1];
        final ExcerptFromText excerpt = new ExcerptFromText();

        final List<String> excerpts = excerpt.extractExcerptsFromText(afterTotal, BEFORE.replace(LAST_NAME, lastName),
                                                                      AFTER.replace(FLOUR, flour), TIME_REGEXP);
        if (excerpts.isEmpty()) {
            return null;
        }
        return excerpts.get(0);
    }

    public static void main(final String[] args) throws IOException {
        if (args.length != 0 && !isEmpty(args[0]) && !isEmpty(args[1])) {
            new Attendance().execute(args[0], args[1]);
            final FileWriter fileWriter = new FileWriter(CREDENTIAL_PATH);
            fileWriter.write(args[0] + "," + args[1]);
            fileWriter.close();
        } else {
            try {
                final BufferedReader fileReader = new BufferedReader(new FileReader(CREDENTIAL_PATH));
                final String[] credentials = fileReader.readLine().split(",");
                new Attendance().execute(credentials[0], credentials[1]);
            } catch (final IOException e) {
                System.err.println("Wrong arguments. Example: java -jar Attendance.jar LastName 1");
            }
        }
    }
}
