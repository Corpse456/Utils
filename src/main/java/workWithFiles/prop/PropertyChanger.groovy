package workWithFiles.prop

import java.util.stream.Collectors

println("Here")

//def env = System.getenv()

final List<String> listArgs = Arrays.asList(args)
List<String> inputFileNames = getOptionValues("file", listArgs)
String type = getOptionValues("type", listArgs).get(0)
List<String> propertiesList = getOptionValues("property", listArgs)

final List<File> files = addFolders(inputFileNames, type)

final Map<String, String> properties = getPropertiesMap(propertiesList)

for (final File file : files) {
    convert(file, properties)
}
println("Done")

private static List<File> addFolders(final List<String> inputFileNames, final String type) {
    final List<File> files = new ArrayList<>()
    for (final String inputFileName : inputFileNames) {
        final File file = new File(inputFileName)
        if (file.isDirectory()) {
            files.addAll(Arrays.stream(Objects.requireNonNull(file.listFiles()))
                    .filter({ f -> isContains(f.getName(), type) })
                    .collect(Collectors.toList()))
        } else {
            files.add(file)
        }
    }
    return files
}

private static boolean isContains(final String name, final String value) {
    return name.toLowerCase().contains(("." + value).toLowerCase())
}

private static List<String> getOptionValues(final String property, final List<String> args) {
    final List<String> list = new ArrayList<>()
    final int propertyIndex =
            Integer.max(args.indexOf("--" + property), args.indexOf("-" + property.charAt(0)))

    for (int i = propertyIndex + 1; i < args.size() && args.get(i).charAt(0) != '-' as char; i++) {
        list.add(args.get(i))
    }
    if (list.isEmpty()) {
        System.err.println("Missed argument " + property)
        System.exit(1)
    }
    return list
}

private static Map<String, String> getPropertiesMap(final List<String> args) {
    final Map<String, String> properties = new HashMap<>()
    for (final String arg : args) {
        final String[] split = arg.split("=")
        properties.put(split[0], split[1])
    }
    return properties
}

private static void convert(File file, final Map<String, String> properties) throws Exception {
    final StringBuilder result = new StringBuilder()
    final BufferedReader bufferedReader = new BufferedReader(new FileReader(file))

    while (bufferedReader.ready()) {
        String line = bufferedReader.readLine()

        final String property = line.split("=")[0].replaceAll(" ", "").replaceAll("#", "")
        if (properties.containsKey(property)) {
            line = property + "=" + properties.get(property)
        }
        result.append(line).append("\n")
    }
    bufferedReader.close()

    try {
        final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))
        bufferedWriter.write(result.toString())
        bufferedWriter.close()
    } catch (Exception e) {
        println("Cannot write file " + file.getName())
        e.printStackTrace()
    }
}
