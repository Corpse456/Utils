package htmlConnector;

import com.google.gson.Gson;
import parsers.ExcerptFromText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HtmlExecutor {

    private static final String GOOGLE = "https://www.googleapis.com/customsearch/v1?key=";
    private static final String API_KEY = "AIzaSyBzrncKHG5QvdgxVLnUaFnvJguMNGWGVgE";
    private static final String SEARCH_ENGINE = "016782248932805790950:fxjqnp1ffvw";
    private static final String SEARCH_URL = GOOGLE + API_KEY + "&cx=" + SEARCH_ENGINE + "&q=";
    private String encoding = "UTF-8";
    private HttpURLConnection connection;
    private String cookie = null;


    /**
     * @param path     - URL требуемого ресурса
     * @param encoding - кодировка
     * @return
     */
    public String contentGetExecutor(String path, String encoding) {
        this.encoding = encoding;
        return contentGetExecutor(path);
    }

    /**
     * @param path - URL требуемого ресурса
     * @return
     */
    public String contentGetExecutor(String path) {
        StringBuilder content = new StringBuilder();

        try {
            URL site = new URL(path);
            connection = (HttpURLConnection) site.openConnection();
            connection.setRequestMethod("GET");
            if (cookie != null) {
                connection.setRequestProperty("Cookie", cookie);
            }
            connection.setRequestProperty("User-Agent",
                                          "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String answer = content.toString();

        // если сайт требует куки, попытаться взять их из ответа сайта и отправить запрос повторно
        if (answer.contains("To visit this site requires javascript and cookies of your browser.") && cookie == null) {
            cookie = cookieExtractor(answer);
            return contentGetExecutor(path);
        }

        return answer;
    }

    /**
     * @param path - URL требуемого ресурса
     * @return
     */
    public String contentPostExecutor(String path) {
        return contentPostExecutor(path, null);
    }

    public String contentPostExecutor(String path, final Map<String, String> parameters) {
        StringBuilder content = new StringBuilder();

        try {
            URL site = new URL(path);
            connection = (HttpURLConnection) site.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            if (cookie != null) {
                connection.setRequestProperty("Cookie", cookie);
                connection.setRequestProperty("Authorization", "Basic bmV6bmFldjpGYWxsb3V0NzY=");
            }
            connection.setRequestProperty("User-Agent",
                                          "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
            if (parameters != null) {
                final StringBuilder params = new StringBuilder();
                parameters.forEach((key, value) -> {
                    params.append(key);
                    params.append("=");
                    params.append(value);
                    params.append("&");
                });
                params.deleteCharAt(params.length() - 1);

                final OutputStream outputStream = connection.getOutputStream();
                outputStream.write(params.toString().getBytes());
                outputStream.flush();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine + "\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String answer = content.toString();

        // если сайт требует куки, попытаться взять их из ответа сайта и отправить запрос повторно
        if (answer.contains("To visit this site requires javascript and cookies of your browser.") && cookie == null) {
            cookie = cookieExtractor(answer);
            return contentGetExecutor(path);
        }

        return answer;
    }

    /**
     * @param answer ответе сайта, c прописанными куки
     * @return извлечённая строка куки
     */
    private String cookieExtractor(String answer) {
        int start = answer.indexOf("cookie=\"") + "cookie=\"".length();
        // если такая строчка не найдена
        if (start == -1) {
            return "MfPers=12678646695048a98819027298bf50127329f8c315e8f; vuid=8a98819027298bf50127329f8c315e8f; ptkn=40EAFA18-5758-F374-F570-A0480F306222; WT_FPC=id=174.142.104.57-1456441520.30063880:lv=1267888167073:ss=1267888167073; __utma=76091412.2059393411.1267864686.1267878351.1267891770.4; __utmz=76091412.1267864686.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); BFHost=wd-web04.osl.basefarm.net; JSESSIONID=20C8FD4414F50F3AE361C487D0E3C719; MfTrack=12678917654148a98819027298bf50127329f8c315e8f; BIGipServerwd-web-pt=285284362.20480.0000; __utmb=76091412.1.10.1267891770; __utmc=76091412";
        }
        int end = answer.indexOf("\";");
        return answer.substring(start, end);
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    /**
     * @param text - текст, который необходимо найти в гугл
     * @return - Map описаний ссылок и самих сссылок (title, link)
     */
    public List<GoogleResult.Item> findInGoogle(final String text) {
        InputStreamReader reader = null;
        try {
            URL url = new URL(SEARCH_URL + URLEncoder.encode(text, encoding));
            reader = new InputStreamReader(url.openStream(), encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Gson().fromJson(reader, GoogleResult.class).getItems();
    }

    public String wikiExecutor(String link) {
        String wikiContent = contentGetExecutor(link);

        String title = new ExcerptFromText().titleFromHtmlPage(wikiContent);
        String wiki = "";

        if (link.contains("ru.wikipedia.org")) {
            wiki = " — Википедия";
        } else if (link.contains("be.wikipedia.org")) {
            wiki = " — Вікіпедыя";
        } else {
            wiki = " - Wikipedia";
        }
        title = title.replaceAll(wiki, "");

        return title;
    }

    public static void main(String[] args) {
        List<GoogleResult.Item> found = new HtmlExecutor().findInGoogle("Википедия tesv - skyrim ru");
        found.forEach(m -> System.out.println(m.getTitle()));
    }
}
