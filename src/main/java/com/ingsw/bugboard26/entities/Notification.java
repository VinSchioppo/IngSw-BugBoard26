package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ingsw.bugboard26.entities.compositeId.NotificationId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@IdClass(NotificationId.class)
@Table(name = "NOTIFICATION")
@Data
public class Notification
{
    @Id
    @Column(name = "ADMINUSERNAME")
    private String adminUsername;

    @Id
    @Column(name = "MEMBERUSERNAME")
    private String memberUsername;

    @Id
    @Column(name = "DESCRIPTION")
    @Basic(optional = false)
    private String description;

    @Column(name = "STATUS")
    private Boolean status;

    @ManyToOne
    @MapsId
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "ADMINUSERNAME", referencedColumnName = "username")
    @JsonBackReference
    private TeamAdmin teamAdmin;

    @ManyToOne
    @MapsId
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "MEMBERUSERNAME", referencedColumnName = "username")
    @JsonBackReference
    private TeamMember teamMember;
}
