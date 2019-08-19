package workWithFiles.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class PropertiesChanger {

    public static void main(String[] args) throws Exception {
        for (final String arg : args) {
            System.out.println("arg = " + arg);
        }
        final List<String> listArgs = Arrays.asList(args);
        List<String> inputFileNames = getOptionValues("file", listArgs);
        final List<File> files = addFolder(inputFileNames);
        List<String> propertiesList = getOptionValues("property", listArgs);

        final Map<String, String> properties = getPropertiesMap(propertiesList);

        for (final File file : files) {
            convert(file, properties);
        }
        System.out.println("Done");
    }

    private static List<File> addFolder(final List<String> inputFileNames) {
        final List<File> files = new ArrayList<>();
        for (final String inputFileName : inputFileNames) {
            final File file = new File(inputFileName);
            if (file.isDirectory()) {
                files.addAll(Arrays.stream(Objects.requireNonNull(file.listFiles()))
                                 .filter(f -> f.getName().contains(".properties")).collect(Collectors.toList()));
            } else {
                files.add(file);
            }
        }
        return files;
    }

    private static List<String> getOptionValues(final String property, final List<String> args) {
        final List<String> list = new ArrayList<>();
        final int propertyIndex =
            Integer.max(args.indexOf("--" + property), args.indexOf("-" + property.charAt(0)));

        for (int i = propertyIndex + 1; i < args.size() && args.get(i).charAt(0) != '-'; i++) {
            list.add(args.get(i));
        }
        if (list.isEmpty()) {
            System.out.println("Missed argument " + property);
            System.exit(1);
        }
        return list;
    }

    private static Map<String, String> getPropertiesMap(final List<String> args) {
        System.out.println("properties = " + args);
        final Map<String, String> properties = new HashMap<>();
        for (final String arg : args) {
            final String[] split = arg.split("=");
            properties.put(split[0], split[1]);
        }
        return properties;
    }

    private static void convert(File file, final Map<String, String> properties) throws Exception {
        final StringBuilder result = new StringBuilder();
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();

            final String property = line.split("=")[0].replaceAll(" ", "").replaceAll("#", "");
            if (properties.containsKey(property)) {
                line = property + "=" + properties.get(property);
            }
            result.append(line).append("\n");
        }
        bufferedReader.close();

        try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(result.toString());
        }
    }
}
