package com.xmlmanagement.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class TxtToXmlConverter {

    private XMLStreamWriter writer;
    private XMLOutputFactory output;
    private BufferedReader inputFile;

    public void convertTxtToXml(String inputTxtFile, String outputXmlFile) throws XMLStreamException, IOException {
        output = XMLOutputFactory.newInstance();
        FileOutputStream outXML;

        try {
            outXML = new FileOutputStream(outputXmlFile);
            inputFile = new BufferedReader(new FileReader(inputTxtFile));
            writer = output.createXMLStreamWriter(outXML, "UTF-16");
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
            return;
        }

        writer.writeStartDocument("UTF-16", "1.0");
        writer.writeCharacters("\n");
        writer.writeStartElement("book");
        writer.writeCharacters("\n");

        String line;
        int paragraphCounter = 0;

        try {
            while ((line = inputFile.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                paragraphCounter++;

                if (paragraphCounter == 1) {
                    writer.writeStartElement("chapter");
                    writer.writeCharacters("\n");
                }

                writeParagraph(line.split("\\. "));

                if (paragraphCounter >= 20) {
                    writer.writeEndElement();
                    writer.writeCharacters("\n");
                    paragraphCounter = 0;
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        if (paragraphCounter != 0) {
            writer.writeEndElement();
            writer.writeCharacters("\n");
        }

        writer.writeEndElement();
        writer.writeEndDocument();
        inputFile.close();
        writer.close();
    }

    private void writeParagraph(String[] lines) throws XMLStreamException {
        writer.writeStartElement("paragraph");
        writer.writeCharacters("\n");

        writer.writeStartElement("lines");
        writer.writeCharacters("\n");

        for (String line : lines) {
            writer.writeStartElement("line");
            writer.writeCharacters(line.trim());
            writer.writeEndElement();
            writer.writeCharacters("\n");
        }

        writer.writeEndElement();
        writer.writeCharacters("\n");
        writer.writeEndElement();
        writer.writeCharacters("\n");
    }

}
