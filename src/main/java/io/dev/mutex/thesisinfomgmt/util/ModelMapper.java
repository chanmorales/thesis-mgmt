package io.dev.mutex.thesisinfomgmt.util;

import io.dev.mutex.thesisinfomgmt.dto.AuthorDTO;
import io.dev.mutex.thesisinfomgmt.dto.DegreeDTO;
import io.dev.mutex.thesisinfomgmt.model.Author;
import io.dev.mutex.thesisinfomgmt.model.Degree;
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

  /**
   * Maps a degree dto to a degree entity
   *
   * @param degreeDTO the degree dto to be mapped
   * @return degree entity
   */
  public static Degree map(DegreeDTO degreeDTO) {
    Degree degree = new Degree();
    degree.setCode(degreeDTO.getCode());
    degree.setName(degreeDTO.getName());

    return degree;
  }
}
