package com.ud.csrf.test.Model;

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
@Table(name = "permit_role", schema = "public")
public class PermitRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @ManyToOne
    @JoinColumn(name = "permit", nullable = false)
    private Permit permit;
    @ManyToOne
    @JoinColumn(name = "role", nullable = false)
    private Role role;
    
}
