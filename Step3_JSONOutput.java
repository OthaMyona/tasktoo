import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.json.JSONObject;

import java.io.File;
import java.util.Scanner;

public class Step3_JSONOutput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the fields you want to display (comma-separated): ");
        String[] selectedFields = scanner.nextLine().split(",");

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

                JSONObject json = new JSONObject();

                for (String field : selectedFields) {
                    String value = getText(record, field);
                    json.put(field, value);
                }

                System.out.println(json.toString(4)); // Pretty print with 4 spaces
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
