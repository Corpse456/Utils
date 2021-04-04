package parsers;

import com.ibm.icu.text.Transliterator;

public class Transliteration {

    private final static String LATIN_TO_CYRILLIC = "Latin-Russian/BGN";
    private final static String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";

    private static String toRussian(final String string) {
        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        return toLatinTrans.transliterate(string);
    }
}
