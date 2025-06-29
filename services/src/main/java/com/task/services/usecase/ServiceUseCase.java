package com.task.services.usecase;

import com.task.services.entity.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceUseCase {

    /**
     *
     * @param service - Service Object
     */
    Service createService(Service service);

    /**
     *
     * @param serviceId - Input ServiceId
     * @return Service Details based on a given ServiceId
     */
    Optional<Service> getService(String serviceId);

    /**
     *
     * @return List of all Services Details
     */
    List<Service> getAllServices();

    /**
     *
     * @param service - Service Object
     * @return boolean indicating if the update of Service details is successful or not
     */
    boolean updateService(Service service);

    /**
     *
     * @param serviceId - Input ServiceId
     * @return boolean indicating if the delete of Service details is successful or not
     */
    boolean deleteService(String serviceId);
}
