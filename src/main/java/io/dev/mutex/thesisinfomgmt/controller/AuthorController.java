package io.dev.mutex.thesisinfomgmt.controller;

import io.dev.mutex.thesisinfomgmt.common.PaginatedData;
import io.dev.mutex.thesisinfomgmt.model.dto.AuthorDTO;
import io.dev.mutex.thesisinfomgmt.service.AuthorService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

  private final AuthorService authorService;

  /**
   * Retrieves paginated list of authors
   *
   * @return 200 / OK if successful. Every other results indicates an error.
   */
  @GetMapping
  public ResponseEntity<PaginatedData<AuthorDTO>> getAuthors(
      @Parameter(name = "page", description = "Page of the data to be retrieved.")
      @RequestParam(name = "page", defaultValue = "0") int page,
      @Parameter(name = "pageSize", description = "Number of items to be retrieved.")
      @RequestParam(name = "pageSize", defaultValue = "100") int pageSize,
      @Parameter(name = "query", description = "The query string.")
      @RequestParam(name = "query", defaultValue = "") String query
  ) {
    return ResponseEntity.ok(authorService.getAuthors(page, pageSize, query));
  }

  /**
   * Retrieves paginated list of authors
   *
   * @return 201 / CREATED if successful. Every other results indicates an error.
   */
  @PostMapping
  public ResponseEntity<AuthorDTO> createAuthor(
      @Parameter(name = "author", description = "The author details to be created.")
      @RequestBody AuthorDTO author
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authorService.createAuthor(author));
  }

  /**
   * Retrieves an author
   *
   * @return 200 / OK if successful. Every other results indicates an error.
   */
  @GetMapping("/{id}")
  public ResponseEntity<AuthorDTO> getAuthor(
      @Parameter(name = "id", description = "The id of the author to be retrieved.")
      @PathVariable long id
  ) {
    return ResponseEntity.ok(authorService.getAuthor(id));
  }

  /**
   * Updates an author
   */
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateAuthor(
      @Parameter(name = "id", description = "The id of the author to be updated.")
      @PathVariable long id,
      @Parameter(name = "author", description = "The updated author details.")
      @RequestBody AuthorDTO author
  ) {
    authorService.updateAuthor(id, author);
  }

  /**
   * Deletes an author
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAuthor(
      @Parameter(name = "id", description = "The id of the author to be deleted.")
      @PathVariable long id
  ) {
    authorService.deleteAuthor(id);
  }
}
