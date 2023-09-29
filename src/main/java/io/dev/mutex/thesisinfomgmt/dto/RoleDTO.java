package io.dev.mutex.thesisinfomgmt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.dev.mutex.thesisinfomgmt.model.Role;
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
public class RoleDTO {

  @XmlElement
  private long id;

  @XmlElement
  private String name;

  /**
   * Creates a new role dto based on the provided role entity
   *
   * @param role the role entity
   */
  public RoleDTO(Role role) {
    this.id = role.getId();
    this.name = role.getName();
  }
}
