package io.dev.mutex.thesisinfomgmt.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.dev.mutex.thesisinfomgmt.model.entity.Author;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class AuthorDTO {

  @XmlElement
  private long id;

  @XmlElement
  private String lastName;

  @XmlElement
  private String firstName;

  @XmlElement
  private String middleName;

  /**
   * Creates a new author dto based on the provided author entity
   *
   * @param author the author entity
   */
  public AuthorDTO(Author author) {
    this.id = author.getId();
    this.lastName = author.getLastName();
    this.firstName = author.getFirstName();
    this.middleName = author.getMiddleName();
  }
}
