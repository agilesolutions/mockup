package com.example.mockup.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class Assessment {

    private int id;

    @NotBlank
    private String phase;

    @NotBlank
    private String status;

    @NotBlank
    private String description;
}
