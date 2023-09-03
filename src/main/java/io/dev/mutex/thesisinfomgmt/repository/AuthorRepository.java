package io.dev.mutex.thesisinfomgmt.repository;

import io.dev.mutex.thesisinfomgmt.model.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

  /**
   * Finds authors that contains either the searched last name or first name
   *
   * @param lastName  the searched name
   * @param firstName the searched name
   * @param pageable  the pageable details
   * @return authors that matched the searched criteria
   */
  Page<Author> findAllByLastNameContainsIgnoreCaseOrFirstNameContainsIgnoreCase(
      String lastName, String firstName, Pageable pageable);
}
