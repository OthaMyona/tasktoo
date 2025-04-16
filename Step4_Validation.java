import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.json.JSONObject;

import java.io.File;
import java.util.*;

public class Step4_Validation {
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
                System.out.println("⚠️  Warning: '" + field + "' is not a valid field and will be skipped.");
            }
        }

        if (selectedFields.isEmpty()) {
            System.out.println("No valid fields selected. Exiting.");
            return;
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

                System.out.println(json.toString(4));
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
