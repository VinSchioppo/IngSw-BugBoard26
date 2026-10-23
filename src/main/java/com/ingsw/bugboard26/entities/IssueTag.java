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
    @ManyToOne
    @MapsId
    @JoinColumn(name = "IDISSUE", referencedColumnName = "idIssue")
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Issue issue;

    @Id
    @Column(name = "NAMETAG")
    private String nameTag;
}
