package io.dev.mutex.thesisinfomgmt.service;

import static io.dev.mutex.thesisinfomgmt.common.Constants.ENTITY_ROLE;
import static io.dev.mutex.thesisinfomgmt.common.Errors.ENTITY_NOT_FOUND;
import static io.dev.mutex.thesisinfomgmt.common.Errors.PROPERTY_REQUIRED;
import static io.dev.mutex.thesisinfomgmt.common.Errors.PROPERTY_SHOULD_BE_UNIQUE;

import io.dev.mutex.thesisinfomgmt.common.PaginatedData;
import io.dev.mutex.thesisinfomgmt.dto.RoleDTO;
import io.dev.mutex.thesisinfomgmt.exception.ThesisInfoServiceException;
import io.dev.mutex.thesisinfomgmt.model.Role;
import io.dev.mutex.thesisinfomgmt.repository.RoleRepository;
import io.dev.mutex.thesisinfomgmt.util.DataHelper;
import io.dev.mutex.thesisinfomgmt.util.ModelMapper;
import io.dev.mutex.thesisinfomgmt.util.PaginationHelper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  public PaginatedData<RoleDTO> getRoles(int page, int pageSize, String query) {
    // Construct the page request
    PageRequest pageRequest = PaginationHelper.of(page, pageSize);

    // Retrieve data based on the query string
    Page<Role> roles;
    if (DataHelper.isNullOrEmpty(query)) {
      roles = roleRepository.findAll(pageRequest);
    } else {
      roles = roleRepository.findAllByNameContainsIgnoreCase(query, pageRequest);
    }

    return DataHelper.toPaginatedData(roles, pageRequest, RoleDTO::new);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RoleDTO createRole(RoleDTO role) {
    // Validate the role to be created
    validateRole(role, NumberUtils.INTEGER_MINUS_ONE);

    // Create the role
    Role newRole = ModelMapper.map(role);
    newRole = roleRepository.save(newRole);
    return new RoleDTO(newRole);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RoleDTO getRole(long id) {
    // Check if the role to be retrieved exists
    Optional<Role> role = roleRepository.findById(id);
    if (role.isEmpty()) {
      throw new ThesisInfoServiceException(String.format(ENTITY_NOT_FOUND, ENTITY_ROLE),
          HttpStatus.NOT_FOUND);
    }

    return new RoleDTO(role.get());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteRole(long id) {
    roleRepository.deleteById(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateRole(long id, RoleDTO role) {
    // Check if the role to be updated exists
    Optional<Role> updateRole = roleRepository.findById(id);
    if (updateRole.isEmpty()) {
      throw new ThesisInfoServiceException(String.format(ENTITY_NOT_FOUND, ENTITY_ROLE),
          HttpStatus.NOT_FOUND);
    }

    // Validate the updated role details
    validateRole(role, id);

    // Update the role
    Role updatedRole = updateRole.get();
    updatedRole.setName(role.getName());
    roleRepository.save(updatedRole);
  }

  /**
   * Validates role details
   *
   * @param role the role details to be validated
   */
  private void validateRole(RoleDTO role, long id) {
    // Name is required
    if (DataHelper.isNullOrEmpty(role.getName())) {
      throw new ThesisInfoServiceException(
          String.format(PROPERTY_REQUIRED, "Role name"), "name", HttpStatus.BAD_REQUEST);
    }

    // Name should be unique
    Optional<Role> duplicateNameRole = roleRepository.findByNameIgnoreCase(role.getName());
    if (duplicateNameRole.isPresent() &&
        duplicateNameRole.get().getId() != id) {
      throw new ThesisInfoServiceException(
          String.format(PROPERTY_SHOULD_BE_UNIQUE, "Role name"), "name",
          HttpStatus.BAD_REQUEST);
    }
  }
}
