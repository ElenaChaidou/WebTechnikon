package com.xmlmanagement.model;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Line implements Serializable{
    @XmlElement(name = "line")
    private String word;
}