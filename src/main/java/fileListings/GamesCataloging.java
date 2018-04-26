package fileListings;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import fileOperation.WriterToFile;
import htmlConnector.HtmlExecutor;
import parsers.CP1251toUTF8;
import parsers.ExcerptFromText;

public class GamesCataloging {

    private final String DISC_LETTER;
    private final String PATH;
    private final String[] EXEPTIONS = { "Космические рейнджеры", "Братья Пилоты 8", "Pharaoh & Cleopatra" };

    /**
     * @param disc - буква диска
     * @param path - путь для фала с именами
     */
    public GamesCataloging(String disc, String path) {
        DISC_LETTER = disc;
        PATH = path;
    }

    /**
     * @return
     */
    public boolean createList() {
        WriterToFile writer = null;
        try {
            writer = new WriterToFile(PATH);
            File disc = new File(DISC_LETTER);
            writer.write(discAtributes(disc));
            for (File f : disc.listFiles()) {
                if (isGame(f)) {
                    String name = goodNameApplier(f);
                    writer.writeLine(name + "," + calculateSize(f));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            writer.close();
        }
        return true;
    }

    /**
     * @param f - имя папки, для которой необходимо найти имя
     * @return 
     * @return дата выпуска игры
     */
    private String goodNameApplier(File f) {
        String name = f.getName();
        String changedName = name;
        System.out.println(name);
        if (f.isFile()) {
            int lastIndexOf = changedName.lastIndexOf(".");
            if (lastIndexOf > 0) changedName = changedName.substring(0, lastIndexOf);
        }
        changedName = changedName.replaceAll("\\[.*]", "");
        changedName = changedName.toLowerCase().replaceAll("repack", "");
        changedName = changedName.replaceAll("_", " ");
        System.out.println(changedName);
        
        HtmlExecutor exec = new HtmlExecutor();
        Map<String, String> googleAnswer = exec.findInGoogle("Википедия " + changedName);
        
        Collection<String> googleLinks = googleAnswer.values();

        String series = new CP1251toUTF8().convert("серия");
        for (String link : googleLinks) {
            if (link.contains("wikipedia.org/wiki") && !link.contains(series)) {
                String newName = wikiExecutor(link);
                //newName = newName.replace("(игра, ", "(");
                newName = newName.replace("(игра)", "");
                newName = newName.replaceAll(":", " ").replaceAll("  ", " ");
                
                String newNameAndPath = null;
                if (!name.equals(newName)) {
                    newNameAndPath = f.getAbsolutePath().replace(name, newName);
                    System.out.println(newNameAndPath);
                    //f.renameTo(new File(newNameAndPath));
                }
                return newNameAndPath;
            }
        }
        return null;
    }

    private String wikiExecutor(String link) {
        HtmlExecutor exec = new HtmlExecutor();
        String wikiContent = exec.contentExecutor(link);

        ExcerptFromText excerpt = new ExcerptFromText();
        String title = excerpt.TitleFromHtmlPage(wikiContent);
        String wiki = "";
        
        if (link.contains("ru.wikipedia.org")) {
            wiki = " — Википедия";
        } else if (link.contains("be.wikipedia.org")) {
            wiki = " — Вікіпедыя";
        } else {
            wiki = " - Wikipedia";
        }
        title = title.replaceAll(wiki, "");
        System.out.println(title);

        return title;
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

    private boolean isGame(File f) {
        if ("$RECYCLE.BIN".equals(f.getName()) || "System Volume Information".equals(f.getName())) return false;
        return true;
    }

    public static void main(String[] args) {
        GamesCataloging catalogs = new GamesCataloging("g:", "D:/Games.exe/Two.csv");
        if (catalogs.createList()) System.out.println("Done");
        /*String name = new GamesCataloging("g:", "D:/Games.exe/Two.csv").goodNameApplier(new File("g:/Космические рейнджеры"));
        System.out.println(name);*/
    }
}
