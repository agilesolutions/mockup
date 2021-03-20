package com.example.mockup.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
public class Assessment {

    private int id;

    @NotBlank
    private String phase;

    @NotBlank
    private String status;

    @NotBlank
    private String description;
}
