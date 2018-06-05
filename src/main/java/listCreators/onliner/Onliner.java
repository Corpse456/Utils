package listCreators.onliner;

import java.io.IOException;

import htmlConnector.HtmlExecutor;
import workWithFiles.fileIO.WriterToFile;

public class Onliner {

    private static final String PATH = "https://catalog.onliner.by/videocard";
    private static final String COOKIE = "Gdyn=KlQL2RMGQMQG77DfsCnlNnE5ssGMjjLy7s4xLl8exgLif8QsG0Y6MHnMgM2GYlCFGiG8SlGSSGROguJiLRaGGSpASrYgsEgjhn4O0JGm3HW8IfrkQWW_2cjegmAv4AYeaFSG; ouid=snyBEFmupSGbyQvHw0KWAg==; ouid=snyBDlmupSN72gIIM6QmAg==; ouid=snyBDFmupTMvmU6Kv0r2Ag==; ouid=snyBDFnPXkR4My5WTK73Ag==; ouid=snyBDlmupSF7LwIHKa0ZAg==; tmr_detect=1%7C1519237940358";

    public static void main(String[] args) throws IOException {
        HtmlExecutor exec = new HtmlExecutor();
        exec.setCookie(COOKIE);
        String content = exec.contentExecutor(PATH);
        // System.out.println(content);
        WriterToFile writer = new WriterToFile("C:/onliner.html");
        writer.write(content);
        writer.close();
        System.out.println("Done");
    }
}
