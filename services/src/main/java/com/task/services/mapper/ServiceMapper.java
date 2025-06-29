package com.task.services.mapper;

import com.task.services.dto.OwnerDTO;
import com.task.services.dto.ResourceDTO;
import com.task.services.dto.ServiceDTO;
import com.task.services.entity.Owner;
import com.task.services.entity.Resource;
import com.task.services.entity.Service;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceMapper {

    public ServiceDTO toDTO(Service service) {
        if (service == null) return null;

        return ServiceDTO.builder()
                .id(service.getId())
                .resources(toResourceDTOList(service.getResources()))
                .build();
    }

    public Service toEntity(ServiceDTO dto) {
        if (dto == null) return null;

        return Service.builder()
                .id(dto.getId())
                .resources(toResourceEntityList(dto.getResources()))
                .build();
    }

    private List<ResourceDTO> toResourceDTOList(List<Resource> resources) {
        if (resources == null) return null;

        return resources.stream()
                .map(resource -> ResourceDTO.builder()
                        .id(resource.getId())
                        .owners(toOwnerDTOList(resource.getOwners()))
                        .build())
                .collect(Collectors.toList());
    }

    private List<Resource> toResourceEntityList(List<ResourceDTO> resourceDTOs) {
        if (resourceDTOs == null) return null;

        return resourceDTOs.stream()
                .map(dto -> Resource.builder()
                        .id(dto.getId())
                        .owners(toOwnerEntityList(dto.getOwners()))
                        .build())
                .collect(Collectors.toList());
    }

    private List<OwnerDTO> toOwnerDTOList(List<Owner> owners) {
        if (owners == null) return null;

        return owners.stream()
                .map(owner -> OwnerDTO.builder()
                        .id(owner.getId())
                        .name(owner.getName())
                        .accountNumber(owner.getAccountNumber())
                        .level(owner.getLevel())
                        .build())
                .collect(Collectors.toList());
    }

    private List<Owner> toOwnerEntityList(List<OwnerDTO> ownerDTOs) {
        if (ownerDTOs == null) return null;

        return ownerDTOs.stream()
                .map(dto -> Owner.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .accountNumber(dto.getAccountNumber())
                        .level(dto.getLevel())
                        .build())
                .collect(Collectors.toList());
    }
}
