package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "NOTIFICATION")
@Data
public class Notification
{
    @Id
    @Column(name = "DESCRIPTION")
    @Basic(optional = false)
    private String description;

    @Column(name = "STATUS")
    private Boolean status;

    @Id
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "ADMINUSERNAME", referencedColumnName = "username")
    @JsonBackReference
    private TeamAdmin teamAdmin;

    @Id
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "MEMBERUSERNAME", referencedColumnName = "username")
    @JsonBackReference
    private TeamMember teamMember;
}
