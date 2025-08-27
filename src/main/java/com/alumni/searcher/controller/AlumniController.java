package com.alumni.searcher.controller;

import com.alumni.searcher.dto.AlumniRequest;
import com.alumni.searcher.dto.AlumniResponse;
import com.alumni.searcher.service.AlumniService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alumni")
@RequiredArgsConstructor
public class AlumniController {

    private final AlumniService alumniService;

    // Search + Save
    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> searchAlumni(@Valid @RequestBody AlumniRequest request) {
        List<AlumniResponse> data = alumniService.searchAndSaveAlumni(request);
        return ResponseEntity.ok(Map.of("status", "success", "data", data));
    }

    // Get Saved Alumni
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllAlumni() {
        List<AlumniResponse> data = alumniService.getAllAlumni();
        return ResponseEntity.ok(Map.of("status", "success", "data", data));
    }

    // Get Alumni by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAlumniById(@PathVariable Long id) {
        AlumniResponse data = alumniService.getAlumniById(id);
        return ResponseEntity.ok(Map.of("status", "success", "data", data));
    }

}
