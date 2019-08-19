package workWithFiles.xml;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class PropertiesChanger {

    public static void main(String[] args) throws Exception {
        CommandLine cmd = getCommandLine(args);

        String[] inputFiles = cmd.getOptionValues("file");
        String[] propertiesList = cmd.getOptionValues("property");

        final Map<String, String> properties = getPropertiesMap(propertiesList);

        for (final String inputFile : inputFiles) {
            convert(inputFile, properties);
        }
    }

    private static Map<String, String> getPropertiesMap(final String[] args) {
        final Map<String, String> properties = new HashMap<>();
        for (final String arg : args) {
            final String[] split = arg.split("=");
            properties.put(split[0], split[1]);
        }
        return properties;
    }

    private static CommandLine getCommandLine(final String[] args) {
        Options options = new Options();

        addOption(options, "File path", "file");
        addOption(options, "Properties", "property");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("PropertiesChanger", options);

            System.exit(1);
        }
        return cmd;
    }

    private static void addOption(final Options options, final String description, final String longOpt) {
        Option file = new Option(longOpt.charAt(0) + "", longOpt, true, description);
        file.setRequired(true);
        file.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(file);
    }

    private static void convert(String xmlName, final Map<String, String> properties) throws Exception {
        final StringBuilder result = new StringBuilder();
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(xmlName)));

        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();

            final String property = line.split("=")[0].replaceAll(" ", "").replaceAll("#", "");
            if (properties.containsKey(property)) {
                line = property + "=" + properties.get(property);
            }
            result.append(line).append("\n");
        }
        bufferedReader.close();

        try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(xmlName)))) {
            bufferedWriter.write(result.toString());
        }
    }
}
