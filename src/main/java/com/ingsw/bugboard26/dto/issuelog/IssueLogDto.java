package com.ingsw.bugboard26.dto.issuelog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueLogDto
{
    private String description;
    private LocalDate date;
}
