package com.db.auth.model.entity;

import com.db.auth.assets.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "users")
@Where(clause = "deleted=false")
@Getter
@Setter
public class User extends LogicalDeletableEntity<Long> {

    @Basic
    @Column(name = "first_name")
    private String firstName;

    @Basic
    @Column(name = "last_name")
    private String lastName;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "uid")
    private String uid;

    @Column(name = "status")
    @Convert(converter = UserStatus.Converter.class)
    private UserStatus status;

}
