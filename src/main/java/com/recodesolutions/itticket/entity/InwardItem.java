package com.recodesolutions.itticket.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Audited
@Table(name = "inward_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InwardItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "serial_no")
    private String serialNo;

    @ManyToOne
    @JoinColumn(name = "inward_id")
    private Inward inward;

    @Audited
    @ManyToOne
    @JoinColumn(name = "accessory_id")
    private AccessoryType accessory;

    @Column(name = "accessory_id",insertable = false,updatable = false)
    private Long accessoryId;

    @Column(name = "qty")
    private Long qty;

    @Column(name = "others_name")
    private String othersName;

}