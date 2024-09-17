package com.xmlmanagement.service;

import com.xmlmanagement.model.Statistics;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class CalculateStatistics {

    public Statistics calculateStatistics(String xmlFilePath) throws IOException, XMLStreamException {
        Statistics statistics = new Statistics();
        statistics.setCreationDateTime(LocalDateTime.now());
        statistics.setApplicationClassName(this.getClass().getSimpleName());

        XMLInputFactory factory = XMLInputFactory.newInstance();
        try (FileInputStream fileInputStream = new FileInputStream(xmlFilePath)) {
            XMLStreamReader reader = factory.createXMLStreamReader(fileInputStream);

            int paragraphCount = 0;
            int lineCount = 0;
            int wordCount = 0;
            Set<String> distinctWords = new HashSet<>();

            StringBuilder lineText = new StringBuilder();
            boolean inParagraph = false;

            while (reader.hasNext()) {
                int event = reader.next();

                if (event == XMLStreamConstants.START_ELEMENT) {
                    String localName = reader.getLocalName();
                    if ("paragraph".equals(localName)) {
                        inParagraph = true;
                        paragraphCount++;
                    } else if (inParagraph && "line".equals(localName)) {
                        lineCount++;
                        lineText.setLength(0);
                    }
                } else if (event == XMLStreamConstants.CHARACTERS && inParagraph) {
                    lineText.append(reader.getText().trim()).append(" ");
                } else if (event == XMLStreamConstants.END_ELEMENT) {
                    String localName = reader.getLocalName();
                    if ("line".equals(localName)) {
                        String line = lineText.toString().trim();
                        if (!line.isEmpty()) {
                            String[] words = line.split("\\s+");
                            wordCount += words.length;
                            for (String word : words) {
                                distinctWords.add(word.toLowerCase());
                            }
                        }
                    } else if ("paragraph".equals(localName)) {
                        inParagraph = false;
                    }
                }
            }

            statistics.setParagraphCount(paragraphCount);
            statistics.setLineCount(lineCount);
            statistics.setWordCount(wordCount);
            statistics.setDistinctWordCount(distinctWords.size());

        } catch (FileNotFoundException e) {
            throw new IOException("File not found: " + xmlFilePath, e);
        } catch (XMLStreamException e) {
            throw new XMLStreamException("Error reading XML file: " + xmlFilePath, e);
        }

        return statistics;
    }
}
