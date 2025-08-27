package com.alumni.searcher.service;

import com.alumni.searcher.dto.AlumniRequest;
import com.alumni.searcher.dto.AlumniResponse;
import com.alumni.searcher.dto.dummyjson.DummyUserResponse;
import com.alumni.searcher.model.Alumni;
import com.alumni.searcher.repository.AlumniRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.alumni.searcher.exception.AlumniNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlumniService {

    private final AlumniRepository alumniRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public List<AlumniResponse> searchAndSaveAlumni(AlumniRequest request) {
        String url = "https://dummyjson.com/users?limit=10";

        ResponseEntity<DummyUserResponse> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});

        DummyUserResponse response = responseEntity.getBody();

        if (response == null || response.users() == null) {
            throw new RuntimeException("Failed to fetch users from DummyJSON API");
        }

        List<Alumni> results = response.users().stream().map(u ->
                Alumni.builder()
                        .name(u.firstName() + " " + u.lastName())
                        .currentRole(request.getDesignation())
                        .university(request.getUniversity())
                        .location(u.address() != null ? u.address().city() : "Unknown")
                        .linkedinHeadline(u.company() != null ? u.company().title() : "N/A")
                        .passoutYear(request.getPassoutYear() != null ? request.getPassoutYear() : 2020)
                        .build()
        ).collect(Collectors.toList());

        alumniRepository.saveAll(results);

        return results.stream()
                .map(a -> AlumniResponse.builder()
                        .name(a.getName())
                        .currentRole(a.getCurrentRole())
                        .university(a.getUniversity())
                        .location(a.getLocation())
                        .linkedinHeadline(a.getLinkedinHeadline())
                        .passoutYear(a.getPassoutYear())
                        .build())
                .collect(Collectors.toList());
    }

    public List<AlumniResponse> getAllAlumni() {
        return alumniRepository.findAll().stream()
                .map(a -> AlumniResponse.builder()
                        .name(a.getName())
                        .currentRole(a.getCurrentRole())
                        .university(a.getUniversity())
                        .location(a.getLocation())
                        .linkedinHeadline(a.getLinkedinHeadline())
                        .passoutYear(a.getPassoutYear())
                        .build())
                .collect(Collectors.toList());
    }
    public AlumniResponse getAlumniById(Long id) {
        Alumni alumni = alumniRepository.findById(id)
                .orElseThrow(() -> new AlumniNotFoundException("Alumni with id " + id + " not found"));

        return AlumniResponse.builder()
                .name(alumni.getName())
                .currentRole(alumni.getCurrentRole())
                .university(alumni.getUniversity())
                .location(alumni.getLocation())
                .linkedinHeadline(alumni.getLinkedinHeadline())
                .passoutYear(alumni.getPassoutYear())
                .build();
    }

}
