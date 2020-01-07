package helper;

import parsers.ExcerptFromText;

public class Gastrofest {

    private final static String URL = "https://gastrofest.by/node/";
    private final static String START = "Возможность взять с собой:";
    private final static String END = "НЕТ";
    private final static int START_NUMBER = 12911;
    private final static int END_NUMBER = 12930;

    public static void main(String[] args) {
        for (int i = START_NUMBER; i <= END_NUMBER; i++) {
            final String url = URL + i;
            if (!ExcerptFromText.contains(url, START, END)) {
                System.out.println(url);
            }
        }
    }
}
