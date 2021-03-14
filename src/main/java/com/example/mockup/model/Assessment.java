package com.example.mockup.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Builder
@Data
public class Assessment implements Serializable {

    private int id;

    @NotBlank
    private String phase;

    @NotBlank
    private String status;

    @NotBlank
    private String description;
}
