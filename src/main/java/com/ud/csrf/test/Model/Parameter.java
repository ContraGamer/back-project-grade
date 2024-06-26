package com.ud.csrf.test.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "parameter", schema = "public")
public class Parameter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @Column(name = "key", unique = true, nullable = false)
    private String key;
    @Column(name = "value", nullable = false)
    private String value;
    @Column(name = "state", nullable = false)
    private String state;
    @Column(name = "of")
    private String of;
    @Column(name = "additional")
    private String additional;

}
