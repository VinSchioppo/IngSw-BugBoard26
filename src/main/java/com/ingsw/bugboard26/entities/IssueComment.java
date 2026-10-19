package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Table(name = "ISSUECOMMENT")
@Data
public class IssueComment
{
    @Id
    @Column(name = "MEMBERUSERNAME")
    @Basic(optional = false)
    private String memberUsername;

    @Id
    @Column(name = "IDISSUE")
    @Basic(optional = false)
    private int idIssue;

    @Id
    @Column(name = "DESCRIPTION")
    @Basic(optional = false)
    private String description;

    @Column(name = "DATE")
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "USERNAME", referencedColumnName = "username")
    @JsonBackReference
    private TeamMember teamMember;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "IDISSUE", referencedColumnName = "idIssue")
    @JsonBackReference
    private Issue issue;
}
