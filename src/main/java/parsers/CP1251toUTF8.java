package parsers;

import java.util.HashMap;
import java.util.Map;

import fileOperation.ReaderFromFile;

public class CP1251toUTF8 {

    Map<String, String> alphabet;

    public CP1251toUTF8() {
        alphabet = new HashMap<>();
        ReaderFromFile reader = new ReaderFromFile(CP1251toUTF8.class.getClassLoader().getResource("RussionSymbols.csv"));
        while (reader.isReady()) {
            String[] current = reader.readLine().split(",");
            alphabet.put(current[1], current[0]);
        }
        reader.close();
    }

    public String convert(String string) {
        StringBuilder convertedString = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            if (alphabet.containsKey(string.charAt(i) + "")) {
                convertedString.append(alphabet.get(string.charAt(i) + ""));
            } else convertedString.append(string.charAt(i) + "");
        }
        return convertedString.toString();
    }
}
