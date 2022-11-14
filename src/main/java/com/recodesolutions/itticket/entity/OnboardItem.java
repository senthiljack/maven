package com.recodesolutions.itticket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
@Table(name = "onboard_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnboardItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "serial_no")
    private String serialNo;

    @ManyToOne
    @JoinColumn(name = "onboard_id")
    private Onboard onboard;

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