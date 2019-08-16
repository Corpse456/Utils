package workWithFiles.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class PropertiesChanger {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Wrong usage");
            System.exit(0);
        }
        final Map<String, String> properties = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            final String[] split = args[i].split("=");
            properties.put(split[0], split[1]);
        }
        convert(args[0], properties);
    }

    private static void convert(String xmlName, final Map<String, String> properties) throws Exception {
        final StringBuilder result = new StringBuilder();
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(xmlName)));

        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();

            final String property = line.split("=")[0].replaceAll(" ", "");
            if (properties.containsKey(property)) {
                line = property + "=" + properties.get(property);
            }
            result.append(line).append("\n");
        }
        bufferedReader.close();

        final String newName = xmlName.replace(".properties", "_New.properties");
        try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(newName)))) {
            bufferedWriter.write(result.toString());
        }
    }
}
