package com.example.test_servicebank.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Slf4j
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value_max")
    private Integer valueMax;

    @NotNull
    @Column(name = "percentage")
    private Integer percentageGrowth;

    @Column(name = "period")
    private Integer period;

    @Column(name = "reg_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registeredDate;

    @Column(name = "update_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    @Column(name = "removed")
    private boolean removed;

    @PrePersist
    public void prePersist() {
        this.setRegisteredDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
        this.removed = false;
    }

    @PreUpdate
    public void preUpdate() {
        log.info("The date of changes of Product was updated!_*_!");
        this.setUpdateDate(LocalDateTime.now());
    }


}
