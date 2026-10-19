package com.ingsw.bugboard26.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "TEAMADMIN")
public class TeamAdmin
{
    @Id
    @Column(name = "USERNAME")
    @Basic(optional = false)
    private String username;

    @OneToOne
    @JoinColumn(name = "USERNAME", referencedColumnName = "username")
    @JsonBackReference
    private TeamMember acount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "teamAdmin", orphanRemoval = true)
    @JsonManagedReference
    private List<Notification> notifications = new ArrayList<>();
}
