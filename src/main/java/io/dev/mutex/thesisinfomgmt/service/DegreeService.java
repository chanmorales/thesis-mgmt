package io.dev.mutex.thesisinfomgmt.service;

import io.dev.mutex.thesisinfomgmt.common.PaginatedData;
import io.dev.mutex.thesisinfomgmt.dto.DegreeDTO;

public interface DegreeService {

  /**
   * Retrieves degrees
   *
   * @param page     the page of the data to be retrieved
   * @param pageSize the number of items to be retrieved
   * @param query    the query string
   * @return paginated degree data
   */
  PaginatedData<DegreeDTO> getDegrees(int page, int pageSize, String query);

  /**
   * Creates a new degree
   *
   * @param degree the details of the new degree
   * @return created degree
   */
  DegreeDTO createDegree(DegreeDTO degree);

  /**
   * Retrieves a degree by its id
   *
   * @param id the id of the degree to be retrieved
   * @return retrieved degree
   */
  DegreeDTO getDegree(long id);

  /**
   * Deletes a degree
   *
   * @param id the id of the degree to be deleted
   */
  void deleteDegree(long id);

  /**
   * Updates a degree
   *
   * @param id     the id of the degree to be updated
   * @param degree the updated degree details
   */
  void updateDegree(long id, DegreeDTO degree);
}
