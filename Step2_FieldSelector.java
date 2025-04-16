import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.io.File;
import java.util.Scanner;

public class Step2_FieldSelector {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user for which fields they want
        System.out.println("Enter the fields you want to display (comma-separated): ");
        String[] selectedFields = scanner.nextLine().split(",");

        // Trim spaces
        for (int i = 0; i < selectedFields.length; i++) {
            selectedFields[i] = selectedFields[i].trim();
        }

        try {
            File xmlFile = new File("data.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            NodeList recordList = doc.getElementsByTagName("record");

            for (int i = 0; i < recordList.getLength(); i++) {
                Element record = (Element) recordList.item(i);
                System.out.println("Record " + (i + 1) + ":");

                for (String field : selectedFields) {
                    String value = getText(record, field);
                    System.out.println("  " + field + ": " + value);
                }

                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    private static String getText(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list.getLength() > 0) {
            return list.item(0).getTextContent();
        }
        return "";
    }
}
