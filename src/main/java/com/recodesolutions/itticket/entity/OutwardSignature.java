package com.recodesolutions.itticket.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "outward_signature")
public class OutwardSignature {

    @Id
    private Long id;

    @Column(name = "file_type")
    private String fileType;

    @Lob
    @Column(name = "receiver_signature")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] receiverSignature;

    @Lob
    @Column(name = "provider_signature")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] providerSignature;


}