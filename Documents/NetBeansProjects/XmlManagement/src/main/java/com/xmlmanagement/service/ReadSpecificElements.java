package com.xmlmanagement.service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReadSpecificElements {

    public void extractChapters(String inputXmlFile, String outputXmlFile, int startChapter, int endChapter) throws XMLStreamException, IOException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        
        try (FileInputStream inputStream = new FileInputStream(inputXmlFile);
             FileOutputStream outputStream = new FileOutputStream(outputXmlFile)) {

            XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(outputStream, "UTF-8");

            boolean inSelectedChapter = false;
            int currentChapter = 0;

            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement("book");

            while (reader.hasNext()) {
                int event = reader.next();

                if (event == XMLStreamConstants.START_ELEMENT) {
                    String localName = reader.getLocalName();

                    if ("chapter".equals(localName)) {
                        currentChapter++;
                        if (currentChapter >= startChapter && currentChapter <= endChapter) {
                            inSelectedChapter = true;
                            writer.writeStartElement("chapter");
                        } else {
                            inSelectedChapter = false;
                        }
                    } else if (inSelectedChapter) {
                        if ("paragraph".equals(localName)) {
                            writer.writeStartElement("paragraph");
                        } else if ("line".equals(localName)) {
                            writer.writeStartElement("line");
                            writer.writeCharacters(reader.getElementText());  
                            writer.writeEndElement(); 
                            writer.writeCharacters("\n");  
                        }
                    }
                } else if (event == XMLStreamConstants.END_ELEMENT) {
                    String localName = reader.getLocalName();

                    if ("chapter".equals(localName) && inSelectedChapter) {
                        writer.writeEndElement(); 
                    } else if (inSelectedChapter && "paragraph".equals(localName)) {
                        writer.writeEndElement(); 
                    }
                }
            }

            writer.writeEndElement(); 
            writer.writeEndDocument();

            reader.close();
            writer.close();
        }
    }
}