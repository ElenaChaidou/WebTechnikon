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

    public static boolean xmlValidator(String xmlFileName, String xsdFileName, Class xmlClass) {
        log.debug("Starting XML validation...");
        boolean isValid = false;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(xmlClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();


            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdFileName));
            unmarshaller.setSchema(schema); 

            File xmlFile = new File(xmlFileName);
            Object object = unmarshaller.unmarshal(xmlFile);

            log.debug("XML file validated successfully: {}", object);
            isValid = true; 
        } catch (JAXBException | SAXException e) {
            log.error("XML validation error: ", e); 
        } catch (Exception e) {
            log.error("Unexpected error during XML validation: ", e);
        }

        log.debug("XML validation process finished.");
        return isValid;
    }
}
