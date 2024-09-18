package com.xmlmanagement.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Statistics implements Serializable{
    private int paragraphCount;
    private int lineCount;
    private int wordCount;
    private int distinctWordCount;
    private LocalDateTime creationDateTime;
    private String authorsName; 
    private String applicationClassName;
    
}