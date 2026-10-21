package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ingsw.bugboard26.entities.compositeId.IssueCommentId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@IdClass(IssueCommentId.class)
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
    private LocalDate date;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "MEMBERUSERNAME", referencedColumnName = "username")
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private TeamMember teamMember;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "IDISSUE", referencedColumnName = "idIssue")
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Issue issue;
}
