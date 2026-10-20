package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ingsw.bugboard26.entities.compositeId.IssueTagId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@IdClass(IssueTagId.class)
@Table(name = "ISSUETAG")
@Data
public class IssueTag
{
    @Id
    @Column(name = "IDISSUE")
    private int idIssue;

    @Id
    @Column(name = "NAMETAG")
    private String nameTag;

    @ManyToOne
    @MapsId
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "IDISSUE", referencedColumnName = "idIssue")
    @JsonBackReference
    private Issue issue;
}
