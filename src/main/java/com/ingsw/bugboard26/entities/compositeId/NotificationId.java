package com.ingsw.bugboard26.entities.compositeId;

import lombok.Data;

import java.io.Serializable;

@Data
public class NotificationId implements Serializable
{
    private String adminUsername;

    private String memberUsername;

    private String description;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
