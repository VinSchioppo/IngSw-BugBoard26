package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "TEAMADMIN")
@Data
public class TeamAdmin
{
    @Id
    @Column(name = "USERNAME")
    @Basic(optional = false)
    private String username;

    @OneToOne
    @MapsId
    @JoinColumn(name = "USERNAME", referencedColumnName = "username")
    @JsonBackReference
    private TeamMember account;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "teamAdmin", orphanRemoval = true)
    @JsonManagedReference
    @PrimaryKeyJoinColumn
    private List<Notification> notifications = new ArrayList<>();
}
