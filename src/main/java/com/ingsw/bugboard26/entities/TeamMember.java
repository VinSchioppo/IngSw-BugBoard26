package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TEAMMEMBER")
@Data
public class TeamMember
{
    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    @Basic(optional = false)
    private String email;

    @Column(name = "PASSWORD")
    @Basic(optional = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "acount", orphanRemoval = true)
    @JsonManagedReference
    private TeamAdmin adminAcount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "teamMember", orphanRemoval = true)
    @JsonManagedReference
    private List<Issue> issues = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "teamMember", orphanRemoval = true)
    @JsonManagedReference
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "teamMember", orphanRemoval = true)
    @JsonManagedReference
    private List<IssueComment> comments = new ArrayList<>();
}
