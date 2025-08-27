package com.alumni.searcher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ApiErrorResponse {
    private String status;
    private String message;
    private LocalDateTime timestamp;
    private String path;   // request URI
}
