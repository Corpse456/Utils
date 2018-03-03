package listCreators;

import fileOperation.WriterToFile;
import htmlConnector.HtmlExecutor;

public class Igromania {
    public static void main(String[] args) {
        String PATH = "https://www.igromania.ru/games/";
        for (int i = 1; i < 2; i++) {
            HtmlExecutor exec = new HtmlExecutor();
            String content = exec.contentExecutor(PATH + i + "/1/");
            // System.out.println(content);
            WriterToFile writer = new WriterToFile("C:/Igromania.html");
            writer.write(content);
            writer.close();
            System.out.println("Done");
        }
    }
}
