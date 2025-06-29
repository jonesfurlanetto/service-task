package com.task.services.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.services.dto.ServiceDTO;
import com.task.services.entity.Service;
import com.task.services.exception.GlobalExceptionHandler;
import com.task.services.exception.ResourceNotFoundException;
import com.task.services.mapper.ServiceMapper;
import com.task.services.usecase.ServiceUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ServiceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServiceUseCase useCase;

    @Mock
    private ServiceMapper mapper;

    @InjectMocks
    private ServiceController controller;

    private ObjectMapper objectMapper;

    private ServiceDTO dto;
    private Service entity;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        dto = ServiceDTO.builder().id("123").build();
        entity = Service.builder().id("123").build();
    }

    @Test
    void createService_shouldReturn201() throws Exception {
        Mockito.when(mapper.toEntity(any())).thenReturn(entity);
        Mockito.when(useCase.createService(any())).thenReturn(entity);

        mockMvc.perform(post("/api/services/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.statusMsg").value("Service created successfully"));
    }

    @Test
    void getService_shouldReturn200IfFound() throws Exception {
        Mockito.when(useCase.getService("123")).thenReturn(Optional.of(entity));
        Mockito.when(mapper.toDTO(entity)).thenReturn(dto);

        mockMvc.perform(get("/api/services/fetch").param("serviceId", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void getService_shouldReturn404IfNotFound() throws Exception {
        Mockito.when(useCase.getService("123")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/services/fetch").param("serviceId", "123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getService_shouldReturn500OnError() throws Exception {
        Mockito.when(useCase.getService("123")).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/services/fetch").param("serviceId", "123"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getAllServices_shouldReturn200WithList() throws Exception {
        Service entity2 = Service.builder().id("456").build();
        ServiceDTO dto2 = ServiceDTO.builder().id("456").build();

        Mockito.when(useCase.getAllServices()).thenReturn(List.of(entity, entity2));
        Mockito.when(mapper.toDTO(entity)).thenReturn(dto);
        Mockito.when(mapper.toDTO(entity2)).thenReturn(dto2);

        mockMvc.perform(get("/api/services/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("123"))
                .andExpect(jsonPath("$[1].id").value("456"));
    }

    @Test
    void getAllServices_shouldReturnEmptyList() throws Exception {
        Mockito.when(useCase.getAllServices()).thenReturn(List.of());

        mockMvc.perform(get("/api/services/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getAllServices_shouldReturn500OnError() throws Exception {
        Mockito.when(useCase.getAllServices()).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/services/all"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void updateService_shouldReturn200_whenUpdateSucceeds() throws Exception {
        Mockito.when(mapper.toEntity(any())).thenReturn(entity);
        Mockito.when(useCase.updateService(any(Service.class))).thenReturn(true);

        mockMvc.perform(put("/api/services/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.statusMsg").value("Request processed successfully"));
    }

    @Test
    void updateService_shouldReturn404IfServiceNotFound() throws Exception {
        Mockito.when(mapper.toEntity(any())).thenReturn(entity);
        Mockito.when(useCase.updateService(any(Service.class)))
                .thenThrow(new ResourceNotFoundException("Service", "ServiceID", "123"));

        mockMvc.perform(put("/api/services/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteService_shouldReturn200_whenDeleteSucceeds() throws Exception {
        Mockito.when(useCase.deleteService("123")).thenReturn(true);

        mockMvc.perform(delete("/api/services/delete").param("serviceId", "123"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteService_shouldReturn500_whenDeleteThrows() throws Exception {
        Mockito.when(useCase.deleteService("123")).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(delete("/api/services/delete").param("serviceId", "123"))
                .andExpect(status().isInternalServerError());
    }

}