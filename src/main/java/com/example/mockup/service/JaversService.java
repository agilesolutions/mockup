package com.example.mockup.service;

import com.example.mockup.model.Assessment;
import com.example.mockup.model.History;
import com.example.mockup.model.Snapshots;
import lombok.AllArgsConstructor;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class JaversService {

    private final Javers javers;

    public Assessment historize(@RequestParam String id,
                                @RequestBody Assessment assessment) {

        javers.commit("assessment", assessment,
                Map.of("phase", assessment.getPhase(), "status", assessment.getStatus()));

        return assessment;

    }

    public Snapshots getSnapshots(@RequestParam String id,
                                       @RequestParam int commit) {

        Snapshots snapshots = null;

        List<Shadow<Assessment>> shadows = javers.findShadows(QueryBuilder
                .byInstanceId("assessment", Assessment.class)
                .withScopeCommitDeep()
                .withScopeDeepPlus()
                .build());

        if (shadows.size() == 0) {
            throw new IllegalArgumentException("invalid size");
        }

        if (commit == 1) {
            snapshots = Snapshots.builder()
                    .oldInstance(shadows.get(commit).get())
                    .newInstance(shadows.get(commit).get())
                    .build();

        } else if (commit > shadows.size()){
            throw new IllegalArgumentException("index out of range");
        } else {
            snapshots = Snapshots.builder()
                    .oldInstance(shadows.get(commit - 1).get())
                    .newInstance(shadows.get(commit).get())
                    .build();
        }



        return snapshots;

    }

    public List<History> getHistory(@RequestParam String id,
                                    @RequestParam int commit) {

        List<CdoSnapshot> snapshots = javers.findSnapshots(QueryBuilder
                .byInstanceId("assessment", Assessment.class)
                .withScopeCommitDeep()
                .withScopeDeepPlus()
                .build());

        List<History> histories = snapshots.stream()
                .map(c -> {
                    return History.builder()
                            .author(c.getCommitMetadata().getAuthor())
                            .commitDateInstant(c.getCommitMetadata().getCommitDateInstant())
                            .id(c.getCommitId())
                            .phase(String.valueOf(c.getPropertyValue("phase")))
                            .status(String.valueOf(c.getPropertyValue("status")))
                            .build();

                }).collect(Collectors.toList());

        return histories;
    }
}
