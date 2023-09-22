package io.dev.mutex.thesisinfomgmt.service;

import io.dev.mutex.thesisinfomgmt.common.PaginatedData;
import io.dev.mutex.thesisinfomgmt.dto.AuthorDTO;

public interface AuthorService {

  /**
   * Retrieves authors
   *
   * @param page     the page of the data to be retrieved
   * @param pageSize the number of items to be retrieved
   * @param query    the query string
   * @return paginated authors data
   */
  PaginatedData<AuthorDTO> getAuthors(int page, int pageSize, String query);

  /**
   * Creates a new author
   *
   * @param author the details of the new author
   * @return created author
   */
  AuthorDTO createAuthor(AuthorDTO author);

  /**
   * Retrieves an author by its id
   *
   * @param id the id of the author to be retrieved
   * @return retrieved author
   */
  AuthorDTO getAuthor(long id);

  /**
   * Deletes an author
   *
   * @param id the id of the author to be deleted
   */
  void deleteAuthor(long id);

  /**
   * Updates an author
   *
   * @param id     the id of the author to be updated
   * @param author the updated author details
   */
  void updateAuthor(long id, AuthorDTO author);
}
