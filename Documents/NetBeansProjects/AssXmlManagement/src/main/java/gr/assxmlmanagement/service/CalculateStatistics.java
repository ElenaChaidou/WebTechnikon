package gr.assxmlmanagement.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

public class CalculateStatistics {

    public void printXmlByXmlCursorReader(String xmlInputFile) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory xmlInput = XMLInputFactory.newInstance();
        FileInputStream inputStream = null;
        XMLStreamReader reader = null;

        try {
            inputStream = new FileInputStream(xmlInputFile);
            reader = xmlInput.createXMLStreamReader(inputStream);

            while (reader.hasNext()) {
                int eventType = reader.next();
                if (eventType == XMLEvent.START_ELEMENT) {
                    String localPart = reader.getName().getLocalPart();
                    switch (localPart) {
                        case "chapter":
                            System.out.println("-This chapter contains the following paragraphs: ");
                            break;
                        
                        case "paragraph":
                            System.out.println("--The paragraph has the following lines: ");
                            break;
                        
                        case "lines":
                            System.out.printf("Lines: %s%n", reader.getElementText());
                            break;
                    }
                } else if (eventType == XMLEvent.END_ELEMENT) {
                    if (reader.getName().getLocalPart().equals("chapter")) {
                        System.out.println("---");
                    }
                }
            }
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

