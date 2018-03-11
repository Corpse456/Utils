package fileListings;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import fileOperation.WriterToFile;
import htmlConnector.HtmlExecutor;

public class Catalogs {

    private final String DISC_LETTER;
    private final String PATH;

    public Catalogs(String disc, String path) {
        DISC_LETTER = disc;
        PATH = path;
    }

    /**
     * @return
     */
    public boolean createList() {
        try {
            WriterToFile writer = new WriterToFile(PATH);
            File disc = new File(DISC_LETTER);
            writer.write(discAtributes(disc));
            for (File f : disc.listFiles()) {
                if (isGameFolder(f)) {
                    goodNameApplier(f);
                    writer.writeLine(f.getName() + ";" + calculateSize(f));
                }
            }
            writer.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @param f
     */
    private void goodNameApplier(File f) {
        HtmlExecutor exec = new HtmlExecutor();
        Map<String, String> googleAnswer = exec.findInGoogle("Википедия " + f.getName());

        Collection<String> entrySet = googleAnswer.values();
        
        for (String link : entrySet) {
            if (link.contains("https://ru.wikipedia.org/wiki")) {
                String newName = wikiExecutor(link);
                String newNameAndPath = f.getAbsolutePath().replace(f.getName(), newName);
                f.renameTo(new File(newNameAndPath));
                break;
            }
        }
    }

    private String wikiExecutor(String link) {
        HtmlExecutor exec = new HtmlExecutor();
        exec.contentExecutor(link);
        return null;
    }

    /**
     * @param disc - имя диска, для которого требуется распечатать атрибуты
     * @return
     */
    public String discAtributes(File disc) {
        String string = disc.getPath() + "\r\n";
        string += disc.getFreeSpace() + "\r\n";
        string += disc.getTotalSpace() + "\r\n";
        return string;
    }

    /**
     * @param f - файл или папка, для которго необходимо посчитать размер
     * @return
     */
    public long calculateSize(File f) {
        long size = 0;
        if (f.listFiles() != null) {
            for (File f1 : f.listFiles()) {
                if (f1.isFile()) size += f1.length();
                if (f1.isDirectory()) size += calculateSize(f1);
            }
        }
        return size;
    }

    private boolean isGameFolder(File f) {
        if (!f.isDirectory()) return false;
        if ("$RECYCLE.BIN".equals(f.getName()) || "System Volume Information".equals(f.getName())) return false;
        return true;
    }
    
    public static void main(String[] args) {
        new Catalogs("", "").goodNameApplier(new File("C:/Клиника"));
        
        
        /*Catalogs catalogs = new Catalogs("g:", "d://Games.exe//One.csv");
        if (catalogs.createList()) System.out.println("Done");*/
    }
}
