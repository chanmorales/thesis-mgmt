package io.dev.mutex.thesisinfomgmt.repository;

import io.dev.mutex.thesisinfomgmt.model.Role;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  /**
   * Finds roles that contains the searched name
   *
   * @param name     the searched name
   * @param pageable the pageable details
   * @return roles that matched the searched criteria
   */
  Page<Role> findAllByNameContainsIgnoreCase(String name, Pageable pageable);

  /**
   * Retrieves role by its name
   *
   * @param name the name of the role
   * @return retrieved role
   */
  Optional<Role> findByNameIgnoreCase(String name);
}
