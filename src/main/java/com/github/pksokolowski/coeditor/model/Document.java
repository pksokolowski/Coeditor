package com.github.pksokolowski.coeditor.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String title;
    public String content;
    public Long lastModified;
    public Long createdBy;

    public Document(String title, String content, Long lastModified, Long createdBy) {
        this.title = title;
        this.content = content;
        this.lastModified = lastModified;
        this.createdBy = createdBy;
    }
}
