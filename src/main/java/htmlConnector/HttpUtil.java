package htmlConnector;

import org.eclipse.jetty.http.HttpStatus;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

public class HttpUtil {

    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String GET = "GET";
    public static final String DELETE = "DELETE";

    public static String sendPost(final String urlPath,
                                  final String value,
                                  final HashMap<String, String> property) throws Exception {
        return connect(POST, urlPath, value, property);
    }

    public static String sendPut(final String urlPath,
                                 final String value,
                                 final HashMap<String, String> property) throws Exception {
        return connect(PUT, urlPath, value, property);
    }

    public static String sendGet(final String urlPath,
                                 final HashMap<String, String> property) throws Exception {
        return connect(GET, urlPath, null, property);
    }

    private static String connect(final String post,
                                  final String urlPath,
                                  final String value,
                                  final HashMap<String, String> property) throws Exception {
        final URL url = new URL(urlPath);
        final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        connection.setRequestMethod(post);
        property.forEach(connection::setRequestProperty);

        if (value != null) {
            connection.setDoOutput(true);
            final DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(value);
            outputStream.flush();
            outputStream.close();
        }

        final int responseCode = connection.getResponseCode();
        final String body = getBody(responseCode, connection);
        if (HttpStatus.isSuccess(responseCode)) {
            return body;
        } else {
            throw new Exception(body);
        }
    }

    private static String getBody(final int responseCode, final HttpsURLConnection connection) throws IOException {
        final BufferedReader reader;
        if (200 <= responseCode && responseCode <= 299) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String inputLine;
        final StringBuilder response = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
        return response.toString();
    }
}