package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ingsw.bugboard26.entities.compositeId.IssueLogId;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@IdClass(IssueLogId.class)
@Table(name = "ISSUELOG")
public class IssueLog
{
    @Id
    @Column(name = "IDISSUE")
    private int idIssue;

    @Id
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DATE")
    @Basic(optional = false)
    private LocalDate date;

    @ManyToOne
    @MapsId
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "IDISSUE", referencedColumnName = "idIssue")
    @JsonBackReference
    private Issue issue;
}
