package io.dev.mutex.thesisinfomgmt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.dev.mutex.thesisinfomgmt.model.Thesis;
import java.util.HashSet;
import java.util.Set;
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
public class ThesisDTO {

  @XmlElement
  private long id;

  @XmlElement
  private String title;

  @XmlElement
  private DateSubmittedDTO dateSubmitted;

  @XmlElement
  private DegreeDTO degree;

  @XmlElement
  private Set<AuthorRoleDTO> authorRoles;

  /**
   * Creates a new thesis dto based on the provided thesis entity
   *
   * @param thesis the thesis entity
   */
  public ThesisDTO(Thesis thesis) {
    this.id = thesis.getId();
    this.title = thesis.getTitle();
    this.dateSubmitted = new DateSubmittedDTO(
        Integer.parseInt(thesis.getDateSubmitted().substring(0, 4)),
        Integer.parseInt(thesis.getDateSubmitted().substring(5)));
    this.degree = new DegreeDTO(thesis.getDegree());
    this.authorRoles = new HashSet<>();
  }
}
