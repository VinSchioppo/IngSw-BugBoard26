package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ISSUEIMAGE")
@Data
public class IssueImage
{
    @Id
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "IDISSUE", referencedColumnName = "idIssue")
    @JsonBackReference
    private Issue issue;

    @Id
    @Column(name = "IMAGENAME")
    private String name;

    @Column(name = "IMAGE")
    @Basic(optional = false)
    private byte[] image;
}
