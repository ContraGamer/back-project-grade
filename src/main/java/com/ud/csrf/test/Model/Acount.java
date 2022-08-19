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
@Table(name = "acount", schema = "public")
public class Acount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "amount", nullable = false)
    private String amount;
    @Column(name = "number", nullable = false, unique = true)
    private String number;
    @ManyToOne
    @JoinColumn(name = "type_acount", nullable = false)
    private TypeAcount typeAcount;
    
}
