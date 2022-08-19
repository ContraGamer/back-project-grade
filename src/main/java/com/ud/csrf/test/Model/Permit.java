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
@Table(name = "permit", schema = "public")
public class Permit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @Column(name = "state", nullable = false)
    private String state;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "icon", nullable = false)
    private String icon;
    @Column(name = "parent")
    private long parent;
    @Column(name = "url", nullable = false)
    private String url;

}
