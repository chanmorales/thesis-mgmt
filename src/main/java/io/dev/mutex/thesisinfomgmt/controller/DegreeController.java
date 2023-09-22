package io.dev.mutex.thesisinfomgmt.controller;

import io.dev.mutex.thesisinfomgmt.common.PaginatedData;
import io.dev.mutex.thesisinfomgmt.dto.DegreeDTO;
import io.dev.mutex.thesisinfomgmt.service.DegreeService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/degrees")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DegreeController {

  private final DegreeService degreeService;

  /**
   * Retrieves paginated list of degrees
   *
   * @return 200 / OK if successful. Every other results indicates an error.
   */
  @GetMapping
  public ResponseEntity<PaginatedData<DegreeDTO>> getDegrees(
      @Parameter(name = "page", description = "Page of the data to be retrieved.")
      @RequestParam(name = "page", defaultValue = "0") int page,
      @Parameter(name = "pageSize", description = "Number of items to be retrieved.")
      @RequestParam(name = "pageSize", defaultValue = "100") int pageSize,
      @Parameter(name = "query", description = "The query string.")
      @RequestParam(name = "query", defaultValue = "") String query
  ) {
    return ResponseEntity.ok(degreeService.getDegrees(page, pageSize, query));
  }

  /**
   * Creates a new degree
   *
   * @return 201 / CREATED if successful. Every other results indicates an error.
   */
  @PostMapping
  public ResponseEntity<DegreeDTO> createDegree(
      @Parameter(name = "degree", description = "The degree details to be created.")
      @RequestBody DegreeDTO degree
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(degreeService.createDegree(degree));
  }

  /**
   * Retrieves a degree
   *
   * @return 200 / OK if successful. Every other results indicates an error.
   */
  @GetMapping("/{id}")
  public ResponseEntity<DegreeDTO> getDegree(
      @Parameter(name = "id", description = "The id of the degree to be retrieved.")
      @PathVariable long id
  ) {
    return ResponseEntity.ok(degreeService.getDegree(id));
  }

  /**
   * Updates a degree
   */
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateDegree(
      @Parameter(name = "id", description = "The id of the author to be updated.")
      @PathVariable long id,
      @Parameter(name = "author", description = "The updated author details.")
      @RequestBody DegreeDTO degree
  ) {
    degreeService.updateDegree(id, degree);
  }

  /**
   * Deletes a degree
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteDegree(
      @Parameter(name = "id", description = "The id of the author to be deleted.")
      @PathVariable long id
  ) {
    degreeService.deleteDegree(id);
  }
}
