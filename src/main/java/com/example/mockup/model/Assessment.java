package com.example.mockup.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.validation.constraints.NotBlank;


@Builder
@Data
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Assessment {


    private Long id;

    @NotBlank
    private String phase;


    private String status;

    @NotBlank
    private String description;


}
