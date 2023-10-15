package io.dev.mutex.thesisinfomgmt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.dev.mutex.thesisinfomgmt.model.Degree;
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
public class DegreeDTO {

  @XmlElement
  private long id;

  @XmlElement
  private String code;

  @XmlElement
  private String name;

  @XmlElement
  private int thesisCount;

  /**
   * Creates a new degree dto based on the provided degree entity
   *
   * @param degree the degree entity
   */
  public DegreeDTO(Degree degree) {
    this.id = degree.getId();
    this.code = degree.getCode();
    this.name = degree.getName();
    this.thesisCount = degree.getThesisCount();
  }
}
