package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Table(name = "ISSUELOG")
public class IssueLog
{
    @Id
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "IDISSUE", referencedColumnName = "idIssue")
    @JsonBackReference
    private Issue issue;

    @Id
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DATE")
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;
}
