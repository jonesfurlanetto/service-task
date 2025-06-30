package com.task.services.controller;

import com.task.services.constants.ServiceConstants;
import com.task.services.dto.ErrorResponseDto;
import com.task.services.dto.ResponseDto;
import com.task.services.dto.ServiceDTO;
import com.task.services.entity.Service;
import com.task.services.mapper.ServiceMapper;
import com.task.services.usecase.ServiceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(
        name = "CRUD REST APIs for Service",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH, GETALL AND DELETE service details"
)
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceUseCase useCase;
    private final ServiceMapper mapper;

    public ServiceController(ServiceUseCase useCase, ServiceMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Create Service REST API",
            description = "REST API to create new Service"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createService(@Valid @RequestBody ServiceDTO request) {
        Service entity = mapper.toEntity(request);
        useCase.createService(entity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(ServiceConstants.STATUS_201, ServiceConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Service Details REST API",
            description = "REST API to fetch Service details based on a serviceId"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found"
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<ServiceDTO> getService(@RequestParam String serviceId) {
        return useCase.getService(serviceId)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get all Services REST API",
            description = "REST API to get all Services"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            )
    }
    )
    @GetMapping("/all")
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        List<ServiceDTO> services = useCase.getAllServices()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(services);
    }

    @Operation(
            summary = "Update Service Details REST API",
            description = "REST API to update Service details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateService(@Valid @RequestBody ServiceDTO request) {
        Service entity = mapper.toEntity(request);
        boolean isUpdated = useCase.updateService(entity);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ServiceConstants.STATUS_200, ServiceConstants.MESSAGE_200));
        }
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(ServiceConstants.STATUS_417, ServiceConstants.MESSAGE_417_UPDATE));
    }

    @Operation(
            summary = "Delete Service Details REST API",
            description = "REST API to delete Service details based on a serviceId"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteService(@RequestParam String serviceId) {
        boolean isDeleted = useCase.deleteService(serviceId);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ServiceConstants.STATUS_200, ServiceConstants.MESSAGE_200));
        }
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(ServiceConstants.STATUS_417, ServiceConstants.MESSAGE_417_DELETE));
    }

}

