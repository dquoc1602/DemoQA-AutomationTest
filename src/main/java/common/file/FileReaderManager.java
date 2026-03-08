package common.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides static utilities for reading various file formats (TXT, JSON, CSV, XML).
 */
public class FileReaderManager {
    private static final Logger logger = LogManager.getLogger(FileReaderManager.class);

    private FileReaderManager() {
        // Prevent instantiation
    }

    // ───────────────────── TEXT READING ─────────────────────

    public static String readText(String filePath) {
        return readText(filePath, StandardCharsets.UTF_8);
    }

    public static String readText(String filePath, Charset charset) {
        try {
            return Files.readString(Paths.get(filePath), charset);
        } catch (IOException e) {
            logger.error("Failed to read text from file: {}", filePath, e);
            throw new RuntimeException("Could not read text file: " + filePath, e);
        }
    }

    public static List<String> readLines(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("Failed to read lines from file: {}", filePath, e);
            throw new RuntimeException("Could not read lines from file: " + filePath, e);
        }
    }

    // ───────────────────── JSON READING ─────────────────────

    public static JSONObject readJson(String filePath) {
        String content = readText(filePath);
        try {
            return new JSONObject(content);
        } catch (Exception e) {
            logger.error("Failed to parse JSON file as JSONObject: {}", filePath, e);
            throw new RuntimeException("Invalid JSON format in file: " + filePath, e);
        }
    }

    public static JSONArray readJsonArray(String filePath) {
        String content = readText(filePath);
        try {
            return new JSONArray(content);
        } catch (Exception e) {
            logger.error("Failed to parse JSON file as JSONArray: {}", filePath, e);
            throw new RuntimeException("Invalid JSON Array format in file: " + filePath, e);
        }
    }

    // ───────────────────── CSV READING ─────────────────────

    public static List<Map<String, String>> readCsv(String filePath) {
        return readCsv(filePath, ',');
    }

    public static List<Map<String, String>> readCsv(String filePath, char delimiter) {
        List<Map<String, String>> recordsList = new ArrayList<>();
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setDelimiter(delimiter)
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, format)) {

            for (CSVRecord record : csvParser) {
                recordsList.add(record.toMap());
            }
            return recordsList;

        } catch (IOException e) {
            logger.error("Failed to read CSV file: {}", filePath, e);
            throw new RuntimeException("Could not read CSV file: " + filePath, e);
        }
    }

    public static List<CSVRecord> readCsvAsRecords(String filePath) {
        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setHeader().build())) {
            return csvParser.getRecords();
        } catch (IOException e) {
            logger.error("Failed to read CSV records from file: {}", filePath, e);
            throw new RuntimeException("Could not read CSV records: " + filePath, e);
        }
    }

    // ───────────────────── XML READING ─────────────────────

    /**
     * Reads an XML file into a DOM Document.
     */
    public static Document readXml(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Secure XML processing
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new File(filePath));
        } catch (Exception e) {
            logger.error("Failed to read XML into DOM from file: {}", filePath, e);
            throw new RuntimeException("Could not read XML file: " + filePath, e);
        }
    }

    /**
     * Reads an XML file and converts it into a Map using Jackson dataformat XML.
     */
    public static Map<String, Object> readXmlAsMap(String filePath) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(new File(filePath), new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            logger.error("Failed to read XML as Map from file: {}", filePath, e);
            throw new RuntimeException("Could not read XML as map: " + filePath, e);
        }
    }
}
