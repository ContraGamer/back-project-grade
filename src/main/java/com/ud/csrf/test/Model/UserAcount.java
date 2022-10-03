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
@Table(name = "users_acount", schema = "public")
public class UserAcount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @ManyToOne
    @JoinColumn(name = "final_user", nullable = false)
    private FinalUser finalUser;
    @ManyToOne
    @JoinColumn(name = "acount", nullable = false)
    private FinalUser acount;
    @Column(name = "state", nullable = false)
    private String state;

}
