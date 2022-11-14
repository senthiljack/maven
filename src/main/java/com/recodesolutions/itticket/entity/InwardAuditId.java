package com.recodesolutions.itticket.entity;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InwardAuditId implements Serializable {
    private static final long serialVersionUID = -6063780931782443275L;
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "revision_id", nullable = false)
    private Integer revisionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(Integer revisionId) {
        this.revisionId = revisionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InwardAuditId entity = (InwardAuditId) o;
        return Objects.equals(this.revisionId, entity.revisionId) &&
                Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(revisionId, id);
    }

}