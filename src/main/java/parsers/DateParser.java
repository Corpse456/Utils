package parsers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class DateParser {

    public LocalDateTime milliToLocalDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(1587143661753L), TimeZone.getDefault().toZoneId());
    }
}
