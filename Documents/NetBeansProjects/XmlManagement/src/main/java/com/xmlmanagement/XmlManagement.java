package com.xmlmanagement;

import com.xmlmanagement.model.Statistics;
import com.xmlmanagement.service.CalculateStatistics;
import com.xmlmanagement.service.ReadSpecificElements;
import com.xmlmanagement.service.TxtToXmlConverter;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class XmlManagement {

    public static void main(String[] args) {
        
        TxtToXmlConverter converter = new TxtToXmlConverter();
        try {
            converter.convertTxtToXml("xml_files/sample-lorem-ipsum-text-file.txt", "xml_files/book.xml");
        } catch (XMLStreamException | IOException e) {
            System.err.println("Error during TXT to XML conversion: " + e.getMessage());
        }
        
                CalculateStatistics calculator = new CalculateStatistics();
        try {
            Statistics stats = calculator.calculateStatistics("xml_files/book.xml");
            System.out.println("Paragraph Count: " + stats.getParagraphCount());
            System.out.println("Line Count: " + stats.getLineCount());
            System.out.println("Word Count: " + stats.getWordCount());
            System.out.println("Distinct Word Count: " + stats.getDistinctWordCount());
            System.out.println("Creation Date & Time: " + stats.getCreationDateTime());
            System.out.println("Author's name: " + stats.getAuthorsName());
            System.out.println("Application Class Name: " + stats.getApplicationClassName());
        } catch (IOException | XMLStreamException e) {
            System.err.println("Error while calculating statistics: " + e.getMessage());
        }
        
                ReadSpecificElements extractor = new ReadSpecificElements();
        try {       
            extractor.extractChapters("xml_files/book.xml", "xml_files/book2", 3, 7);
            System.out.println("Selected chapters have been written to the new XML file.");
        } catch (XMLStreamException | IOException e) {
            System.err.println("Error during XML processing: " + e.getMessage());
        }
        
    }

}
