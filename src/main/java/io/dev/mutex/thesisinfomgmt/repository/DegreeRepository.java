package io.dev.mutex.thesisinfomgmt.repository;

import io.dev.mutex.thesisinfomgmt.model.Degree;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DegreeRepository extends JpaRepository<Degree, Long> {

  /**
   * Finds degrees that contains either the searched code or name
   *
   * @param code     the searched code
   * @param name     the searched name
   * @param pageable the pageable details
   * @return degrees that matched the searched criteria
   */
  Page<Degree> findAllByCodeContainsIgnoreCaseOrNameContainsIgnoreCase(
      String code, String name, Pageable pageable);

  /**
   * Retrieves degree by its code
   *
   * @param code the code of the degree
   * @return retrieved degree
   */
  Optional<Degree> findByCodeIgnoreCase(String code);

  /**
   * Retrieves degree by its name
   *
   * @param name the name of the degree
   * @return retrieved degree
   */
  Optional<Degree> findByNameIgnoreCase(String name);
}
