package io.dev.mutex.thesisinfomgmt.util;

import io.dev.mutex.thesisinfomgmt.model.dto.AuthorDTO;
import io.dev.mutex.thesisinfomgmt.model.entity.Author;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelMapper {

  /**
   * Maps an author dto to an author entity
   *
   * @param authorDTO the author dto to be mapped
   * @return author entity
   */
  public static Author map(AuthorDTO authorDTO) {
    Author author = new Author();
    author.setLastName(authorDTO.getLastName());
    author.setFirstName(authorDTO.getFirstName());
    author.setMiddleName(authorDTO.getMiddleName());

    return author;
  }
}
