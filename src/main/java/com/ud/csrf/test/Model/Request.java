package com.ud.csrf.test.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "request", schema = "public")
public class Request {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "origin")
    private String origin;
    @Column(name = "destiny")
    private String destiny;
    @Column(name = "status_http")
    private String statusHttp;
    @Column(name = "date_time_start")
    private Date dateTimeStart;
    @Column(name = "date_time_finish")
    private Date dateTimeFinish;
    @Column(name = "time_taken")
    private String timeTaken;
    @Column(name = "status_request")
    private String statusRequest; 
    @Column(name = "security_level")
    private String securityLevel;
    @Column(name = "data_request")
    private String dataRequest;

}
