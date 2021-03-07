package com.example.mockup.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Assessment {

    private int id;

    private String phase;

    private String status;

    private String description;
}
