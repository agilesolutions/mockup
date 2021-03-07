package com.example.mockup.model;

import lombok.Builder;
import lombok.Data;
import org.javers.core.commit.CommitId;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
public class History {

    private String author;
    private Instant commitDateInstant;
    private CommitId id;
    private String phase;
    private String status;

}
