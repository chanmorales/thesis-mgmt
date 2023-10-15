package io.dev.mutex.thesisinfomgmt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.dev.mutex.thesisinfomgmt.model.ThesisAuthorRole;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class AuthorRoleDTO {

  @XmlElement
  private AuthorDTO author;

  @XmlElement
  private RoleDTO role;

  /**
   * Creates a new author roles dto based on the thesis author role entity
   *
   * @param thesisAuthorRole the thesis author role basis
   */
  public AuthorRoleDTO(ThesisAuthorRole thesisAuthorRole) {
    this.author = new AuthorDTO(thesisAuthorRole.getAuthor());
    this.role = new RoleDTO(thesisAuthorRole.getRole());
  }
}
