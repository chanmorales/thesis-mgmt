package io.dev.mutex.thesisinfomgmt.service;

import io.dev.mutex.thesisinfomgmt.common.PaginatedData;
import io.dev.mutex.thesisinfomgmt.dto.ThesisDTO;

public interface ThesisService {

  /**
   * Retrieve theses
   *
   * @param page     the page of the data to be retrieved
   * @param pageSize the number of items to be retrieved
   * @param query    the query string
   * @return paginated thesis data
   */
  PaginatedData<ThesisDTO> getTheses(int page, int pageSize, String query);

  /**
   * Creates a new thesis
   *
   * @param thesis the details of the new thesis
   * @return created thesis
   */
  ThesisDTO createThesis(ThesisDTO thesis);

  /**
   * Retrieves a thesis by its id
   *
   * @param id the id of the thesis to be retrieved
   * @return retrieved thesis
   */
  ThesisDTO getThesis(long id);

  /**
   * Deletes a thesis
   *
   * @param id the id of the thesis to be deleted
   */
  void deleteThesis(long id);

  /**
   * Updates a thesis
   *
   * @param id     the id of the thesis to be updated
   * @param thesis the updated thesis details
   */
  void updateThesis(long id, ThesisDTO thesis);
}
