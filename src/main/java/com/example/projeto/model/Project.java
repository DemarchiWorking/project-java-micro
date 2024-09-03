package com.example.projeto.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TB_PROJECT")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private BigDecimal budget;
    @Enumerated(EnumType.STRING)
    private Proceeding proceeding;
    private String category;
    private String description;
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    //private Set<Servicee> services = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Proceeding getProceeding() {
        return proceeding;
    }

    public void setProceeding(Proceeding proceeding) {
        this.proceeding = proceeding;
    }
}

