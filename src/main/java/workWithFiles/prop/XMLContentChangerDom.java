package workWithFiles.prop;

import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

public class XMLContentChangerDom {

    private static final String TAGNAME = "propertiesContent";

    public static void main(String[] args) throws Exception {
        for (final String xml : args) {
            convert(xml);
        }
    }

    private static void convert(String xmlName) throws Exception {
        final Document document = getDocument(xmlName);
        String[] properties = getTagData(document).split("\n");

        final String propertyAsString = getProperties(properties);
        setTagData(document, propertyAsString);

        saveXml(xmlName, document);
    }

    private static String getProperties(final String[] properties) {
        final Properties propertiesToSave = new Properties();
        for (final String property : properties) {
            final String[] splitted = property.split("=");
            propertiesToSave.put(getStringReformatted(splitted[0]), getStringReformatted(splitted[1]));
        }
        return getPropertyAsString(propertiesToSave);
    }

    private static void saveXml(final String xmlName, final Document document) throws TransformerException {
        document.setXmlStandalone(true);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(xmlName.replace(".prop", "_New.prop")));
        transformer.transform(source, result);
    }

    private static String getStringReformatted(final String string) {
        return string.replaceAll("\\\\", "\\\\\\\\");
    }

    private static String getPropertyAsString(Properties prop) {
        StringWriter writer = new StringWriter();
        prop.list(new PrintWriter(writer));
        return writer.getBuffer().toString();
    }

    private static Document getDocument(final String xml) throws Exception {
        File inputFile = new File(xml);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        dbFactory.setIgnoringComments(true);
        dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        dbFactory.setIgnoringElementContentWhitespace(true);
        final Document document = dBuilder.parse(inputFile);
        document.getDocumentElement().normalize();
        return document;
    }

    private static String getTagData(final Document doc) {
        return doc.getElementsByTagName(TAGNAME).item(0).getFirstChild().getNodeValue();
    }

    private static void setTagData(final Document doc, final String value) {
        doc.getElementsByTagName(TAGNAME).item(0).getFirstChild().setNodeValue(value);
    }
}
