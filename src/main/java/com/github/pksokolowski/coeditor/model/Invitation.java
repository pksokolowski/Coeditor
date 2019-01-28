package com.github.pksokolowski.coeditor.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Entity
@NoArgsConstructor
public class Invitation {
    @Id
    @GeneratedValue(strategy = AUTO)
    public Long id;
    public Long issuerId;
    public Long code;

    public Invitation(Long id, Long issuerId, Long code) {
        this.id = id;
        this.issuerId = issuerId;
        this.code = code;
    }

    public Invitation(Long issuerId, Long code) {
        this(0L, issuerId, code);
    }
}
