package com.lethabo.jobtracker;

import com.lethabo.jobtracker.controller.ApplicationController;
import com.lethabo.jobtracker.model.Application;
import com.lethabo.jobtracker.model.ApplicationStatus;
import com.lethabo.jobtracker.service.ApplicationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

@WebMvcTest(ApplicationController.class)
class ApplicationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApplicationService applicationService;

    @Test
    void getApplications_returnsAllApplications() throws Exception {
        Application application1 = new Application();
        application1.setId(1L);
        application1.setCompany("Google");

        Application application2 = new Application();
        application2.setId(2L);
        application2.setCompany("Microsoft");
        List<Application> applications = List.of(application1, application2);

        when(applicationService.getApplications(null))
                .thenReturn(applications);

        mockMvc.perform(get("/applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[0].company").value("Google"))
                .andExpect(jsonPath("$[1].company").value("Microsoft"));
    }

    @Test
    void getApplications_whenStatusIsProvided_returnsMatchingApplications() throws Exception {
        Application application = new Application();
        application.setId(1L);
        application.setCompany("Microsoft");
        application.setStatus(ApplicationStatus.INTERVIEW);
        List<Application> matchingApplications = List.of(application);

        when(applicationService.getApplications(ApplicationStatus.INTERVIEW))
                .thenReturn(matchingApplications);

        mockMvc.perform(get("/applications")
                        .param("status", "INTERVIEW"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].company").value("Microsoft"))
                .andExpect(jsonPath("$[0].status").value("INTERVIEW"));
    }

    @Test
    void getApplications_whenStatusIsInvalid_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/applications")
                        .param("status", "HELLO"))
                .andExpect(status().isBadRequest());

        verify(applicationService, never()).getApplications(any(ApplicationStatus.class));
    }

    @Test
    void getApplication_whenApplicationExists_returnsApplication() throws Exception {
        Application application = new Application();
        application.setId(1L);
        application.setCompany("Google");
        application.setPosition("Junior Developer");
        application.setStatus(ApplicationStatus.APPLIED);

        when(applicationService.getApplication(1L))
                .thenReturn(application);

        mockMvc.perform(get("/applications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.company").value("Google"))
                .andExpect(jsonPath("$.position").value("Junior Developer"))
                .andExpect(jsonPath("$.status").value("APPLIED"));
    }

    @Test
    void getApplication_whenApplicationDoesntExist_returnsNotFound() throws Exception {
        when(applicationService.getApplication(999L))
                .thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/applications/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createApplication_returnsCreated() throws Exception {
        Application application = new Application();
        application.setId(1L);
        application.setCompany("Google");
        application.setPosition("Junior Developer");
        application.setStatus(ApplicationStatus.APPLIED);

        when(applicationService.createApplication(any(Application.class)))
                .thenReturn(application);

        mockMvc.perform(post("/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "company": "Google",
                            "position": "Junior Developer",
                            "status": "APPLIED"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.company").value("Google"))
                .andExpect(jsonPath("$.position").value("Junior Developer"))
                .andExpect(jsonPath("$.status").value("APPLIED"));
    }

    @Test
    void createApplication_whenApplicationIsInvalid_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "company": "",
                            "position": "Junior Developer",
                            "status": null
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", hasItem("company: must not be blank")))
                .andExpect(jsonPath("$", hasItem("status: must not be null")));

        verify(applicationService, never()).createApplication(any(Application.class));
    }

    @Test
    void createApplication_whenStatusIsInvalid_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "company": "Google",
                            "position": "Junior Developer",
                            "status": "HELLO"
                        }
                        """))
                .andExpect(status().isBadRequest());

        verify(applicationService, never()).createApplication(any(Application.class));
    }

    @Test
    void updateApplication_whenApplicationExists_returnsUpdatedApplication() throws Exception {
        Application application = new Application();
        application.setId(1L);
        application.setCompany("Microsoft");
        application.setPosition("Backend Developer");
        application.setStatus(ApplicationStatus.INTERVIEW);

        when(applicationService.updateApplication(eq(1L), any(Application.class)))
                .thenReturn(application);

        mockMvc.perform(put("/applications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "company": "Microsoft",
                            "position": "Backend Developer",
                            "status": "INTERVIEW"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.company").value("Microsoft"))
                .andExpect(jsonPath("$.position").value("Backend Developer"))
                .andExpect(jsonPath("$.status").value("INTERVIEW"));
    }

    @Test
    void updateApplication_whenApplicationDoesntExist_returnsNotFound() throws Exception {
        when(applicationService.updateApplication(eq(999L), any(Application.class)))
                .thenThrow(new EntityNotFoundException());

        mockMvc.perform(put("/applications/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "company": "Microsoft",
                    "position": "Backend Developer",
                    "status": "INTERVIEW"
                }
                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteApplication_whenApplicationExists_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/applications/1"))
                .andExpect(status().isNoContent());

        verify(applicationService).deleteApplication(1L);
    }

    @Test
    void deleteApplication_whenApplicationDoesntExist_returnsNotFound() throws Exception {
        doThrow(new EntityNotFoundException())
                .when(applicationService).deleteApplication(999L);

        mockMvc.perform(delete("/applications/999"))
                .andExpect(status().isNotFound());

        verify(applicationService).deleteApplication(999L);
    }
}