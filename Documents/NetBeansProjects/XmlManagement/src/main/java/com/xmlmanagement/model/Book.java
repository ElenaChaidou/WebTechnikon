package com.xmlmanagement.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "book")
@XmlType(name = "", propOrder = {
    "title",
    "author",
    "chapters",
    "statistics"
}) 
public class Book implements Serializable
{
    private String title;
    private String author;
    
    @XmlElement(name = "chapter")
    private List<Chapter> chapters;

    @XmlElement(name = "statistics")
    private Statistics statistics;
}