package com.alumni.searcher.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AlumniRequest {
    @NotBlank(message = "University name is required")
    private String university;

    @NotBlank(message = "Designation is required")
    private String designation;

    private Integer passoutYear; // optional
}
