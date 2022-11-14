package com.recodesolutions.itticket.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Audited
@Table(name = "outward")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Outward extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "display_id")
    private String displayId;

    @Column(name = "provider_id")
    private Long providerId;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "receiver_name")
    private String receiverName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "outward_id")
    private Set<OutwardItem> outwardItems = new LinkedHashSet<>();

    @Column(name = "others")
    private Boolean others=false;

    @Column(name = "name")
    private String name;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "issued_date")
    private LocalDateTime issuedDate;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "created_by",insertable = false,updatable = false)
    private User createdByUser;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "last_updated_by",insertable = false,updatable = false)
    private User lastUpdatedByUser;

    @Transient
    private String stringIssuedDate;

    @OneToOne(cascade = CascadeType.ALL)
    @NotAudited
    @JoinColumn(name = "id", referencedColumnName = "id")
    private OutwardSignature signature;

    @Transient
    private String receiverSignature;

    @Transient
    private String providerSignature;
}