package gr.assxmlmanagement.model;

import jakarta.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paragraph implements Serializable{
    
    @XmlElement(name = "line")
    private List<String> lines;
}

