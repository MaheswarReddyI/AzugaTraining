package week2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//create ConvertJsonToXML class to convert JSON data into XML
public class JsonToXml {
    static int k = 0;

    private static final Logger logger = LogManager.getLogger(JsonToXml.class);

    public static void main(String[] args) {

        // create variable loc that store location of the Sample.json file
        String loc = "./Files/fakestore.json";

        String result;
        try {

            // read byte data from the Sample.json file and convert it into String
            result = new String(Files.readAllBytes(Paths.get(loc)));
            logger.info("Reading data from" + loc);
            //Convert JSON to XML
            result = result.substring(2, result.length() - 2);
            String[] arr = result.split("},\\{");
            // This method converts json object to xml string
            StringBuilder xml = new StringBuilder();

            //String xml = convertToXML(result.substring(1,result.length()-1), "root");
            for (int i = 0; i < 6; i++, k++) {
                String xmlVal = convertToXML(arr[i], "root");
                xml.append(xmlVal.replaceAll("><", ">\n<"));
                logger.debug("XML Object "+(k+1)+ xmlVal);
            }
            xml.append("\n</roots>");
            FileWriter file = new FileWriter("./Files/jtox.xml");

            // use write() method of File to write XML data into XMLData.txt
            String xm = new String(xml);
            file.write(xm);
            file.flush();
            logger.info("./Files/jtox.xml is successfully created");

            // close FileWriter
            file.close();

        } catch (IOException e1) {
            logger.error("Error in reading ./Files/fakestore.json or writing ./Files/jtox.xml", e1);
        }
    }

    // create convertToXML() method for converting JSOn data into XML
    public static String convertToXML(String jsonString, String root) {    // handle JSONException

        // create instance of JSONObject by passing jsonString to the constructor
        if ((k == 0)) {
            logger.info("convertToXML method invoked");
        }
        jsonString = "{" + jsonString + "}";
        JSONObject jsonObject = new JSONObject(jsonString);
        logger.debug("String Object " + (k + 1) + ":  " + jsonObject);


        // use XML.toString() method to convert JSON into XML and store the result into a string
        if ((k == 0)) {
            return "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<roots>\n\t<" + root + ">" + XML.toString(jsonObject) + "\n\t</" + root + ">\n";
        } else {

            return "\n\t<root>" + XML.toString(jsonObject) + "\n\t</root>";
        }
        // pass the XML data to the main() method
    }
}