package com.task.services.usecase;

import com.task.services.entity.Service;
import com.task.services.exception.ResourceNotFoundException;
import com.task.services.exception.ServiceAlreadyExistsException;
import com.task.services.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ServiceUseCaseImpl implements ServiceUseCase {

    private final ServiceRepository repository;
    private final Map<String, Service> cache = new ConcurrentHashMap<>();

    /**
     *
     * @param service - Service Object
     */
    @Override
    public Service createService(Service service) {
        Optional<Service> optionalService = Optional.ofNullable(cache.computeIfAbsent(service.getId(), k -> repository.findById(k).orElse(null)));
        if (optionalService.isPresent()) {
            throw new ServiceAlreadyExistsException("Service already registered with given ServiceID " + service.getId());
        }
        Service saved = repository.save(service);
        cache.put(saved.getId(), saved);
        log.info("Service created with ID: {}", saved.getId());
        return saved;
    }

    /**
     *
     * @param serviceId - Input ServiceId
     * @return Service Details based on a given ServiceId
     */
    @Override
    public Optional<Service> getService(String serviceId) {
        Optional<Service> service = Optional.ofNullable(cache.computeIfAbsent(serviceId, k -> repository.findById(k).orElse(null)));
        log.info("Complete service: {}", service);
        return service;
    }

    /**
     *
     * @return List of all Services Details
     */
    @Override
    public List<Service> getAllServices() {
        List<Service> services = repository.findAll();
        services.forEach(service -> cache.putIfAbsent(service.getId(), service));
        log.info("Retrieved all services. Total: {}", services.size());
        return services;
    }

    /**
     *
     * @param service - Service Object
     * @return boolean indicating if the update of Service details is successful or not
     */
    @Override
    public boolean updateService(Service service) {
        cache.computeIfAbsent(service.getId(), k -> repository.findById(k).orElseThrow(
                () -> new ResourceNotFoundException("Service", "ServiceID", service.getId()))
        );
        Service saved = repository.save(service);
        cache.put(service.getId(), saved);
        log.info("Service updated with ID: {}", service.getId());
        return true;
    }

    /**
     *
     * @param serviceId - Input ServiceId
     * @return boolean indicating if the delete of Service details is successful or not
     */
    @Override
    public boolean deleteService(String serviceId) {
        repository.deleteById(serviceId);
        cache.remove(serviceId);
        log.info("Service deleted with ID: {}", serviceId);
        return true;
    }
}

