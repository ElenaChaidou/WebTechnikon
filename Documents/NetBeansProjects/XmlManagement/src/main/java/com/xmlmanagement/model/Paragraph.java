package com.xmlmanagement.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD) 
public class Paragraph implements Serializable{
    
//    @XmlElementWrapper(name = "lines")
    @XmlElement(name = "lines")
    private List<String> lines;
}
