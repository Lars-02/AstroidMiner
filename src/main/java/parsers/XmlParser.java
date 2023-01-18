package parsers;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlParser {
    public static List<Map<String, String>> parse(String fileContents, String tagName) {
        List<Map<String, String>> data = new ArrayList<>();
        try {
            var dbFactory = DocumentBuilderFactory.newInstance();
            var dBuilder = dbFactory.newDocumentBuilder();
            var doc = dBuilder.parse(new InputSource(new StringReader(fileContents)));

            doc.getDocumentElement().normalize();
            var nList = doc.getElementsByTagName(tagName);

            for (int temp = 0; temp < nList.getLength(); temp++) {
                var nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    var eElement = (Element) nNode;
                    var elementData = new HashMap<String, String>();
                    System.out.println(nNode.getNodeName());
                    var children = eElement.getChildNodes();
                    for (int i = 0; i < children.getLength(); i++) {
                        var node = children.item(i);
                        var key = node.getNodeName();
                        var value = node.getTextContent();
                        System.out.println(key);
                        elementData.put(key, value);
                    }
                    data.add(elementData);

                    break;
                }
            }

            System.out.println("=================");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
