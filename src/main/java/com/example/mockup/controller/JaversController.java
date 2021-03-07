package com.example.mockup.controller;

import com.example.mockup.model.Assessment;
import com.example.mockup.model.History;
import com.example.mockup.model.Snapshots;
import org.javers.core.Javers;
import org.javers.core.commit.CommitId;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class JaversController {

    @Autowired
    Javers javers;


    @PostMapping("/api/javers/{id}/historize")
    public ResponseEntity historize(@RequestParam String id,
                                    @RequestBody Assessment assessment) {

        javers.commit("assessment", assessment,
                Map.of("phase", assessment.getPhase(), "status", assessment.getStatus()));

        return ResponseEntity.accepted().body(assessment);

    }


    @GetMapping("/api/javers/{id}/snapshot/{commit}")
    public ResponseEntity getSnapshots(@RequestParam String id,
                                     @RequestParam int commit) {

        Snapshots snapshots = null;

        List<Shadow<Assessment>> shadows = javers.findShadows(QueryBuilder
                .byInstanceId("assessment", Assessment.class)
                .withScopeCommitDeep()
                .withScopeDeepPlus()
                .build());

        if (shadows.size() == 0) {
            return ResponseEntity.badRequest().body("no records found");
        }

        if (commit == 1) {
            snapshots = Snapshots.builder()
                    .oldInstance(shadows.get(commit).get())
                    .newInstance(shadows.get(commit).get())
                    .build();

        } else if (commit > shadows.size()){
            return ResponseEntity.badRequest().body("out of range");
        } else {
            snapshots = Snapshots.builder()
                    .oldInstance(shadows.get(commit - 1).get())
                    .newInstance(shadows.get(commit).get())
                    .build();
        }



        return ResponseEntity.ok().body(snapshots);

    }

    @GetMapping("/api/javers/{id}/history")
    public ResponseEntity<List<History>> getHistory(@RequestParam String id,
                                                    @RequestParam int commit) {

        List<CdoSnapshot> snapshots = javers.findSnapshots(QueryBuilder
                .byInstanceId("assessment", Assessment.class)
                .withScopeCommitDeep()
                .withScopeDeepPlus()
                .build());

        List<History> histories = snapshots.stream()
                .map(c -> {return History.builder()
                        .author(c.getCommitMetadata().getAuthor())
                        .commitDateInstant(c.getCommitMetadata().getCommitDateInstant())
                        .id(c.getCommitId())
                        .phase(String.valueOf(c.getPropertyValue("phase")))
                        .status(String.valueOf(c.getPropertyValue("status")))
                        .build();

                }).collect(Collectors.toList());


        return ResponseEntity.accepted().body(histories);

    }
}
