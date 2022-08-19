package com.ud.csrf.test.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "final_user", schema = "public")
public class FinalUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "auth_type", nullable = false)
    private String authType;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "cellphone", unique = true, nullable = false)
    private String cellphone;
    @ManyToOne
    @JoinColumn(name = "role", nullable = false)
    private Role role;
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
    @Column(name = "address", unique = true, nullable = false)
    private String address;
    @Column(name = "identification", unique = true, nullable = false)
    private String identification;
    @Column(name = "id_type", nullable = false)
    private String idType;
    @Column(name = "state", nullable = false)
    private String state;
    
}   
