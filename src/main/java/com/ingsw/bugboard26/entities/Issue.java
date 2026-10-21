package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ISSUE")
@Data
public class Issue
{
    @Id
    @Column(name = "IDISSUE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idIssue;

    @Column(name = "TITLE")
    @Basic(optional = false)
    private String title;

    @Column(name = "DESCRIPTION")
    @Basic(optional = false)
    private String description;

    @Column(name = "PRIORITY")
    private int priority;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "DUEDATE")
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "IDPROJECT", referencedColumnName = "idProject")
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Project project;

    @ManyToOne
    @JoinColumn(name = "MEMBERUSERNAME", referencedColumnName = "username")
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private TeamMember teamMember;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "issue", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private List<IssueTag> tags = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "issue", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private List<IssueLog> issueLogs = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "issue", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private List<IssueComment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "issue", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private List<IssueImage> images = new ArrayList<>();
}
