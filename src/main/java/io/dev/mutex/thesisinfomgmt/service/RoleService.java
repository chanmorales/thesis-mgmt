package io.dev.mutex.thesisinfomgmt.service;

import io.dev.mutex.thesisinfomgmt.common.PaginatedData;
import io.dev.mutex.thesisinfomgmt.dto.RoleDTO;

public interface RoleService {

  /**
   * Retrieves roles
   *
   * @param page     the page of the data to be retrieved
   * @param pageSize the number of items to be retrieved
   * @param query    the query string
   * @return paginated roles data
   */
  PaginatedData<RoleDTO> getRoles(int page, int pageSize, String query);

  /**
   * Creates a new role
   *
   * @param role the details of the new role
   * @return create role
   */
  RoleDTO createRole(RoleDTO role);

  /**
   * Retrieves a role by its id
   *
   * @param id the id of the role to be retrieved
   * @return retrieved role
   */
  RoleDTO getRole(long id);

  /**
   * Deletes a role
   *
   * @param id the id of the role to be deleted
   */
  void deleteRole(long id);

  /**
   * Updates a role
   *
   * @param id   the id of the role to be updated
   * @param role the updated role details
   */
  void updateRole(long id, RoleDTO role);
}
