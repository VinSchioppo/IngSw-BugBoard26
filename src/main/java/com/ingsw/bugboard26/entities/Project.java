package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PROJECT")
@Data
public class Project
{
    @Id
    @Column(name = "IDPROJECT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProject;

    @Column(name = "TITLE")
    @Basic(optional = false)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "project", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private List<Issue> issues = new ArrayList<>();
}
