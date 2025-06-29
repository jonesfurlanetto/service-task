package com.task.services.usecase;

import com.task.services.entity.Service;
import com.task.services.exception.ResourceNotFoundException;
import com.task.services.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class ServiceUseCaseImplTest {

    @Mock
    private ServiceRepository repository;

    @InjectMocks
    private ServiceUseCaseImpl useCase;

    private Service service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        service = Service.builder()
                .id("service-123")
                .resources(null)
                .build();
    }

    @Test
    void createService_shouldSaveAndCacheService() {
        when(repository.save(service)).thenReturn(service);

        Service result = useCase.createService(service);

        assertNotNull(result);
        assertEquals("service-123", result.getId());
        verify(repository).save(service);

        Optional<Service> cached = useCase.getService("service-123");
        assertTrue(cached.isPresent());
        assertSame(result, cached.get());
    }

    @Test
    void getService_shouldLoadFromRepositoryIfNotInCache() {
        when(repository.findById("service-123")).thenReturn(Optional.of(service));

        Optional<Service> result = useCase.getService("service-123");

        assertTrue(result.isPresent());
        assertEquals("service-123", result.get().getId());
        verify(repository, times(1)).findById("service-123");
    }

    @Test
    void getService_shouldReturnFromCacheIfPresent() {
        when(repository.findById("service-123")).thenReturn(Optional.of(service));
        useCase.getService("service-123");

        Optional<Service> cached = useCase.getService("service-123");

        assertTrue(cached.isPresent());
        verify(repository, times(1)).findById("service-123");
    }

    @Test
    void getService_shouldReturnEmptyIfNotFound() {
        when(repository.findById("non-existent")).thenReturn(Optional.empty());

        Optional<Service> result = useCase.getService("non-existent");

        assertTrue(result.isEmpty());
        verify(repository).findById("non-existent");
    }

    @Test
    void getAllServices_shouldReturnAllFromRepositoryAndUpdateCache() {
        Service service1 = Service.builder().id("service-1").build();
        Service service2 = Service.builder().id("service-2").build();

        when(repository.findAll()).thenReturn(List.of(service1, service2));

        List<Service> result = useCase.getAllServices();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getId().equals("service-1")));
        assertTrue(result.stream().anyMatch(s -> s.getId().equals("service-2")));

        Optional<Service> cached1 = useCase.getService("service-1");
        Optional<Service> cached2 = useCase.getService("service-2");

        assertTrue(cached1.isPresent());
        assertTrue(cached2.isPresent());
    }

    @Test
    void getAllServices_shouldReturnEmptyListWhenNoData() {
        when(repository.findAll()).thenReturn(List.of());

        List<Service> result = useCase.getAllServices();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllServices_shouldNotOverwriteExistingCacheEntries() {
        Service existing = Service.builder().id("service-1").resources(null).build();
        when(repository.save(existing)).thenReturn(existing);
        useCase.createService(existing);

        Service newInstance = Service.builder().id("service-1").resources(null).build();
        when(repository.findAll()).thenReturn(List.of(newInstance));

        List<Service> result = useCase.getAllServices();

        Optional<Service> cached = useCase.getService("service-1");
        assertTrue(cached.isPresent());
        assertSame(existing, cached.get());
    }

    @Test
    void updateService_shouldSaveAndCache_whenServiceExists() {
        when(repository.findById("service-123")).thenReturn(Optional.of(service));
        when(repository.save(service)).thenReturn(service);

        boolean result = useCase.updateService(service);

        assertTrue(result);
        verify(repository).findById("service-123");
        verify(repository).save(service);

        Optional<Service> cached = useCase.getService("service-123");
        assertTrue(cached.isPresent());
        assertEquals("service-123", cached.get().getId());
    }

    @Test
    void updateService_shouldThrow_whenServiceDoesNotExist() {
        when(repository.findById("service-123")).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                () -> useCase.updateService(service));

        assertEquals("Service not found with the given input data ServiceID : 'service-123'", thrown.getMessage());
        verify(repository).findById("service-123");
        verify(repository, never()).save(any());
    }

    @Test
    void deleteService_shouldReturnTrueAndRemoveFromCacheAndRepository() {
        when(repository.findById("service-123")).thenReturn(Optional.of(service));
        useCase.getService("service-123");

        boolean result = useCase.deleteService("service-123");

        assertTrue(result);
        verify(repository).deleteById("service-123");

        useCase.getService("service-123");
        verify(repository, times(2)).findById("service-123");
    }

    @Test
    void deleteService_shouldReturnTrueEvenIfIdNotFound() {
        boolean result = useCase.deleteService("missing-id");
        assertTrue(result);
        verify(repository).deleteById("missing-id");
    }
}
