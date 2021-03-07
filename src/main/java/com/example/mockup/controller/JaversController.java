package com.example.mockup.controller;

import com.example.mockup.model.Assessment;
import com.example.mockup.model.History;
import com.example.mockup.service.JaversService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@AllArgsConstructor
public class JaversController {

    private final JaversService javersService;


    @PostMapping("/api/javers/{id}/historize")
    public ResponseEntity historize(@RequestParam String id,
                                    @RequestBody Assessment assessment) {

        return ResponseEntity.accepted().body(javersService.historize(id, assessment));

    }


    @GetMapping("/api/javers/{id}/snapshot/{commit}")
    public ResponseEntity getSnapshots(@RequestParam String id,
                                     @RequestParam int commit) {

        return ResponseEntity.ok().body(javersService.getSnapshots(id, commit));

    }

    @GetMapping("/api/javers/{id}/history")
    public ResponseEntity<List<History>> getHistory(@RequestParam String id,
                                                    @RequestParam int commit) {


        return ResponseEntity.accepted().body(javersService.getHistory(id, commit));

    }
}
