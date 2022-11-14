package com.recodesolutions.itticket.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "inward_audit")
public class InwardAudit {
    @EmbeddedId
    private InwardAuditId id;

    public InwardAuditId getId() {
        return id;
    }

    public void setId(InwardAuditId id) {
        this.id = id;
    }

    //TODO [JPA Buddy] generate columns from DB
}