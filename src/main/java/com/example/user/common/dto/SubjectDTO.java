package com.example.user.common.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectDTO {
    @Parameter(description = "과목명")
    private String subject;
}
