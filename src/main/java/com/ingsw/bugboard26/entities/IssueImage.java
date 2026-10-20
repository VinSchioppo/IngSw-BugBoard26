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
    @Column(name = "IDISSUE")
    private int idIssue;

    @Id
    @Column(name = "IMAGENAME")
    private String name;

    @Column(name = "IMAGE")
    @Basic(optional = false)
    private byte[] image;

    @ManyToOne
    @MapsId
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "IDISSUE", referencedColumnName = "idIssue")
    @JsonBackReference
    private Issue issue;
}
