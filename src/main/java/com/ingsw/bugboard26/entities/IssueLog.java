package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ingsw.bugboard26.entities.compositeId.IssueLogId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@IdClass(IssueLogId.class)
@Table(name = "ISSUELOG")
@Data
public class IssueLog
{
    @Id
    @ManyToOne
    @MapsId
    @JoinColumn(name = "IDISSUE", referencedColumnName = "idIssue")
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Issue issue;

    @Id
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DATE")
    @Basic(optional = false)
    private LocalDate date;
}
