package workWithFiles.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class XMLContentChanger2 {

    private static final String START_TAG = "<propertiesContent>";
    private static final String END_TAG = "</propertiesContent>";

    public static void main(String[] args) throws Exception {
        final String[] arg = {"d:/config.xml"};
        convert(arg);
    }

    public static void convert(String[] args) throws Exception {
        for (final String xml : args) {
            convert(xml);
        }
    }

    private static void convert(String xmlName) throws Exception {
        final StringBuilder result = new StringBuilder();
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(xmlName)));

        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();

            if (line.contains(START_TAG)) {
                appendProperties(result, bufferedReader, line);
            } else {
                result.append(line).append("\n");
            }
        }
        bufferedReader.close();

        final String newName = xmlName.replace(".xml", "_New.xml");
        try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(newName)))) {
            bufferedWriter.write(result.toString());
        }
    }

    private static void appendProperties(final StringBuilder result,
                                         final BufferedReader bufferedReader,
                                         String line) throws IOException {
        final List<String> prop = new ArrayList<>();

        result.append(START_TAG).append("\n");

        prop.add(line.split(START_TAG)[1]);
        while (!(line = bufferedReader.readLine()).contains(END_TAG)) {
            prop.add(line);
        }
        prop.add(line.replace(END_TAG, ""));
        final String properties = getProperties(prop);

        result.append(properties);
        result.append(END_TAG).append("\n");
    }

    private static String getProperties(final List<String> properties) {
        final Properties propertiesToSave = new Properties();
        properties.forEach(p -> {
            if (p.contains("=")) {
                final String[] split = p.split("=");
                propertiesToSave.put(getStringReformatted(split[0]), getStringReformatted(split[1]));
            }
        });
        return getPropertyAsString(propertiesToSave);
    }

    private static String getStringReformatted(final String string) {
        return string.replaceAll("\\\\", "\\\\\\\\");
    }

    private static String getPropertyAsString(Properties prop) {
        StringWriter writer = new StringWriter();
        prop.list(new PrintWriter(writer));
        return writer.getBuffer().toString();
    }
}
