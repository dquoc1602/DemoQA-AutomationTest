package common.file;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Provides static utilities for writing various file formats (TXT, JSON, CSV, XML).
 */
public class FileWriterManager {
    private static final Logger logger = LogManager.getLogger(FileWriterManager.class);

    private FileWriterManager() {
        // Prevent instantiation
    }

    // ───────────────────── TEXT WRITING ─────────────────────

    public static void writeText(String filePath, String content) {
        writeText(filePath, content, StandardCharsets.UTF_8);
    }

    public static void writeText(String filePath, String content, Charset charset) {
        try {
            Path path = Paths.get(filePath);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.writeString(path, content, charset, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            logger.info("Wrote text to file: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to write text to file: {}", filePath, e);
            throw new RuntimeException("Could not write to text file: " + filePath, e);
        }
    }

    public static void appendText(String filePath, String content) {
        try {
            Path path = Paths.get(filePath);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.writeString(path, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            logger.info("Appended text to file: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to append text to file: {}", filePath, e);
            throw new RuntimeException("Could not append to text file: " + filePath, e);
        }
    }

    public static void writeLines(String filePath, List<String> lines) {
        try {
            Path path = Paths.get(filePath);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            logger.info("Wrote {} lines to file: {}", lines.size(), filePath);
        } catch (IOException e) {
            logger.error("Failed to write lines to file: {}", filePath, e);
            throw new RuntimeException("Could not write lines to file: " + filePath, e);
        }
    }

    // ───────────────────── JSON WRITING ─────────────────────

    public static void writeJson(String filePath, JSONObject jsonObject) {
        writeJson(filePath, jsonObject, 4);
    }

    public static void writeJson(String filePath, JSONObject jsonObject, int indent) {
        try {
            writeText(filePath, jsonObject.toString(indent));
        } catch (Exception e) {
            logger.error("Failed to write JSON string to file: {}", filePath, e);
            throw new RuntimeException("Could not write JSON file: " + filePath, e);
        }
    }

    // ───────────────────── CSV WRITING ─────────────────────

    public static void writeCsv(String filePath, String[] headers, List<List<String>> records) {
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader(headers)
                .build();

        Path path = Paths.get(filePath);
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            try (FileWriter out = new FileWriter(filePath);
                 CSVPrinter printer = new CSVPrinter(out, format)) {
                 
                for (List<String> record : records) {
                    printer.printRecord(record);
                }
                logger.info("Wrote {} CSV records to file: {}", records.size(), filePath);
            }
        } catch (IOException e) {
            logger.error("Failed to write CSV file: {}", filePath, e);
            throw new RuntimeException("Could not write CSV file: " + filePath, e);
        }
    }

    // ───────────────────── XML WRITING ─────────────────────

    /**
     * Writes a DOM Document to an XML file.
     */
    public static void writeXml(String filePath, Document document) {
        try {
            Path path = Paths.get(filePath);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            // Secure XML processing
            transformerFactory.setAttribute("indent-number", 4);
            
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
            
            logger.info("Wrote XML Document to file: {}", filePath);
        } catch (Exception e) {
            logger.error("Failed to write XML Document to file: {}", filePath, e);
            throw new RuntimeException("Could not write XML file: " + filePath, e);
        }
    }
}
