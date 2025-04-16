import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONObject;

import java.io.File;
import java.util.*;

public class Step5_SAXParser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> validFields = Arrays.asList(
                "name", "postalZip", "region", "country", "address", "list"
        );

        System.out.println("Enter the fields you want to display (comma-separated): ");
        String[] inputFields = scanner.nextLine().split(",");

        List<String> selectedFields = new ArrayList<>();
        for (String field : inputFields) {
            field = field.trim();
            if (validFields.contains(field)) {
                selectedFields.add(field);
            } else {
                System.out.println("⚠️ Warning: '" + field + "' is not a valid field and will be skipped.");
            }
        }

        if (selectedFields.isEmpty()) {
            System.out.println("No valid fields selected. Exiting.");
            return;
        }

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            saxParser.parse(new File("data.xml"), new DefaultHandler() {
                JSONObject currentRecord = null;
                String currentElement = "";
                StringBuilder currentText = new StringBuilder();
                boolean insideRecord = false;

                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.equals("record")) {
                        currentRecord = new JSONObject();
                        insideRecord = true;
                    }
                    currentElement = qName;
                    currentText.setLength(0); // reset buffer
                }

                public void characters(char[] ch, int start, int length) {
                    currentText.append(ch, start, length);
                }

                public void endElement(String uri, String localName, String qName) {
                    if (insideRecord && selectedFields.contains(qName)) {
                        currentRecord.put(qName, currentText.toString().trim());
                    }

                    if (qName.equals("record")) {
                        System.out.println(currentRecord.toString(4));
                        currentRecord = null;
                        insideRecord = false;
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}
