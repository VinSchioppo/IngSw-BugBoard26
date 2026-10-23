package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ingsw.bugboard26.entities.compositeId.IssueImageId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@IdClass(IssueImageId.class)
@Table(name = "ISSUEIMAGE")
@Data
public class IssueImage
{
    @Id
    @ManyToOne
    @MapsId
    @JoinColumn(name = "IDISSUE", referencedColumnName = "idIssue")
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Issue issue;

    @Id
    @Column(name = "IMAGENAME")
    private String name;

    @Column(name = "IMAGE")
    @Basic(optional = false)
    private byte[] image;
}
