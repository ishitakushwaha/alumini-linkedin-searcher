package com.alumni.searcher.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alumni")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alumni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String currentRole;

    private String university;

    private String location;

    private String linkedinHeadline;

    private Integer passoutYear;

}
