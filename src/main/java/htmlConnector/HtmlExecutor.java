package htmlConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import fileOperation.WriterToFile;
import parsers.CP1251toUTF8;
import parsers.TextFromHtml;

public class HtmlExecutor {
    /**
     * @param path URL требуемого ресурса
     * @return
     */
    public String contentExecutor(String path) {
	StringBuilder content = new StringBuilder();
	String inputLine = "";
	
	try {
	    URL site = new URL(path);
	    HttpURLConnection connection = (HttpURLConnection) site.openConnection();
	    connection.setRequestMethod("GET");
	    connection.setRequestProperty("Cookie", "MfPers=12678646695048a98819027298bf50127329f8c315e8f; vuid=8a98819027298bf50127329f8c315e8f; ptkn=40EAFA18-5758-F374-F570-A0480F306222; WT_FPC=id=174.142.104.57-1456441520.30063880:lv=1267888167073:ss=1267888167073; __utma=76091412.2059393411.1267864686.1267878351.1267891770.4; __utmz=76091412.1267864686.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); BFHost=wd-web04.osl.basefarm.net; JSESSIONID=20C8FD4414F50F3AE361C487D0E3C719; MfTrack=12678917654148a98819027298bf50127329f8c315e8f; BIGipServerwd-web-pt=285284362.20480.0000; __utmb=76091412.1.10.1267891770; __utmc=76091412");	       
	    connection.setRequestProperty("User-Agent", "Opera/9.80 (Windows NT 6.1; U; en) Presto/2.9.168 Version/11.52");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

	    while ((inputLine = reader.readLine()) != null) {
		content.append(inputLine + "\n");
	    }
	    reader.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return content.toString();
    }
    
    public String findInGoogle(String text) {
	text = text.replaceAll(" ", "+");
	CP1251toUTF8 converter = new CP1251toUTF8();
	text = converter.convert(text);
	return contentExecutor("https://www.google.by/search?q=" + text) ;
    }
    
    public static void main(String[] args) {
	String find = new HtmlExecutor().contentExecutor("https://ru.wikipedia.org/wiki/Metal_Gear_Solid_V:_The_Phantom_Pain");
	WriterToFile writer = new WriterToFile("C:/3.html");
	writer.write(find);
	writer.close();
    }
}
