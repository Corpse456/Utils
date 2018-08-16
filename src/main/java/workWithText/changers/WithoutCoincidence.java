/**
 * Совмещает содержимое двух текстовых фалов не дублируя записи
 */

package workWithText.changers;

import java.util.Iterator;
import java.util.TreeSet;

import workWithFiles.fileIO.ReaderFromFile;
import workWithFiles.fileIO.WriterToFile;

public class WithoutCoincidence {

    private static final String FILE2 = "rusWoman2.txt";
    private static final String FILE1 = "rusWoman1.txt";
    private static TreeSet<String> list = new TreeSet<String>();
    private static ReaderFromFile reader;

    public static void main(String[] args) {
        reader = new ReaderFromFile(WithoutCoincidence.class.getClassLoader().getResource(FILE1));
        addToList();
        reader = new ReaderFromFile(WithoutCoincidence.class.getClassLoader().getResource(FILE2));
        addToList();
        reader.close();
        System.out.println(list);
        WriterToFile writer = new WriterToFile("C:/rusWoman.txt");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            writer.writeLine(iterator.next());
        }
        writer.close();
    }

    /**
     * @param list
     * @param reader
     */
    private static void addToList() {
        while (reader.isReady()) {
            list.add(reader.readLine());
        }
    }
}
