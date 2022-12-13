package br.com.hugolini.controller;

import br.com.hugolini.dto.PersonDto;
import br.com.hugolini.service.impl.PersonServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Tag(name = "Person", description = "Endpoint for Person")
@RequestMapping("/api/v1/people")
public class PersonController {

    private final PersonServiceImpl personService;

    public PersonController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Find person by id",
            description = "Find person by id and return a response entity with status code 200 and person object in body",
            tags = {"Person"},
            responses = {
                    @ApiResponse(
                            description = "Person found",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PersonDto.class)
                            )
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Person not found", responseCode = "404"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    public ResponseEntity<Object> findById(
            @PathVariable("id") @Parameter(description = "The id of the person to find") Long id) {
        return ResponseEntity.ok(personService.findById(id));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Find all people",
            description = "Find all people and return a response entity with status code 200 and list of people in body",
            tags = {"Person"},
            responses = {
                    @ApiResponse(
                            description = "People found",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonDto.class))
                            )
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "People not found", responseCode = "404"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    public ResponseEntity<Page<PersonDto>> findAll(@PageableDefault(sort = "id",
            direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.ok(personService.findAll(pageable));
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Create a new person",
            description = "Create a new person and return a response entity with status code 201 and person object in body",
            tags = {"Person"},
            responses = {
                    @ApiResponse(
                            description = "Person created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PersonDto.class)
                            )
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Person not created", responseCode = "404"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    public ResponseEntity<PersonDto> save(@RequestBody PersonDto personDto) throws URISyntaxException {
        var uri = new URI("/api/v1/people/" + personDto.getId());
        return ResponseEntity.created(uri).body(personService.save(personDto));
    }

    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Update a person",
            description = "Update a person and return a response entity with status code 200 and person object in body",
            tags = {"Person"},
            responses = {
                    @ApiResponse(
                            description = "Person updated",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PersonDto.class)
                            )
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Person not updated", responseCode = "404"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    public ResponseEntity<PersonDto> update(@PathVariable Long id, @RequestBody PersonDto personDto) {
        personDto.setId(id);
        return ResponseEntity.ok().body(personService.update(personDto));
    }

    @DeleteMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Delete a person",
            description = "Delete a person and return a response entity with status code 204",
            tags = {"Person"},
            responses = {
                    @ApiResponse(
                            description = "Person deleted",
                            responseCode = "204"
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Person not deleted", responseCode = "404"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
