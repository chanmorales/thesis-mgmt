package io.dev.mutex.thesisinfomgmt.controller;

import io.dev.mutex.thesisinfomgmt.common.PaginatedData;
import io.dev.mutex.thesisinfomgmt.dto.ThesisDTO;
import io.dev.mutex.thesisinfomgmt.service.ThesisService;
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
@RequestMapping("/api/theses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ThesisController {

  private final ThesisService thesisService;

  /**
   * Retrieves paginated list of theses
   *
   * @return 200 / OK if successful. Every other results indicates an error.
   */
  @GetMapping
  public ResponseEntity<PaginatedData<ThesisDTO>> getTheses(
      @Parameter(name = "page", description = "Page of the data to be retrieved.")
      @RequestParam(name = "page", defaultValue = "0") int page,
      @Parameter(name = "pageSize", description = "Number of items to be retrieved.")
      @RequestParam(name = "pageSize", defaultValue = "100") int pageSize,
      @Parameter(name = "query", description = "The query string.")
      @RequestParam(name = "query", defaultValue = "") String query
  ) {
    return ResponseEntity.ok(thesisService.getTheses(page, pageSize, query));
  }

  /**
   * Creates a new thesis
   *
   * @return 201 / CREATED if successful. Every other results indicates an error.
   */
  @PostMapping
  public ResponseEntity<ThesisDTO> createThesis(
      @Parameter(name = "thesis", description = "The thesis details to be created.")
      @RequestBody ThesisDTO thesis
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(thesisService.createThesis(thesis));
  }

  /**
   * Retrieves a thesis
   *
   * @return 200 / OK if successful. Every other results indicates an error.
   */
  @GetMapping("/{id}")
  public ResponseEntity<ThesisDTO> getThesis(
      @Parameter(name = "id", description = "The id of the thesis to be retrieved.")
      @PathVariable long id
  ) {
    return ResponseEntity.ok(thesisService.getThesis(id));
  }

  /**
   * Updates a thesis
   */
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateThesis(
      @Parameter(name = "id", description = "The id of the thesis to be updated.")
      @PathVariable long id,
      @Parameter(name = "thesis", description = "The updated thesis details.")
      @RequestBody ThesisDTO thesis
  ) {
    thesisService.updateThesis(id, thesis);
  }

  /**
   * Deletes a thesis
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteThesis(
      @Parameter(name = "id", description = "The id of the thesis to be deleted.")
      @PathVariable long id
  ) {
    thesisService.deleteThesis(id);
  }
}
