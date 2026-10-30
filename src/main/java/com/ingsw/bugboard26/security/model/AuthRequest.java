package com.ingsw.bugboard26.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest
{
    String username;
    String password;
}
