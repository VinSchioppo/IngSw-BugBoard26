package com.ingsw.bugboard26.dto.issuelog;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IssueLogDto
{
    private String description;
    private LocalDate date;
}
