package com.ingsw.bugboard26.dto.issueimage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueImageDto
{
    private String name;
    private byte[] image;
}
