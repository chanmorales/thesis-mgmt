package io.dev.mutex.thesisinfomgmt.repository;

import io.dev.mutex.thesisinfomgmt.model.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

  Page<Author> findAllByLastNameContainsIgnoreCaseOrFirstNameContainsIgnoreCase(
      String lastName, String firstName, Pageable pageable);
}
