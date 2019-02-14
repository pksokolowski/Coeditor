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
    public long id;
    public long issuerId;
    public long code;

    public Invitation(long issuerId, long code) {
        this.issuerId = issuerId;
        this.code = code;
    }
}
