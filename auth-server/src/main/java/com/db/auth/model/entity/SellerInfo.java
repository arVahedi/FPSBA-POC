package com.db.auth.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "seller_info")
@Getter
@Setter
public class SellerInfo extends BaseEntity<Long> {

    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "postal_code")
    private String postalCode;
    @Basic
    @Column(name = "iban")
    private String iban;
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
