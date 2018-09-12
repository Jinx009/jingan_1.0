package com.protops.gateway.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {

    public static String toXMLWithCData(Object obj) {

        XStream xstream = new XStream(new DomDriver("UTF-8") {
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out, new XmlFriendlyNameCoder("-_", "_")) {
                    boolean cdata = false;

                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                        cdata = (name.equals("return_code") || name.equals("return_msg"));
                    }
                    protected void writeText(QuickWriter writer, String text) {
                        if (cdata) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    }
                };
            }
        });

        xstream.autodetectAnnotations(true);

        return xstream.toXML(obj);

    }

    public static String toXML(Object obj) {

        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        xStreamForRequestPostData.autodetectAnnotations(true);

        return xStreamForRequestPostData.toXML(obj);

    }

    public static Object fromXML(String xml, Class clazz) {

        XStream xStreamForResponsePostData = new XStream();

        xStreamForResponsePostData.alias("xml", clazz);

        xStreamForResponsePostData.autodetectAnnotations(true);

        return xStreamForResponsePostData.fromXML(xml);
    }


    public static Map<String, Object> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is = getStringStream(xmlString);
        Document document = builder.parse(is);

        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, Object> map = new HashMap<String, Object>();
        int i = 0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if (node instanceof Element) {
                map.put(node.getNodeName(), node.getTextContent());
            }
            i++;
        }
        return map;

    }

    public static InputStream getStringStream(String sInputString) throws UnsupportedEncodingException {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !sInputString.trim().equals("")) {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes("UTF-8"));
        }
        return tInputStringStream;
    }

}