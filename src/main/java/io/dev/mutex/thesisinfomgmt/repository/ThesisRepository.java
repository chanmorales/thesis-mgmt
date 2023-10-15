package io.dev.mutex.thesisinfomgmt.repository;

import io.dev.mutex.thesisinfomgmt.model.Thesis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThesisRepository extends JpaRepository<Thesis, Long> {

  /**
   * Find theses where title matches the search query
   *
   * @param title    the title search query
   * @param pageable the pageable details
   * @return theses that matched the search criteria
   */
  Page<Thesis> findAllByTitleContainsIgnoreCase(String title, Pageable pageable);
}
