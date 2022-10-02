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
@Table(name = "final_user", schema = "public")
public class Movements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @ManyToOne
    @JoinColumn(name = "final_user", nullable = false)
    private FinalUser finalUser;
    @Column(name = "acount_first", nullable = false)
    private String acountFirst;
    @Column(name = "acount_second", nullable = false)
    private String acountSecond;
    @Column(name = "amount", nullable = false)
    private String amount;
    @Column(name = "type_move", nullable = false)
    private String typeMove;
    @Column(name = "code", nullable = false)
    private String code;
    
}
