import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.io.File;

public class Step1_ReadXML {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("data.xml"); // Make sure this is in the same folder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            NodeList recordList = doc.getElementsByTagName("record");

            for (int i = 0; i < recordList.getLength(); i++) {
                Element record = (Element) recordList.item(i);

                String name = getText(record, "name");
                String postalZip = getText(record, "postalZip");
                String region = getText(record, "region");
                String country = getText(record, "country");
                String address = getText(record, "address");
                String list = getText(record, "list");

                System.out.println("Record " + (i + 1) + ":");
                System.out.println("  Name: " + name);
                System.out.println("  Postal/Zip: " + postalZip);
                System.out.println("  Region: " + region);
                System.out.println("  Country: " + country);
                System.out.println("  Address: " + address);
                System.out.println("  List: " + list);
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getText(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list.getLength() > 0) {
            return list.item(0).getTextContent();
        }
        return "";
    }
}
