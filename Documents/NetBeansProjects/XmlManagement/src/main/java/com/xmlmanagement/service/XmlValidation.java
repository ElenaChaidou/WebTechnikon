package com.xmlmanagement.service;

import com.xmlmanagement.model.Book;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import org.xml.sax.SAXException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlValidation {

    public static void main(String[] args) {
        boolean isValid = xmlValidator("xml_files/book.xml", "xml_files/book.xsd", Book.class);

        if (isValid) {
            System.out.println("The XML file is valid.");
        } else {
            System.out.println("The XML file is not valid.");
        }
    }

    /**
     * Validates an XML file against an XSD schema.
     *
     * @param xmlFileName the XML file path
     * @param xsdFileName the XSD schema file path
     * @param xmlClass    the class used for JAXB context creation
     * @return true if the XML is valid, false otherwise
     */
    public static boolean xmlValidator(String xmlFileName, String xsdFileName, Class<?> xmlClass) {
        log.debug("Starting XML validation...");
        boolean isValid = false;

        try {
            // Create JAXB context for the provided class
            JAXBContext jaxbContext = JAXBContext.newInstance(xmlClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Set up the schema factory and schema
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdFileName));
            unmarshaller.setSchema(schema); // Set schema for validation

            // Unmarshal the XML file
            File xmlFile = new File(xmlFileName);
            Object object = unmarshaller.unmarshal(xmlFile);

            log.debug("XML file validated successfully: {}", object);
            isValid = true; // Validation successful
        } catch (JAXBException | SAXException e) {
            log.error("XML validation error: ", e); // Validation failed
        } catch (Exception e) {
            log.error("Unexpected error during XML validation: ", e);
        }

        log.debug("XML validation process finished.");
        return isValid;
    }
}
