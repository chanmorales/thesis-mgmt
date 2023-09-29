package io.dev.mutex.thesisinfomgmt.controller;

import io.dev.mutex.thesisinfomgmt.common.PaginatedData;
import io.dev.mutex.thesisinfomgmt.dto.RoleDTO;
import io.dev.mutex.thesisinfomgmt.service.RoleService;
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
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RoleController {

  private final RoleService roleService;

  /**
   * Retrieves paginated list of roles
   *
   * @return 200 / OK if successful. Every other results indicates an error.
   */
  @GetMapping
  public ResponseEntity<PaginatedData<RoleDTO>> getRoles(
      @Parameter(name = "page", description = "Page of the data to be retrieved.")
      @RequestParam(name = "page", defaultValue = "0") int page,
      @Parameter(name = "pageSize", description = "Number of items to be retrieved.")
      @RequestParam(name = "pageSize", defaultValue = "100") int pageSize,
      @Parameter(name = "query", description = "The query string.")
      @RequestParam(name = "query", defaultValue = "") String query
  ) {
    return ResponseEntity.ok(roleService.getRoles(page, pageSize, query));
  }

  /**
   * Creates a new role
   *
   * @return 201 / CREATED if successful. Every other results indicates an error.
   */
  @PostMapping
  public ResponseEntity<RoleDTO> createRole(
      @Parameter(name = "role", description = "The role details to be create.")
      @RequestBody RoleDTO role
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(role));
  }

  /**
   * Retrieves a role
   *
   * @return 200 / OK if successful. Every other results indicates an error.
   */
  @GetMapping("/{id}")
  public ResponseEntity<RoleDTO> getRole(
      @Parameter(name = "id", description = "The id of the role to be retrieved.")
      @PathVariable long id
  ) {
    return ResponseEntity.ok(roleService.getRole(id));
  }

  /**
   * Updates a role
   */
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateRole(
      @Parameter(name = "id", description = "The id of the role to be updated.")
      @PathVariable long id,
      @Parameter(name = "role", description = "The updated role details.")
      @RequestBody RoleDTO role
  ) {
    roleService.updateRole(id, role);
  }

  /**
   * Deletes a role
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteRole(
      @Parameter(name = "id", description = "The id of the role to be deleted.")
      @PathVariable long id
  ) {
    roleService.deleteRole(id);
  }
}
