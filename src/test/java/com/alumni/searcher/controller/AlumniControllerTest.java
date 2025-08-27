package com.alumni.searcher.controller;

import com.alumni.searcher.dto.AlumniRequest;
import com.alumni.searcher.dto.AlumniResponse;
import com.alumni.searcher.service.AlumniService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlumniController.class)
class AlumniControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AlumniService alumniService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSearchAlumni() throws Exception {
        AlumniRequest request = new AlumniRequest();
        request.setUniversity("University of XYZ");
        request.setDesignation("Software Engineer");
        request.setPassoutYear(2020);

        AlumniResponse response = AlumniResponse.builder()
                .name("John Doe")
                .currentRole("Software Engineer")
                .university("University of XYZ")
                .location("New York, NY")
                .linkedinHeadline("Passionate Software Engineer at XYZ Corp")
                .passoutYear(2020)
                .build();

        Mockito.when(alumniService.searchAndSaveAlumni(Mockito.any(AlumniRequest.class)))
                .thenReturn(List.of(response));

        mockMvc.perform(post("/api/alumni/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data[0].name").value("John Doe"))
                .andExpect(jsonPath("$.data[0].currentRole").value("Software Engineer"));
    }

    @Test
    void testGetAllAlumni() throws Exception {
        AlumniResponse response = AlumniResponse.builder()
                .name("Jane Smith")
                .currentRole("Data Scientist")
                .university("University of XYZ")
                .location("San Francisco, CA")
                .linkedinHeadline("Data Scientist | AI Enthusiast")
                .passoutYear(2019)
                .build();

        Mockito.when(alumniService.getAllAlumni()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/alumni/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data[0].name").value("Jane Smith"))
                .andExpect(jsonPath("$.data[0].currentRole").value("Data Scientist"));
    }
}
