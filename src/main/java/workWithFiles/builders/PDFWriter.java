package workWithFiles.builders;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_LEFT;

public class PDFWriter {

    private final static String PATH = "Ambassador.pdf";
    private final Document document;
    private final ByteArrayOutputStream os;
    private final static int BETWEEN = 20;

    public PDFWriter(final List<Pair<String, String>> footerList) {
        document = new Document();
        os = new ByteArrayOutputStream();
        try {
            final HeaderFooterPageEvent footer = new HeaderFooterPageEvent(footerList);
            final PdfWriter instance = PdfWriter.getInstance(document, os);
            instance.setPageEvent(footer);
        } catch (final DocumentException e) {
            e.printStackTrace();
        }
        document.setMargins(document.leftMargin(), document.rightMargin(), document.topMargin(), (footerList.size() *
                                                                                                  BETWEEN) + BETWEEN);
        document.open();
    }

    public PdfPTable createTable(final List<List<String>> content) {
        if (content == null) {
            return null;
        }
        final PdfPTable table = new PdfPTable(content.get(0).size());
        for (final List<String> values : content) {
            addListToTable(table, values, Font.NORMAL);
        }
        return table;
    }

    public void addTable(final PdfPTable table) {
        addToDocument(table);
    }

    public void addBoldLineToTable(final PdfPTable table, final List<String> values) {
        addListToTable(table, values, Font.BOLD);
    }

    private void addListToTable(final PdfPTable table, final List<String> values, final int bold) {
        for (final String value : values) {
            final PdfPCell cell = new PdfPCell(new Phrase(getChunk(value, bold, 16)));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }
    }

    public byte[] getBytesAndClose() {
        document.close();
//        return os.toByteArray();

        try (final OutputStream outputStream = new FileOutputStream(PATH)) {
            os.writeTo(outputStream);
            os.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

    public void createTitle(final List<String> titleRows) {
        createLines(titleRows, Element.ALIGN_RIGHT, Font.NORMAL);
    }

    public void createLines(final List<String> titleRows) {
        createLines(titleRows, Element.ALIGN_LEFT, Font.NORMAL);
    }

    public void createBoldLines(final List<String> lines) {
        createLines(lines, Element.ALIGN_LEFT, Font.BOLD);
    }

    private void createLines(final List<String> lines, final int align, final int font) {
        lines.forEach(row -> addToDocument(getParagraph(align, row, font)));
    }

    public void createEmptyLines(final int amount) {
        for (int i = 0; i < amount; i++) {
            addToDocument(Chunk.NEWLINE);
        }
    }

    private void addToDocument(final Element paragraph) {
        try {
            document.add(paragraph);
        } catch (final DocumentException e) {
            e.printStackTrace();
        }
    }

    private Paragraph getParagraph(final int align, final String text, final int bold) {
        final Chunk chunk = getChunk(text, bold, 16);
        final Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        return paragraph;
    }

    private Chunk getChunk(final String text, final int bold, final int size) {
        return new Chunk(text, FontFactory.getFont(FontFactory.TIMES_ROMAN, size, bold));
    }

    private class HeaderFooterPageEvent extends PdfPageEventHelper {

        private final List<Pair<String, String>> footerList;

        private HeaderFooterPageEvent(final List<Pair<String, String>> footerList) {
            this.footerList = footerList;
        }

        public void onStartPage(final PdfWriter writer, final Document document) {
            final Phrase phrase = new Phrase(getChunk("Page " + document.getPageNumber() + "", Font.NORMAL, 11));
            ColumnText.showTextAligned(writer.getDirectContent(), ALIGN_CENTER, phrase, document.leftMargin(), 800, 0);
        }

        public void onEndPage(final PdfWriter writer, final Document document) {
            if (footerList == null) {
                return;
            }
            int initialY = BETWEEN;
            for (final Pair<String, String> line : footerList) {
                ColumnText.showTextAligned(writer.getDirectContent(), ALIGN_LEFT, new Phrase(line.getKey()),
                                           document.leftMargin(), initialY, 0);
                ColumnText.showTextAligned(writer.getDirectContent(), ALIGN_LEFT, new Phrase(line.getValue()),
                                           400, initialY,
                                           0);
                initialY += BETWEEN;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        final File file = new File(PATH);
        System.out.println("file = " + file.getAbsolutePath());
        System.out.println("java.io.tmpdir = " + System.getProperty("java.io.tmpdir"));

        System.out.println("user.dir = " + System.getProperty("user.dir"));

        File currentDirFile = new File(".");
        System.out.println("currentDirFile = " + currentDirFile);
        String helper = currentDirFile.getAbsolutePath();
        System.out.println("helper = " + helper);
        System.out.println("currentDirFile.getCanonicalPath() = " + currentDirFile.getCanonicalPath());
        String currentDir = helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());
        System.out.println("currentDir = " + currentDir);

        /*file = D:\Me\JAVA\Utils\Ambassador.pdf
java.io.tmpdir = C:\Users\Corpse\AppData\Local\Temp\
user.dir = D:\Me\JAVA\Utils
currentDirFile = .
helper = D:\Me\JAVA\Utils\.
currentDirFile.getCanonicalPath() = D:\Me\JAVA\Utils
currentDir = D:*/
    }
}